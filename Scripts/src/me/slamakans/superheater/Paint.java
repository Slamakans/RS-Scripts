package me.slamakans.superheater;

import java.awt.Color;
import java.awt.Graphics;

import me.slamakans.superheater.Graph;
import me.slamakans.superheater.Superheater;

import org.powerbot.script.rt6.Skills;

import static me.slamakans.superheater.Superheater.*;

public class Paint {
	private int ox, oy;
	
	public Paint(int ox, int oy){
		this.ox = ox;
		this.oy = oy;
	}
	
	public void render(Graphics g){
		if(START == 0) return;
		int s = getExp(Skills.SMITHING);
		int ss = s - smith;
		int sl = getLvl(Skills.SMITHING);
		int sxl = getExpAt(sl);
		int sn = getExpAt(sl+1);
		float sp = (float) (s - sxl)/(sn - sxl);
		
		int m = getExp(Skills.MAGIC); // current exp
		int mm = m - magic;
		int ml = getLvl(Skills.MAGIC); // current level
		int mxl = getExpAt(ml); // exp current level was achieved at
		int mn = getExpAt(ml+1); // exp next level is achieved at
		float mp = (float) (m - mxl)/(mn - mxl);
		
		String barName = "Bronze Bars";
		switch (BAR_ID) {
		case IRON_BAR:
			barName = "Iron Bars";
			break;
		case STEEL_BAR:
			barName = "Steel Bars";
			break;
		case GOLD_BAR:
			barName = "Gold Bars";
			break;
		case MITHRIL_BAR:
			barName = "Mithril Bars";
			break;
		case ADAMANT_BAR:
			barName = "Adamant Bars";
			break;
		case RUNE_BAR:
			barName = "Rune Bars";
			break;
		}
		
		renderRect(g, 350, 230);
		g.setColor(Color.white);
		g.drawString(String.format("%,ds", (int) getTimePassed()), ox+20, oy+20);
		g.drawString(barName, ox+20, oy+40);
		g.drawString(String.format("Bars heated: %,d (%,d)",
				barsHeated, PHR(barsHeated)), ox+20, oy+80);
		g.drawString(String.format("Smithing exp. gained (%d + %.2f%%): %,d (%,d)",
				sl, sp*100, ss, PHR(ss)), ox+20, oy+100);
		g.drawString(String.format("Magic exp. gained (%d + %.2f%%): %,d (%,d)",
				ml, mp*100, mm, PHR(mm)), ox+20, oy+120);

		g.drawString("Estimated earnings: ", ox+20, oy+140);
		int w = g.getFontMetrics().stringWidth("Estimated earnings: ");
		g.setColor(Color.yellow.darker());
		g.drawString(String.format("%,dgp (%,dgp)", (profitPerBar * barsHeated), PHR(profitPerBar*barsHeated)), ox+20 + w, oy+140);
		
		g.setColor(Color.white);
		if(sl != 99){
			g.drawString(String.format("TTNL (Smithing): %,ds", (sn-s)/(PHR(ss)/3600)), ox+20, oy+160);
			g.drawString(String.format("TT99 (Smithing): %,ds", (getExpAt(99)-s)/(PHR(ss)/3600)), ox+20, oy+180);
		}
		
		if(ml != 99){
			g.drawString(String.format("TTNL (Magic): %,ds", (mn-m)/(PHR(mm)/3600)), ox+20, oy+200);
			g.drawString(String.format("TT99 (Magic): %,ds", (getExpAt(99)-m)/(PHR(mm)/3600)), ox+20, oy+220);
		}else{
			g.drawString("Time spent", ox+20, oy+195);
			g.drawString(String.format("Heating: %,ds", Superheater.heating/1000), ox+30, oy+210);
			g.drawString(String.format("Banking: %,ds", Superheater.banking/1000), ox+30, oy+225);
		}
		
		int gx = ox+185;
		int gy = oy+230-10;
		if(Graph.validate()){
			Graph.add(PHR(barsHeated));
		}
		
		Graph.render(g, gx, gy, 163, 75, 5f);
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
		g.setColor(Color.black);
		g.drawLine(cx, 0, cx, 2000);
		g.drawLine(0, cy, 2000, cy);
	}
}
