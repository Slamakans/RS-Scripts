package me.slamakans.honeycollector;

import org.powerbot.script.Condition;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.Constants;
import org.powerbot.script.rt6.GameObject;

public class BankTask extends Task{

	public BankTask(ClientContext ctx) {
		super(ctx);
	}

	@Override
	public boolean activate() {
		return ctx.backpack.select().count() == 28;
	}

	@Override
	public void execute() {
		GameObject bank = ctx.objects.select().id(Constants.BANK_BOOTHS).nearest().poll();
		GameObject gate = ctx.objects.select().id(1553).nearest().poll();
		if(gate.id() == -1){
			if(bank.id() != -1){
				if(bank.inViewport()){
					ctx.bank.open();
					int i = 0;
					while(ctx.bank.opened() == false && i < 2000){ i++; }
					ctx.input.send("{VK_1 down}");
					Condition.sleep(30);
					ctx.input.send("{VK_1 up}");
					while(ctx.bank.opened() == true && i < 2000){ i++; }
				}else{
//					ctx.camera.turnTo(bank);
					ctx.movement.step(bank);
				}
			}
		}else{
			gate.interact("Open");
		}
	}
}