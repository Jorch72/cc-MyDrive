package mydrive.common.handler;

import mydrive.common.util.MDLog;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;

public class PlayerPresenceHandler {
	
	public static final PlayerPresenceHandler instance = new PlayerPresenceHandler();

	@SubscribeEvent
	public void onPlayerJoin(PlayerLoggedInEvent event) {
		MDLog.info("Player %s logged in", event.player.getDisplayName());
	}
}
