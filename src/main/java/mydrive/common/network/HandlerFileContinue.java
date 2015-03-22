package mydrive.common.network;

import mydrive.common.FileManager;
import mydrive.common.util.MDLog;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class HandlerFileContinue implements IMessageHandler<PacketFileContinue, IMessage> {

	@Override
	public IMessage onMessage(PacketFileContinue message, MessageContext ctx) {
		MDLog.debug("Received File Continue packet");
		return FileManager.instance.getNextFileContentsPacket(message.request);
	}

}
