package mydrive.common.network.handler;

import java.io.File;

import mydrive.MyDrive;
import mydrive.common.FileManager;
import mydrive.common.network.packet.PacketFileChanged;
import mydrive.common.network.packet.PacketFileRequest;
import mydrive.common.util.MDLog;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class HandlerFileChanged implements IMessageHandler<PacketFileChanged, IMessage> {

	@Override
	public IMessage onMessage(PacketFileChanged message, MessageContext ctx) {
		MDLog.debug("Received File Changed packet");
		String playerID = ctx.getServerHandler().playerEntity.getUniqueID().toString();
		File playerFolder = new File(new File(MyDrive.proxy.getFolder(), MyDrive.proxy.getDrivePath()), playerID);
		return FileManager.instance.onFileChanged(message.filePath, playerFolder, message.type);
	}

}
