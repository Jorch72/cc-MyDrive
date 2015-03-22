package mydrive.common.handler;

import mydrive.MyDrive;
import mydrive.common.FileManager;
import mydrive.common.util.MDLog;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;

public class PlayerPresenceHandler {
	
	public static final PlayerPresenceHandler instance = new PlayerPresenceHandler();

	@SubscribeEvent
	public void onPlayerJoin(PlayerLoggedInEvent event) {
		MDLog.debug("Player %s logged in", event.player.getDisplayName());
		MDLog.debug("Folder path is %s", MyDrive.proxy.getFolder());
		MDLog.debug("Drive path is %s", MyDrive.proxy.getDrivePath());
		FileManager.instance.onPlayerJoin(event.player);
	}
}
