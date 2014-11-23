package me.slamakans.spidersilkalch;

import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.Item;

public class CraftTask extends Task {
	// 179, comp 16
	private static int WIDGET_ID = 1370;
	private static int CHILD_ID = 38;
	
	public CraftTask(ClientContext ctx) {
		super(ctx);
	}
	
	@Override
	public boolean activate() {
		return ctx.backpack.select().count() == 28;
	}
	
	@Override
	public void execute() {
		Item spiderSilk = ctx.backpack.select().id(SpiderSilkAlchemy.SPIDER_SILK).poll();
		if(ctx.widgets.select().id(WIDGET_ID).poll().component(CHILD_ID).visible()){
			ctx.widgets.select().id(WIDGET_ID).poll().component(CHILD_ID).interact("Make");
		}else if(spiderSilk != null){
			spiderSilk.interact("Craft");
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}