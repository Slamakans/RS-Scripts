package me.slamakans.superheater;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import org.powerbot.script.PaintListener;
import org.powerbot.script.PollingScript;
import org.powerbot.script.Script;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.Component;
import org.powerbot.script.rt6.Constants;
import org.powerbot.script.rt6.GameObject;
import org.powerbot.script.rt6.Npc;
import org.powerbot.script.rt6.Skills;

@Script.Manifest(name = "King Superheater", description = "Superheats ores next to a bank")
public class Superheater extends PollingScript<ClientContext> implements
		PaintListener {
	public static long START;
	public static int barsHeated = 0;

	public static final int BRONZE_BAR = 2349, IRON_BAR = 2351,
			STEEL_BAR = 2353, SILVER_BAR = 2355, GOLD_BAR = 2357,
			MITHRIL_BAR = 2359, ADAMANT_BAR = 2361, RUNE_BAR = 2363;
	
	public static final HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
	
	public static int smith = 0;
	public static int magic = 0;
	
	public static int BAR_ID;
	public static int ORE_ID = 0;;
	public static final int NATURE_RUNE = 561;
	public static final int COAL = 453;
	public static int profitPerBar = 0;
	
	public static long heating = 0;
	public static long banking = 0;

	private List<Task> taskList = new ArrayList<Task>();
	private int lastXP = 0;
	
	public static Superheater INSTANCE;
	public static Paint paint;
	
	@SuppressWarnings("deprecation")
	@Override
	public void start() {
		INSTANCE = this;
		
		map.put(BRONZE_BAR, 436);
		map.put(IRON_BAR, 440);
		map.put(SILVER_BAR, 442);
		map.put(STEEL_BAR, 440);
		map.put(GOLD_BAR, 444);
		map.put(MITHRIL_BAR, 447);
		map.put(ADAMANT_BAR, 449);
		map.put(RUNE_BAR, 451);
		
		String[] types = {
			"Bronze", "Iron", "Silver",
			"Steel", "Gold", "Mithril",
			"Adamant", "Rune"
		};
		int choice = JOptionPane.showOptionDialog(
			null,
			null,
			"Choose a bar",
			JOptionPane.OK_CANCEL_OPTION,
			JOptionPane.PLAIN_MESSAGE,
			null,
			types,
			"Rune"
		);
		
		switch(choice){
		case 0:
			BAR_ID = BRONZE_BAR;
			break;
		case 1:
			BAR_ID = IRON_BAR;
			break;
		case 2:
			BAR_ID = SILVER_BAR;
			break;
		case 3:
			BAR_ID = STEEL_BAR;
			break;
		case 4:
			BAR_ID = GOLD_BAR;
			break;
		case 5:
			BAR_ID = MITHRIL_BAR;
			break;
		case 6:
			BAR_ID = ADAMANT_BAR;
			break;
		case 7:
			BAR_ID = RUNE_BAR;
			break;
		}
		
		ORE_ID = map.get(BAR_ID);
		
		taskList.addAll(Arrays
				.asList(new BankTask(ctx), new SuperheatTask(ctx)));
		smith = ctx.skills.experience(Skills.SMITHING);
		magic = ctx.skills.experience(Skills.MAGIC);
		paint = new Paint(20, 20);
	}

	@SuppressWarnings("all")
	@Override
	public void poll() {
		Component geCloseBtn = ctx.widgets.select().id(105).poll().component(87);
		if(geCloseBtn.visible()){
			geCloseBtn.click();
		}
		if (profitPerBar == 0) {
			int b = getPrice(BAR_ID);
			int o = getPrice(ORE_ID);
			int c = getPrice(COAL);
			c *= coalUse();
			int n = getPrice(NATURE_RUNE);
			profitPerBar = b
					- (o + c + n);
			START = System.currentTimeMillis();
		}
		
		for (Task task : taskList) {
			if (task.activate()) {
				long start = System.currentTimeMillis();
				task.execute();
				if(task instanceof BankTask){
					banking += System.currentTimeMillis() - start;
					heating = (System.currentTimeMillis() - START) - banking;
				}
			}
		}
		if (Math.random() < .1f) {
			Npc bankNpc = ctx.npcs.select().id(Constants.BANK_NPCS).nearest().poll();
			if(bankNpc.id() != -1){
				ctx.camera.turnTo(bankNpc);
			}else{
				GameObject bank = ctx.objects.select().id(Constants.BANK_BOOTHS, Constants.BANK_CHESTS, Constants.BANK_COUNTERS).nearest().poll();
				ctx.camera.turnTo(bank);
			}
		}
	}

	@Override
	public void repaint(Graphics g) {
		if (lastXP == 0) {
			lastXP = getExp(Skills.SMITHING);
		} else if (lastXP != (getExp(Skills.SMITHING))) {
			barsHeated++;
			lastXP = getExp(Skills.SMITHING);
		}
		
		paint.render(g);

		Point p = ctx.input.getLocation();
		int mx = (int) p.getX();
		int my = (int) p.getY();
		paint.renderCursor(g, mx, my);
	}
	
	public static int getExp(int skill){
		return INSTANCE.ctx.skills.experience(skill);
	}
	
	public static int getLvl(int skill){
		return INSTANCE.ctx.skills.level(skill);
	}
	
	public static int getExpAt(int skill){
		return INSTANCE.ctx.skills.experienceAt(skill);
	}
	
	public static float getTimePassed() {
		return (System.currentTimeMillis() - START) / 1000;
	}

	public int getPrice(int id) {
		final String content = downloadString("http://itemdb-rs.runescape.com/viewitem.ws?obj=" + id);
		if (content == null) {
            return -1;
        }
		Pattern p = Pattern.compile("Current Guide Price <span>(.*?)</span>");
		Matcher m = p.matcher(content);
		m.find();
        String str = m.group(1);
        if (str.contains("k")) {
            str = str.replace(".", "");
            return Integer.parseInt(str.substring(0, str.indexOf("k"))) * 100;
        } else if (str.contains("m")) {
            str = str.replace(".", "");
            return Integer.parseInt(str.substring(0, str.indexOf("m"))) * 100000;
        } else if (str.contains("b")) {
            str = str.replace(".", "");
            return Integer.parseInt(str.substring(0, str.indexOf("b"))) * 100000000;
        } else {
            return Integer.parseInt(str.replace(",", ""));
        }
	}
	
	public static boolean usingCoal(){
		return coalUse() != 0;
	}
	
	public static int coalUse(){
		return BAR_ID == RUNE_BAR ? 8 : BAR_ID == ADAMANT_BAR ? 6 : BAR_ID == MITHRIL_BAR ? 4 : BAR_ID == STEEL_BAR ? 2 : 0;
	}
}