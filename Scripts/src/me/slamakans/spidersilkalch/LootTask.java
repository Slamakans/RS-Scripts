package me.slamakans.spidersilkalch;

import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.GroundItem;

public class LootTask extends Task {
	public LootTask(ClientContext ctx) {
		super(ctx);
	}

	@Override
	public boolean activate() {
		return ctx.groundItems.select()
					.id(SpiderSilkAlchemy.SPIDER_SILK).count() > 0
				&& ctx.backpack.select()
					.id(SpiderSilkAlchemy.SPIDER_SILK_ROBE_TOP).count() == 0
				&& ctx.backpack.select().count() != 28
				&& !ctx.players.local().inMotion();
	}

	@Override
	public void execute() {
		GroundItem spiderSilk = ctx.groundItems.select().id(SpiderSilkAlchemy.SPIDER_SILK).nearest().poll();
		if(spiderSilk != null){
			if(spiderSilk.inViewport()){
				spiderSilk.interact("Take", spiderSilk.name());
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}else{
				ctx.camera.turnTo(spiderSilk);
			}
		}
	}
}