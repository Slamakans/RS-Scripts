package me.slamakans.willowstoashes;

import org.powerbot.script.Condition;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.Constants;
import org.powerbot.script.rt6.Npc;

public class BankTask extends Task {
	
	public BankTask(ClientContext ctx) {
		super(ctx);
	}
	
	@Override
	public boolean activate() {
		return ctx.backpack.select().id(WillowsToAshes.ASHES).count() == 28
				|| (ctx.backpack.select().count() == 0
					&& ctx.players.local().idle());
	}
	
	@Override
	public void execute() {
		if(ctx.bank.opened()){
			ctx.input.send("{VK_1 down}");
			Condition.sleep(40);
			ctx.input.send("{VK_1 up}");
			Condition.sleep(500);
			FireTask.curTile++;
		}else{
			Npc banker = ctx.npcs.select().id(Constants.BANK_NPCS).nearest().poll();
			if(banker != null){
				if(banker.inViewport()){
					banker.interact("Bank", banker.name());
				}else{
					ctx.camera.turnTo(banker);
				}
			}
		}
	}
}