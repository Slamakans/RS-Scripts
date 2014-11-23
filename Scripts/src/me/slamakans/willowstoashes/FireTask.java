package me.slamakans.willowstoashes;

import org.powerbot.script.Condition;
import org.powerbot.script.Tile;
import org.powerbot.script.rt6.ClientContext;
import org.powerbot.script.rt6.Item;

public class FireTask extends Task {
	private Tile[] startTiles = {
			new Tile(3189, 3489, 0)};
	
	protected static int curTile = 0;
	
	public FireTask(ClientContext ctx) {
		super(ctx);
	}
	
	@Override
	public boolean activate() {
		return ctx.backpack.select().id(WillowsToAshes.WILLOW_LOG).count() > 0
				&& ctx.players.local().idle();
	}
	
	@Override
	public void execute() {
		if (ctx.backpack.select().count() == 28) {
			int i = curTile % 1;
			Tile t = startTiles[i];
			ctx.movement.step(t);
		}
		
		Item log = ctx.backpack.select().id(WillowsToAshes.WILLOW_LOG)
				.reverse().poll();
		if (log != null) {
			log.interact("Light", log.name());
			Condition.sleep(500);
		}
	}
}