package me.slamakans.runecrafter;

import org.powerbot.script.Condition;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.GameObject;

public class ExitPortalTask extends Task{
	private static final int PORTAL = 2465;

	public ExitPortalTask(ClientContext ctx) {
		super(ctx);
	}

	@Override
	public boolean activate() {
		return ctx.backpack.select().count() != 28
				&& ctx.objects.select().id(PORTAL).poll().id() == PORTAL;
	}

	@Override
	public void execute() {
		Runecrafter.STATE = "Exiting Ruins";
		GameObject portal = ctx.objects.select().id(PORTAL).poll();
		if(portal.inViewport()){
			portal.click();
			Condition.sleep(2000);
		}else{
			ctx.camera.turnTo(portal);
			Condition.sleep((int) (Math.random()*500));
			portal.click();
			Condition.sleep(2000 + (int) (Math.random()*500));
		}
	}

}
