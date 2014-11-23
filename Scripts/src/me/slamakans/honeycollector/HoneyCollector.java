package me.slamakans.honeycollector;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.powerbot.script.Condition;
import org.powerbot.script.MessageEvent;
import org.powerbot.script.MessageListener;
import org.powerbot.script.PaintListener;
import org.powerbot.script.PollingScript;
import org.powerbot.script.Script;
import org.powerbot.script.rt6.Component;
import org.powerbot.script.rt6.GameObject;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.Constants;
import org.powerbot.script.rt6.Npc;
import org.powerbot.script.rt6.Skills;

@Script.Manifest(name = "King Honey Collector", description = "Collects honeycombs for profit")
public class HoneyCollector extends PollingScript<ClientContext> implements
		PaintListener, MessageListener {
	public static long START;
	public static final int HONEYCOMB = 12156;
	public static int honeyCollected = 0;
	public static int profit = 0;
	
	public static Paint paint;

	private List<Task> taskList = new ArrayList<Task>();
	
	@Override
	public void start() {
		taskList.addAll(Arrays
				.asList(new CollectTask(ctx), new BankTask(ctx)));
		
		paint = new Paint(20, 20);
	}

	@Override
	public void poll() {
		if (profit == 0) {
			profit = getPrice(HONEYCOMB);
			START = System.currentTimeMillis();
		}
		
		
		for (Task task : taskList) {
			if (task.activate()) {
				task.execute();
			}
		}
	}

	@Override
	public void repaint(Graphics g) {
		paint.render(g);

		Point p = ctx.input.getLocation();
		int mx = (int) p.getX();
		int my = (int) p.getY();
		paint.renderCursor(g, mx, my);
	}
	
	public static float getTimePassed() {
		return (System.currentTimeMillis() - START) / 1000;
	}

	/**
	 * @author Coma
	 * @author Cakemix
	 * @param id
	 *            Item ID.
	 * @return price
	 */
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

	@Override
	public void messaged(MessageEvent e) {
		if(e.text().equals("You take some honeycomb from the beehive.")){
			honeyCollected++;
		}
	}
}