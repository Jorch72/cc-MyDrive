package mydrive.common.network.handler;

import java.io.File;

import mydrive.MyDrive;
import mydrive.common.network.packet.PacketComparisonResponse;
import mydrive.common.network.packet.PacketDirectoryComparison;
import mydrive.common.util.FileSystemUtils;
import mydrive.common.util.MDLog;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class HandlerDirectoryComparison implements IMessageHandler<PacketDirectoryComparison, PacketComparisonResponse> {

	@Override
	public PacketComparisonResponse onMessage(PacketDirectoryComparison message, MessageContext ctx) {
		MDLog.debug("Received Directory Comparison Packet");
		File playerFolder = new File(MyDrive.proxy.getFolder(), MyDrive.proxy.getDrivePath());
		String digest = FileSystemUtils.calcMD5HashForDir(playerFolder);
		return new PacketComparisonResponse(playerFolder.getPath(), digest.equals(message.md5Digest));
	}

}
