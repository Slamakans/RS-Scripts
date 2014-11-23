package me.slamakans.willowstoashes;

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


@Script.Manifest(name="King Willows to Ashes", description="Burns willow logs and banks the ashes")
public class WillowsToAshes extends PollingScript<ClientContext> implements PaintListener{
		public static final int ASHES = 592;
		public static final int WILLOW_LOG = 1519;
		
		public static long START;
		
		private List<Task> taskList = new ArrayList<Task>();
		
		@Override
		public void start(){
			START = System.currentTimeMillis();
			taskList.addAll(Arrays.asList(new BankTask(ctx), new FireTask(ctx), new LootTask(ctx)));
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
			g.setColor(Color.white);
			g.drawString(getTimePassed() + "s", 120, 120);
		}
		
		public static float getTimePassed(){
			return (System.currentTimeMillis()-START)/1000;
		}
}