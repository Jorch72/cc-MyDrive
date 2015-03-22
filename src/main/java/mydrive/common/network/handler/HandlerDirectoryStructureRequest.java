package mydrive.common.network.handler;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import mydrive.MyDrive;
import mydrive.common.network.packet.PacketDirectoryStructure;
import mydrive.common.network.packet.PacketDirectoryStructureRequest;
import mydrive.common.util.FileSystemUtils;
import mydrive.common.util.MDLog;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class HandlerDirectoryStructureRequest implements IMessageHandler<PacketDirectoryStructureRequest,PacketDirectoryStructure> {

	@Override
	public PacketDirectoryStructure onMessage(PacketDirectoryStructureRequest message, MessageContext ctx) {
		MDLog.debug("Received Directory Structure Request packet");
		File directory = new File(MyDrive.proxy.getFolder(), MyDrive.proxy.getDrivePath());
		ArrayList<File> files = FileSystemUtils.getDirectoryContents(directory);
		Iterator<File> iter = files.iterator();
		while (iter.hasNext()) {
			File f = iter.next();
			
			if (f.isDirectory()) {
				iter.remove();
			}
		}
		String[] paths = new String[files.size()];
		String[] digests = new String[files.size()];
		for (int i = 0; i < files.size(); i++) {
			paths[i] = directory.toPath().relativize(files.get(i).toPath()).toString();
			digests[i] = FileSystemUtils.getFileDigest(files.get(i));
		}
		return new PacketDirectoryStructure(paths, digests);
	}
	

}
