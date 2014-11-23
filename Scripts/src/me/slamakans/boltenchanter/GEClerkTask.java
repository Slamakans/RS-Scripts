package me.slamakans.boltenchanter;

import me.slamakans.honeycollector.Task;

import org.powerbot.script.Condition;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.Npc;

public class GEClerkTask extends Task {
	private static final int CLERK_ID = 2241;
	
	public GEClerkTask(ClientContext ctx) {
		super(ctx);
	}

	@Override
	public boolean activate() {
		return ctx.backpack.select().id(BoltEnchanter.BOLT_ID).count() == 0;
	}

	@Override
	public void execute() {
		Npc clerk = ctx.npcs.select().id(CLERK_ID).nearest().poll();
		if(clerk != null){
			clerk.interact("Exchange", clerk.name());
			Condition.sleep(200);
		}
	}
}