package me.slamakans.fletcher;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import org.powerbot.script.Condition;
import org.powerbot.script.PaintListener;
import org.powerbot.script.PollingScript;
import org.powerbot.script.Script;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.Constants;
import org.powerbot.script.rt6.Item;
import org.powerbot.script.rt6.Npc;

@Script.Manifest(name = "King Fletcher", description = "Does fletching")
public class Fletcher extends PollingScript<ClientContext> implements PaintListener {
	@Override
	public void poll() {
		String log = "Maple logs";
		if(ctx.players.local().idle() && ctx.backpack.select().name(log).count() != 0){
			boolean a = ctx.widgets.select().id(1370).poll().component(38).visible();
			boolean b = ctx.widgets.select().id(1251).poll().component(8).visible();
			if(a == false && b == false){
//				Item feathers = ctx.backpack.select().name("Feather").poll();
//				Item shafts = ctx.backpack.select().name("Arrow shaft").poll();
				
//				feathers.interact("Use");
//				Condition.sleep(100);
//				shafts.click();
//				Condition.sleep(500);
				
				Item logs = ctx.backpack.select().name(log).poll();
				logs.interact("Craft");
				Condition.sleep(500);
			}else{
				ctx.widgets.select().id(1370).poll().component(38).click();
				Condition.sleep(2000);
			}
		}else if(ctx.backpack.select().name(log).count() == 0){
			Npc bank = ctx.npcs.select().id(Constants.BANK_NPCS).nearest().poll();
			if(bank.inViewport()){
				bank.interact("Bank", bank.name());
				int i = 0;
				while(ctx.bank.opened() == false && i < 2000){
					Condition.sleep(1);
					i++;
				}
			}else{
				ctx.camera.turnTo(bank);
			}
			if(ctx.bank.opened()){
				ctx.keyboard.send("11");
			}
		}
	}
	
	@Override
	public void repaint(Graphics g){
		Point p = ctx.input.getLocation();
		int mx = (int) p.getX();
		int my = (int) p.getY();
		g.setColor(Color.orange);
		g.drawLine(mx - 3, my + 3, mx + 3, my - 3);
		g.drawLine(mx - 3, my - 3, mx + 3, my + 3);
		g.setColor(Color.white);
		g.drawString((ctx.script().getRuntime()/1000) + "s", 20, 20);
	}
}