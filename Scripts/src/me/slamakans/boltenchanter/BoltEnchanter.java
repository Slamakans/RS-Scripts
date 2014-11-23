package me.slamakans.boltenchanter;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.slamakans.honeycollector.Task;

import org.powerbot.script.PaintListener;
import org.powerbot.script.PollingScript;
import org.powerbot.script.Script;
import org.powerbot.script.rt6.ClientContext;

@Script.Manifest(name = "King Bolt Enchanter", description = "Enchants bolts")
public class BoltEnchanter extends PollingScript<ClientContext> implements
		PaintListener {
	public static final int BOLT_ID = 9342; // onyx bolts

	public static long START;

	private List<Task> taskList = new ArrayList<Task>();

	@Override
	public void start() {
		START = System.currentTimeMillis();
		taskList.addAll(
			Arrays.asList(
				new EnchantTask(ctx)
				// This was a bother, might do it later.
				/*, new GEClerkTask(ctx),
				new OpenBuyTask(ctx), new BuyTask(ctx)*/
			)
		);
	}

	@Override
	public void poll() {
		for (Task task : taskList) {
			if (task.activate()) {
				task.execute();
			}
		}
		
		if(ctx.backpack.select().id(BoltEnchanter.BOLT_ID).count() == 0){
			ctx.controller.stop();
		}
	}

	@Override
	public void repaint(Graphics g) {
		Point p = ctx.input.getLocation();
		int mx = (int) p.getX();
		int my = (int) p.getY();
		g.setColor(Color.orange);
		g.drawLine(mx - 3, my + 3, mx + 3, my - 3);
		g.drawLine(mx - 3, my - 3, mx + 3, my + 3);
		g.setColor(Color.white);
		g.drawString(getTimePassed() + "s", 120, 120);
	}

	public static float getTimePassed() {
		return (System.currentTimeMillis() - START) / 1000;
	}
}