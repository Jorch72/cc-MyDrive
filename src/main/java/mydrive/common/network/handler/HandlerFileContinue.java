package mydrive.common.network.handler;

import mydrive.common.FileManager;
import mydrive.common.network.packet.PacketFileContinue;
import mydrive.common.util.MDLog;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class HandlerFileContinue implements IMessageHandler<PacketFileContinue, IMessage> {

	@Override
	public IMessage onMessage(PacketFileContinue message, MessageContext ctx) {
		MDLog.debug("Received File Continue packet for request %d", message.request);
		return FileManager.instance.getNextFileContentsPacket(message.request);
	}

}
