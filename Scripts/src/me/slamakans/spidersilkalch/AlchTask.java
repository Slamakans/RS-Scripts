package me.slamakans.spidersilkalch;

import org.powerbot.script.Condition;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.Item;

public class AlchTask extends Task {
	public AlchTask(ClientContext ctx) {
		super(ctx);
	}
	
	@Override
	public boolean activate() {
		return ctx.backpack.select()
					.id(SpiderSilkAlchemy.SPIDER_SILK_ROBE_TOP).count() != 0
				&& ctx.backpack.select()
					.id(SpiderSilkAlchemy.SPIDER_SILK).count() < 3;
	}

	@Override
	public void execute() {
		Item robeTop = ctx.backpack.select().id(SpiderSilkAlchemy.SPIDER_SILK_ROBE_TOP).reverse().poll();
		if(robeTop != null){
			ctx.input.send("{VK_1 down}");
			Condition.sleep(40);
			ctx.input.send("{VK_1 up}");
			Condition.sleep(150);
			robeTop.click();
			Condition.sleep(200);
		}
	}
}