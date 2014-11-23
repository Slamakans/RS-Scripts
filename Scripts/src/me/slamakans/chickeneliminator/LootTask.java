package me.slamakans.chickeneliminator;

import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.GroundItem;

public class LootTask extends Task {
	public LootTask(ClientContext ctx) {
		super(ctx);
	}

	@Override
	public boolean activate() {
		return ctx.groundItems.select()
					.id(ChickenEliminator.FEATHER).count() > 0;
	}

	@Override
	public void execute() {
		GroundItem spiderSilk = ctx.groundItems.select().id(ChickenEliminator.FEATHER).nearest().poll();
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