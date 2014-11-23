package me.slamakans.alcher;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import org.powerbot.script.Filter;
import org.powerbot.script.PaintListener;
import org.powerbot.script.PollingScript;
import org.powerbot.script.Script;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.Item;

@Script.Manifest(name = "King Alcher", description = "Alchs stuff")
public class Alcher extends PollingScript<ClientContext> implements
		PaintListener {
	public static final int NATURE_RUNE = 561;

	public static long START;
	public static final Filter<Item> filter = new Filter<Item>(){
		@Override
		public boolean accept(Item i) {
			if(i.id() != NATURE_RUNE)
				i.click();
			return i.id() == NATURE_RUNE;
		}
	};

	@Override
	public void start() {
		START = System.currentTimeMillis();
	}

	@SuppressWarnings("deprecation")
	@Override
	public void poll() {
		if (ctx.backpack.select().count() == 1) {
			ctx.controller.stop();
		}else{
			ctx.keyboard.send("1");
			ctx.backpack.select().each(filter);
		}
	}

	@Override
	public void repaint(Graphics g) {
		Point p = ctx.input.getLocation();
		int mx = (int) p.getX();
		int my = (int) p.getY();
		g.setColor(Color.black);
		g.drawLine(0, my, 2000, my);
		g.drawLine(mx, 0, mx, 2000);
		g.setColor(Color.white);
		g.drawString(getTimePassed() + "s", 120, 120);
	}

	public static float getTimePassed() {
		return (System.currentTimeMillis() - START) / 1000;
	}
}