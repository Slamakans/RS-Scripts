package me.slamakans.honeycollector;

import java.awt.Color;
import java.awt.Graphics;

import static me.slamakans.honeycollector.HoneyCollector.*;

public class Paint {
	private int ox, oy;
	
	public Paint(int ox, int oy){
		this.ox = ox;
		this.oy = oy;
	}
	
	public void render(Graphics g){
		if(START == 0) return;
		renderRect(g, 350, 230);
		g.setColor(Color.white);
		g.drawString(String.format("%,ds", (int) getTimePassed()), ox+20, oy+20);
		g.drawString(String.format("Combs: %,d (%,d)", HoneyCollector.honeyCollected, PHR(HoneyCollector.honeyCollected)), ox+20, oy+40);
		
		g.drawString("Profit: ", ox+20, oy+60);
		int w = g.getFontMetrics().stringWidth("Profit: ");
		g.setColor(Color.yellow.darker());
		g.drawString(String.format("%,dgp (%,dgp)",
				HoneyCollector.honeyCollected*HoneyCollector.profit,
				PHR(HoneyCollector.honeyCollected*HoneyCollector.profit)),
				ox+20 + w, oy+60);
	}
	
	private int PHR(float amt){
		return (int) (amt / (getTimePassed() / 3600f));
	}

	private void renderRect(Graphics g, int w, int h) {
		g.setColor(new Color(0f, 0f, 0f, 0.5f));
		g.fillRect(ox, oy, w, h);
		g.setColor(Color.black);
		g.drawRect(ox, oy, w, h);
		g.drawRect(ox+1, oy+1, w-2, h-2);
		g.drawRect(ox+2, oy+2, w-4, h-4);
	}
	
	public void renderCursor(Graphics g, int cx, int cy){
		g.setColor(Color.orange);
		g.drawLine(cx - 3, cy + 3, cx + 3, cy - 3);
		g.drawLine(cx - 3, cy - 3, cx + 3, cy + 3);
	}
}
