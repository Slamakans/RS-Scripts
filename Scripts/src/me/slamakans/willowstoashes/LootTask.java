package me.slamakans.willowstoashes;

import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.GroundItem;

public class LootTask extends Task {

	public LootTask(ClientContext ctx) {
		super(ctx);
	}

	@Override
	public boolean activate() {
		return ctx.groundItems.select().id(WillowsToAshes.ASHES).count() != 0
				&& ctx.backpack.select().id(WillowsToAshes.WILLOW_LOG).count() == 0
				&& ctx.backpack.select().count() != 28;
	}

	@Override
	public void execute() {
		GroundItem ashes = ctx.groundItems.select().id(WillowsToAshes.ASHES).nearest().poll();
		if(ashes != null){
			if(ashes.inViewport()){
				ashes.interact("Take", ashes.name());
			}else{
				ctx.camera.turnTo(ashes);
			}
		}
	}

}
