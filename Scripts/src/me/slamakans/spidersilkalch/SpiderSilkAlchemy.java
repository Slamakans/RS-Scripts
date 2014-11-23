package me.slamakans.spidersilkalch;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.powerbot.script.PaintListener;
import org.powerbot.script.PollingScript;
import org.powerbot.script.Script;
import org.powerbot.script.rt6.ClientContext;

@Script.Manifest(name="King Spider Silk Alchemy", description="Kills spiders in the Catacombs, crafts the silk into robe tops and high alchs them")
public class SpiderSilkAlchemy extends PollingScript<ClientContext> implements PaintListener{
	private static List<Task> taskList = new ArrayList<Task>();
	public static final int SPIDER_SILK = 25547;
	public static final int SPIDER_SILK_ROBE_TOP = 25837;
	
	public static final int CORPSE_SPIDER = 7914;
	
	@Override
	public void start(){
		taskList.addAll(Arrays.asList(new KillTask(ctx), new LootTask(ctx), new CraftTask(ctx), new AlchTask(ctx)));
	}

	@Override
	public void poll() {
		for(Task task : taskList){
			if(task.activate()){
				task.execute();
			}
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
	}
}