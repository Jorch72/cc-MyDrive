package mydrive.common.network.handler;

import java.io.File;

import mydrive.common.FileManager;
import mydrive.common.network.packet.PacketFileHeader;
import mydrive.common.network.packet.PacketFileRequest;
import mydrive.common.util.MDLog;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class HandlerFileHeader implements IMessageHandler<PacketFileHeader, PacketFileRequest> {

	@Override
	public PacketFileRequest onMessage(PacketFileHeader message, MessageContext ctx) {
		MDLog.debug("Received File Header packet");
		String path = new File(ctx.getServerHandler().playerEntity.getUniqueID().toString(), message.filePath).toString();
		MDLog.debug("Final path: %s", path);
		return FileManager.instance.onStartReceivingFile(path, message.request, message.chunkCount);
	}

}
