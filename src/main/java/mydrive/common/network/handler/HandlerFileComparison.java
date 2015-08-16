package mydrive.common.network.handler;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import mydrive.MyDrive;
import mydrive.common.network.packet.PacketComparisonResponse;
import mydrive.common.network.packet.PacketFileComparison;
import mydrive.common.util.FileSystemUtils;
import mydrive.common.util.MDLog;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class HandlerFileComparison implements IMessageHandler<PacketFileComparison, PacketComparisonResponse> {
	
	@Override
	public PacketComparisonResponse onMessage(PacketFileComparison message, MessageContext ctx) {
		MDLog.debug("Received File Comparison packet: %s", message.filePath);
		String digest = FileSystemUtils.getFileDigest(new File(new File(MyDrive.proxy.getFolder(), MyDrive.proxy.getDrivePath()), message.filePath));
		MDLog.debug("Server: %s Client: %s", message.md5Digest, digest);
		return new PacketComparisonResponse(message.filePath, message.md5Digest.equals(digest));
	}

}
