package me.slamakans.chickeneliminator;

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

@Script.Manifest(name="King Chicken Eliminator", description="Kills chickens at the East Lumbridge farm")
public class ChickenEliminator extends PollingScript<ClientContext> implements PaintListener{
	private static List<Task> taskList = new ArrayList<Task>();
	
	public static final int FEATHER = 314;
	
	@Override
	public void start(){
		taskList.addAll(Arrays.asList(new KillTask(ctx), new LootTask(ctx)));
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