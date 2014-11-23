package me.slamakans.willowstoashes;

import org.powerbot.script.ClientAccessor;
import org.powerbot.script.rt6.ClientContext;

public abstract class Task extends ClientAccessor<ClientContext>{

	public Task(ClientContext ctx) {
		super(ctx);
	}
	
	public abstract boolean activate();
	public abstract void execute();
}