package me.slamakans.runecrafter;

import org.powerbot.script.Condition;
import org.powerbot.script.rt6.ClientContext;

public class ToBankTask extends Task{

	public ToBankTask(ClientContext ctx) {
		super(ctx);
	}

	@Override
	public boolean activate() {
		return ctx.backpack.select().count() != 28
//				&& ctx.backpack.select().count() != 4
//				&& ctx.objects.select().id(2465).poll().id() == -1
				&& ctx.objects.select().id(1160/*Potted plant*/).nearest().poll().id() == -1
				&& ctx.players.local().idle();
	}

	@Override
	public void execute() {
//		Runecrafter.STATE = "Going To Bank";
		Runecrafter.STATE = "Teleporting";
//		Runecrafter.walkPath(Runecrafter.getReversedPath(), 10);
//		ctx.movement.step(ctx.objects.select().id(782).nearest().poll());
//		Condition.sleep(1000 + (int) (Math.random()*750));
		ctx.widgets.select().id(1465).poll().component(50).click();
		Condition.sleep(1200);
		ctx.widgets.select().id(1092).poll().component(32).click();
		Condition.sleep(2000);
		System.out.println("Clicked lodestone");
	}
	
}
