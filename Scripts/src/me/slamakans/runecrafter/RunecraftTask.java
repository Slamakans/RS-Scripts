package me.slamakans.runecrafter;

import org.powerbot.script.Condition;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.GameObject;

public class RunecraftTask extends Task{
//	private static final int ALTAR = 2478; // air
	private static final int ALTAR = 2486; // nature
	
	public RunecraftTask(ClientContext ctx) {
		super(ctx);
	}

	@Override
	public boolean activate() {
		return ctx.objects.select().id(ALTAR).poll().id() == ALTAR
				&& ctx.backpack.select().count() == 28;
	}

	@Override
	public void execute() {
		Runecrafter.STATE = "Runecrafting";
		GameObject altar = ctx.objects.select().id(ALTAR).poll();
		if(altar.inViewport()){
			altar.click();
			Condition.sleep((int) (Math.random()*3000));
		}else{
			ctx.camera.turnTo(altar);
			Condition.sleep(500);
			altar.click();
			Condition.sleep((int) (Math.random()*3000));
		}
	}

}
