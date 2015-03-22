package mydrive.common.network.handler;

import java.io.File;

import mydrive.MyDrive;
import mydrive.common.FileManager;
import mydrive.common.network.packet.PacketDirectoryStructure;
import mydrive.common.util.MDLog;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class HandlerDirectoryStructure implements IMessageHandler<PacketDirectoryStructure, IMessage> {

	@Override
	public IMessage onMessage(PacketDirectoryStructure message, MessageContext ctx) {
		MDLog.debug("Received Directory Structure packet");
		String playerID = ctx.getServerHandler().playerEntity.getUniqueID().toString();
		File playerFolder = new File(new File(MyDrive.proxy.getFolder(), MyDrive.proxy.getDrivePath()), playerID);
		return FileManager.instance.onDirectoryContentsMismatch(playerID, playerFolder, message.paths.clone(), message.md5Digests.clone());
	}

}
