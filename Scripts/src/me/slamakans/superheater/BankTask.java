package me.slamakans.superheater;

import org.powerbot.script.Condition;
import org.powerbot.script.rt6.ClientContext;

import static me.slamakans.superheater.Superheater.*;

public class BankTask extends Task {
	public BankTask(ClientContext ctx) {
		super(ctx);
	}

	@Override
	public boolean activate() {
		return ctx.backpack.select().id(ORE_ID).count() == 0;
	}

	@Override
	public void execute() {
		ctx.bank.open();
		int i = 0;
		while(ctx.bank.opened() == false && i < 2000){ i++; }
		// Out of primary ore
		if(ctx.bank.select().id(ORE_ID).poll().stackSize() <= 1){
			INSTANCE.stop();
			return;
		}
		
		// Out of coal
		if(usingCoal() && ctx.bank.select().id(COAL).poll().stackSize() < coalUse()){
			INSTANCE.stop();
			return;
		}
		
		// Out of tin
		if(BAR_ID == BRONZE_BAR && ctx.bank.select().id(438).poll().stackSize() <= 1){
			INSTANCE.stop();
			return;
		}
		
		// Out of nats
		if(ctx.bank.select().id(NATURE_RUNE).poll().stackSize() <= 1){
			INSTANCE.stop();
			return;
		}
		
		ctx.input.send("{VK_1 down}");
		Condition.sleep(30);
		ctx.input.send("{VK_1 up}");
		while(ctx.bank.opened() == true && i < 2000){ i++; }
	}
}