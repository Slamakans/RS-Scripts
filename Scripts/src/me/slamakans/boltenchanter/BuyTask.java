package me.slamakans.boltenchanter;

import me.slamakans.honeycollector.Task;

import org.powerbot.script.Condition;
import org.powerbot.script.rt6.ClientContext;

public class BuyTask extends Task {
	
	private static final int WIDGET_ID = 105;
	private static final int CHILD_ID = 65;

	public BuyTask(ClientContext ctx) {
		super(ctx);
	}
	
	@Override
	public boolean activate() {
		return ctx.widgets.select().id(WIDGET_ID).poll().component(CHILD_ID).visible();
	}
	
	@Override
	public void execute() {
		ctx.input.send("onyx bolts");
		Condition.sleep(2000);
		while(ctx.widgets.select().id(389).poll().component(4).component(1).visible() == false);
		ctx.widgets.select().id(389).poll().component(4).component(1).click();
		Condition.sleep(1000);
	}
}