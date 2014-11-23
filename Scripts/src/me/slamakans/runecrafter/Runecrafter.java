package me.slamakans.runecrafter;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.powerbot.script.Condition;
import org.powerbot.script.PaintListener;
import org.powerbot.script.PollingScript;
import org.powerbot.script.Script;
import org.powerbot.script.Tile;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.TilePath;

@Script.Manifest(name = "King Runecrafter", description = "Superheats ores next to a bank")
public class Runecrafter extends PollingScript<ClientContext> implements
		PaintListener {
	public static long START;
	private List<Task> taskList = new ArrayList<Task>();
	public static String STATE = "Initial state";
	
	public static final int RUNE_ESSENCE = 1436, PURE_ESSENCE = 7936;
	
	public static ClientContext CTX;
	
	public static void setAirPath(TilePath tp){
		AIR_PATH = tp;
	}
	
	public static TilePath getPath(){
		return AIR_PATH.randomize(1, 1);
	}
	
	public static TilePath getReversedPath(){
		return AIR_PATH.reverse().randomize(1, 1);
	}
	
	public static TilePath AIR_PATH;
	
	@Override
	public void start() {
		CTX = ctx;
		taskList.addAll(Arrays
				.asList(/*new BankTask(ctx), new FillPouchesTask(ctx),*/ new ToRuinsTask(ctx),
						new ToBankTask(ctx), new RunecraftTask(ctx), new UnnoteTask(ctx) /*new ExitPortalTask(ctx)*/));
//		Tile[] tiles = {
//			new Tile(3185, 3434, 0), new Tile(3179, 3429, 0),
//			new Tile(3170, 3429, 0), new Tile(3161, 3421, 0),
//			new Tile(3154, 3417, 0), new Tile(3145, 3416, 0),
//			new Tile(3137, 3411, 0), new Tile(3131, 3404, 0)
//		};
		Tile[] tiles = {
				new Tile(2785, 3124, 0), new Tile(2816, 3120, 0),
				new Tile(2828, 3096, 0), new Tile(2845, 3080, 0),
				new Tile(2859, 3064, 0), new Tile(2859, 3051, 0),
				new Tile(2862, 3036, 0), new Tile(2865, 3024, 0)
		};
		setAirPath(new TilePath(ctx, tiles));
	}

	@SuppressWarnings("all")
	@Override
	public void poll() {
		for (Task task : taskList) {
			if (task.activate()) {
				task.execute();
			}
		}
	}

	@Override
	public void repaint(Graphics g) {
		Point p = ctx.input.getLocation();
		int mx = (int) p.getX();
		int my = (int) p.getY();
//		g.setColor(Color.orange);
//		g.drawLine(mx - 3, my + 3, mx + 3, my - 3);
//		g.drawLine(mx - 3, my - 3, mx + 3, my + 3);
		g.setColor(Color.black);
		g.drawLine(0, my, 2000, my);
		g.drawLine(mx, 0, mx, 2000);
		g.setColor(Color.white);
		g.drawString((ctx.script().getRuntime()/1000) + "s", 20, 20);
		
		g.setColor(Color.CYAN);
		g.drawString("State: " + STATE, 300, 300);
//
//		Tile[] tiles = {
//				new Tile(2785, 3124, 0), new Tile(2816, 3120, 0),
//				new Tile(2828, 3096, 0), new Tile(2845, 3080, 0),
//				new Tile(2859, 3064, 0), new Tile(2859, 3051, 0),
//				new Tile(2862, 3036, 0), new Tile(2865, 3024, 0)
//		};
////		Tile tile = tiles[1];
//		Tile tile = getPath().next();
//		g.drawString("Distance to tile: " + tile.distanceTo(ctx.players.local()), 300, 320);
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
	
	public static void walkPath(TilePath t, int i){
		int time = 0;
		while(CTX.movement.destination().distanceTo(t.end()) >= i && time < 2000){
			walkNext(t.next(), i);
			time++;
			if(time % 10 == 0){
				System.out.println("Time=" + time);
			}
		}
	}

	public static void walkNext(Tile t, int i) {
		if (!CTX.players.local().inMotion() || CTX.players.local().tile().distanceTo(CTX.movement.destination()) <= i){
			if(t != null && t.distanceTo(CTX.players.local()) < 40) {
				CTX.movement.step(t);
				Condition.sleep(800 + (int) (Math.random() * 2000));
			}
		}
	}
}