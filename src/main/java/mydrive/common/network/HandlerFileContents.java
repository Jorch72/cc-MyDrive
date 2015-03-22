package mydrive.common.network;

import mydrive.common.FileManager;
import mydrive.common.util.MDLog;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class HandlerFileContents implements IMessageHandler<PacketFileContents, PacketFileRequest> {

	@Override
	public PacketFileRequest onMessage(PacketFileContents message, MessageContext ctx) {
		MDLog.debug("Received File Contents packet");
		PacketFileRequest packet = FileManager.instance.onReceiveFileChunk(message.filePath, message.request, message.chunkNum, message.contents);
		if (packet != null) {
			return packet;
		}
		return new PacketFileContinue(message.filePath, message.request);
	}

}
