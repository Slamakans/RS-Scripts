package me.slamakans.superheater;

import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.Item;

public class SuperheatTask extends Task {
	public SuperheatTask(ClientContext ctx) {
		super(ctx);
	}

	@Override
	public boolean activate() {
		return ctx.backpack.select().id(Superheater.ORE_ID).count() > 0;
	}

	@Override
	public void execute() {
		final Item i = ctx.backpack.select().id(Superheater.ORE_ID).reverse().first().poll();
        ctx.keyboard.send("11");
        i.click();
	}
}