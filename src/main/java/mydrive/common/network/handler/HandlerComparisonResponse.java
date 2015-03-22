package mydrive.common.network.handler;

import java.io.File;

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
		if (compared.isDirectory()) {
			//comparison response for the directory.
			if (!message.response) {
				PacketDirectoryStructureRequest packet = new PacketDirectoryStructureRequest();
				return packet;
			}
		}
		return null;
	}

}
