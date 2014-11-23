package me.slamakans.honeycollector;

import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.GameObject;

public class CollectTask extends Task{

	public CollectTask(ClientContext ctx) {
		super(ctx);
	}

	@Override
	public boolean activate() {
		return ctx.backpack.select().count() < 28;
	}

	@Override
	public void execute() {
		GameObject beehive = ctx.objects.select().id(68).nearest().poll();
		GameObject gate = ctx.objects.select().id(1553).nearest().poll();
		if(gate.id() == -1){
			System.out.println(beehive.id());
			if(beehive.id() != -1){
				if(beehive.inViewport()){
					beehive.interact("Take-honey", beehive.name());
					ctx.camera.turnTo(beehive);
				}else{
					ctx.camera.turnTo(beehive);
					ctx.movement.step(beehive);
				}
			}
		}else{
			gate.interact("Open");
		}
		
	}
	
}
