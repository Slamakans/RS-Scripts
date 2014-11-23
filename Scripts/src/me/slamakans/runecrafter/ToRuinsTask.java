package me.slamakans.runecrafter;

import org.powerbot.script.Condition;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.Constants;
import org.powerbot.script.rt6.GameObject;

public class ToRuinsTask extends Task{
//	private static final int RUINS = 2452; //air
	private static final int RUINS = 2460; //nature

	public ToRuinsTask(ClientContext ctx) {
		super(ctx);
	}

	@Override
	public boolean activate() {
		return ctx.backpack.select().count() == 28 && FillPouchesTask.shouldFill == false;
	}

	@Override
	public void execute() {
		Runecrafter.STATE = "Going To Ruins";
		GameObject ruin = ctx.objects.select().id(RUINS).poll();
		System.out.println(ruin.id());
		if(ruin.id() != -1){
			if(ruin.inViewport()){
				ruin.click();
				Condition.sleep(2000);
			}else{
				ctx.camera.turnTo(ruin);
				ctx.movement.step(ruin);
				Condition.sleep(1000 + (int) (Math.random()*750));
			}
		}else{
			Runecrafter.walkPath(Runecrafter.getPath(), 5);
			Condition.sleep(1000 + (int) (Math.random()*750));
		}
	}
	
}
