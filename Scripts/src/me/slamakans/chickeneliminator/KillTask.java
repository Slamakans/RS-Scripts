package me.slamakans.chickeneliminator;

import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.Npc;

public class KillTask extends Task {
	public KillTask(ClientContext ctx) {
		super(ctx);
	}

	@Override
	public boolean activate() {
		return ctx.groundItems.select().id(ChickenEliminator.FEATHER).count() == 0
				&& ctx.players.local().inCombat();
	}

	@Override
	public void execute() {
		Npc spider = ctx.npcs.select().name("Chicken").nearest().poll();
		if(spider != null){
			if(spider.inViewport() && !spider.inCombat()){
				spider.interact("Attack", spider.name());
			}else{
				ctx.camera.turnTo(spider);
			}
		}
	}

}
