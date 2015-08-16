package mydrive.common.network.handler;

import java.io.File;
import java.util.ArrayList;

import mydrive.MyDrive;
import mydrive.common.FileManager;
import mydrive.common.handler.FileFetchingQueue;
import mydrive.common.network.packet.PacketComparisonResponse;
import mydrive.common.network.packet.PacketDirectoryStructureRequest;
import mydrive.common.util.MDLog;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class HandlerComparisonResponse implements IMessageHandler<PacketComparisonResponse, IMessage> {

	@Override
	public IMessage onMessage(PacketComparisonResponse message, MessageContext ctx) {
		MDLog.debug("Received Comparison Response packet, result: %b", message.response);
		File compared = new File(message.filePath);
		if (!message.response) {
			if (compared.isDirectory()) {
				//comparison response for the directory.
				PacketDirectoryStructureRequest packet = new PacketDirectoryStructureRequest();
				return packet;
			} else {
				String playerID = ctx.getServerHandler().playerEntity.getUniqueID().toString();
				File playerFolder = new File(new File(MyDrive.proxy.getFolder(), MyDrive.proxy.getDrivePath()), playerID);
				return FileManager.instance.queueFile(playerFolder, message.filePath);
			}
		}
		return null;
	}

}
