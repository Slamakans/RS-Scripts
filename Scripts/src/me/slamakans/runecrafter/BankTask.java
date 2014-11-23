package me.slamakans.runecrafter;

import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.Constants;

public class BankTask extends Task{
	public BankTask(ClientContext ctx){
		super(ctx);
	}

	@Override
	public boolean activate() {
		return (ctx.backpack.select().count() != 28 ||
				(ctx.backpack.select().count() == 4 && FillPouchesTask.shouldFill == false)) &&
				ctx.objects.select().id(782).poll().id() != -1;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void execute() {
		Runecrafter.STATE = "Banking";
		if(ctx.bank.open()){
			if(ctx.backpack.select().count() != 4){
//				FillPouchesTask.shouldFill = true;
			}
			
			ctx.keyboard.send("11");
		}
	}
}