package me.slamakans.boltenchanter;

import me.slamakans.honeycollector.Task;

import org.powerbot.script.Condition;
import org.powerbot.script.rt6.ClientContext;

public class EnchantTask extends Task {
	public static final int WIDGET_ID = 1370;
	public static final int CHILD_ID = 38;
	
	public static final int CANCEL_WIDGET_ID = 1251;
	public static final int CANCEL_CHILD_ID = 13;
	
	public EnchantTask(ClientContext ctx) {
		super(ctx);
	}

	@Override
	public boolean activate() {
		return ctx.backpack.select().id(BoltEnchanter.BOLT_ID).count() > 0
				&& ctx.widgets.select()
					.id(CANCEL_WIDGET_ID).poll()
					.component(CANCEL_CHILD_ID).visible() == false;
	}

	@Override
	public void execute() {
		if(ctx.widgets.select().id(WIDGET_ID).poll().component(CHILD_ID).visible()){
			ctx.widgets.select().id(WIDGET_ID).poll().component(CHILD_ID).interact("Make");
			Condition.sleep(2000);
		}else{
			ctx.input.send("{VK_1 down}");
			Condition.sleep(40);
			ctx.input.send("{VK_1 up}");
			Condition.sleep(30);
		}
	}
}