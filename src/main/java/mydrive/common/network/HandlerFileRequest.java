package mydrive.common.network;

import mydrive.common.FileManager;
import mydrive.common.util.MDLog;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class HandlerFileRequest implements IMessageHandler<PacketFileRequest, IMessage> {

	@Override
	public IMessage onMessage(PacketFileRequest message, MessageContext ctx) {
		MDLog.debug("Received File Request packet");
		return FileManager.instance.onFileRequest(message.filePath, message.request);
	}

}
