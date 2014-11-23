package me.slamakans.runecrafter;

import org.powerbot.script.Condition;
import org.powerbot.script.rt6.ClientContext;

public class FillPouchesTask extends Task{
	@SuppressWarnings("unused")
	private static final int
			GIANT_POUCH = 5514, LARGE_POUCH = 5512,
			MEDIUM_POUCH = 5510, SMALL_POUCH = 5509;
	
	public static boolean shouldFill = false;
	
	public FillPouchesTask(ClientContext ctx){
		super(ctx);
	}

	@Override
	public boolean activate() {
		return shouldFill && ctx.backpack.select().count() != 4;
	}

	@Override
	public void execute() {
		Runecrafter.STATE = "Filling Pouches";
		boolean a = ctx.backpack.select().id(LARGE_POUCH).poll().interact("Fill");
		boolean b = ctx.backpack.select().id(GIANT_POUCH).poll().interact("Fill");
		boolean c = ctx.backpack.select().id(SMALL_POUCH).poll().interact("Fill");
		while(!(a && b && c)){
			if(!a){
				a = ctx.backpack.select().id(LARGE_POUCH).poll().interact("Fill");
			}
			
			if(!b){
				b = ctx.backpack.select().id(GIANT_POUCH).poll().interact("Fill");
			}
			
			if(!c){
				c = ctx.backpack.select().id(SMALL_POUCH).poll().interact("Fill");
			}
			
			Condition.sleep(1500);
		}
		
		shouldFill = false;
	}
}