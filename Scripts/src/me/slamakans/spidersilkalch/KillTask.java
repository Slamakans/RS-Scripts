package me.slamakans.spidersilkalch;

import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.Npc;

public class KillTask extends Task {

	public KillTask(ClientContext ctx) {
		super(ctx);
	}

	@Override
	public boolean activate() {
		return ctx.groundItems.select().id(SpiderSilkAlchemy.SPIDER_SILK).count() == 0
				&& ctx.backpack.select().id(SpiderSilkAlchemy.SPIDER_SILK_ROBE_TOP).count() == 0;
	}

	@Override
	public void execute() {
		Npc spider = ctx.npcs.select().id(SpiderSilkAlchemy.CORPSE_SPIDER).nearest().poll();
		if(spider != null){
			if(spider.inViewport() && !spider.inCombat()){
				spider.interact("Attack", spider.name());
			}else{
				ctx.camera.turnTo(spider);
			}
		}
	}

}
