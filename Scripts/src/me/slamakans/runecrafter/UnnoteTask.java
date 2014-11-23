package me.slamakans.runecrafter;

import org.powerbot.script.Condition;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.GameObject;
import org.powerbot.script.rt6.Npc;

public class UnnoteTask extends Task{
	private static final int JIMINUA = 560;
	private static final int UNNOTED_PURE_ESSENCE = 7937;

	public UnnoteTask(ClientContext ctx) {
		super(ctx);
	}

	@Override
	public boolean activate() {
		Npc jimjim = ctx.npcs.select().id(JIMINUA).poll();
		return ctx.backpack.select().count() == 2
				&& (
					jimjim.tile().distanceTo(ctx.players.local()) < 100
					|| ctx.objects.select().id(1160).nearest().poll().tile().distanceTo(ctx.players.local()) < 100
					);
	}

	@Override
	public void execute() {
		Npc jimjim = ctx.npcs.select().id(JIMINUA).poll();
		GameObject pot = ctx.objects.select().id(1160).nearest().poll();
		if(jimjim.id() == -1){
			ctx.camera.turnTo(pot);
			ctx.movement.step(pot);
		}else if(jimjim.tile().distanceTo(ctx.players.local()) > 10){
			ctx.camera.turnTo(jimjim);
			ctx.movement.step(jimjim);
		}else{
			Runecrafter.STATE = "Unnoting";
			ctx.backpack.select().id(UNNOTED_PURE_ESSENCE).poll().interact("Use");
			Condition.sleep(300 + (int) (Math.random() * 300));
			jimjim.interact("Use");
			Condition.sleep(1000);
			ctx.widgets.select().id(1188).poll().component(6).click();
			Condition.sleep(500);
			ctx.widgets.select().id(1188).poll().component(3).click();
			System.out.println("Unnoted");
			Condition.sleep(300);
		}
	}
}