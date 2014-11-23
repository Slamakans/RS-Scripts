package me.slamakans.boltenchanter;

import me.slamakans.honeycollector.Task;

import org.powerbot.script.Condition;
import org.powerbot.script.rt6.ClientContext;

public class OpenBuyTask extends Task {
	private static final int WIDGET_ID = 105;
	private static final int CHILD_ID = 234;
	
	public OpenBuyTask(ClientContext ctx) {
		super(ctx);
	}
	
	@Override
	public boolean activate() {
		return ctx.widgets.select().id(WIDGET_ID).poll().component(CHILD_ID).visible();
	}
	
	@Override
	public void execute() {
		ctx.widgets.select().id(WIDGET_ID).poll().component(CHILD_ID).click();
		Condition.sleep(150);
	}
}