package mydrive.common;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import mydrive.MyDrive;
import mydrive.common.handler.FileFetchingQueue;
import mydrive.common.handler.IncomingFileHandler;
import mydrive.common.handler.OutgoingFileHandler;
import mydrive.common.network.PacketDirectoryComparison;
import mydrive.common.network.PacketFileContents;
import mydrive.common.network.PacketFileContinue;
import mydrive.common.network.PacketFileHeader;
import mydrive.common.network.PacketFileRequest;
import mydrive.common.network.PacketHandler;
import mydrive.common.util.FileSystemUtils;
import mydrive.common.util.MDLog;

public class FileManager {

	//used server-side to track file requests sent to client.
	private int fileRequestID = 0;
	private HashMap<Integer, IncomingFileHandler> incomingHandlers = new HashMap<Integer, IncomingFileHandler>();
	private HashMap<Integer, OutgoingFileHandler> outgoingHandlers = new HashMap<Integer, OutgoingFileHandler>();
	private HashMap<Integer, FileFetchingQueue> requestQueues = new HashMap<Integer, FileFetchingQueue>();
	
	public static final FileManager instance = new FileManager();
	
	public void init() {
		MDLog.debug("Initializing FileManager");
		MDLog.debug("Folder path is %s", MyDrive.proxy.getFolder());
		MDLog.debug("Drive path is %s", MyDrive.proxy.getDrivePath());
	}
	
	public void onPlayerJoin(EntityPlayer player) {
		File playerFolder = new File(new File(MyDrive.proxy.getFolder(), MyDrive.proxy.getDrivePath()), player.getUniqueID().toString());
		if (!playerFolder.exists() || !playerFolder.isDirectory()) {
			MDLog.debug("Creating player folder for first time");
			playerFolder.mkdirs();
		}
		String digest = FileSystemUtils.calcMD5HashForDir(playerFolder);
		MDLog.debug("Sending Directory Comparison with digest %s", digest);
		PacketDirectoryComparison packet = new PacketDirectoryComparison(digest);
		PacketHandler.INSTANCE.sendTo(packet, (EntityPlayerMP) player);
	}
	
	public PacketFileRequest onStartReceivingFile(String path, int request, int chunks) {
		if (chunks > 0) {
			IncomingFileHandler handler = new IncomingFileHandler(path, chunks);
			incomingHandlers.put(request, handler);
			return new PacketFileContinue(path, request);
		} else {
			//file was no good for whatever reason
			PacketFileRequest packet = nextFile(requestQueues.get(request));
			requestQueues.remove(request);
			return packet;
		}
	}
	
	public PacketFileRequest onReceiveFileChunk(String path, int request, int chunkNum, byte[] data) {
		if (incomingHandlers.containsKey(request)) {
			if (incomingHandlers.get(request).receiveChunk(chunkNum, data)) {
				//entire file was received.
				PacketFileRequest packet = nextFile(requestQueues.get(request));
				requestQueues.remove(request);
				incomingHandlers.remove(request);
				return packet;
			}
		}
		return null;
	}
	
	public PacketFileContents getNextFileContentsPacket(int request) {
		if (outgoingHandlers.containsKey(request)) {
			PacketFileContents result = outgoingHandlers.get(request).getNextPacket();
			if (result == null) {
				outgoingHandlers.remove(request);
			}
			return result;
		}
		return null;
	}
	
	public PacketFileHeader onFileRequest(String path, int requestID) {
		OutgoingFileHandler handler = new OutgoingFileHandler(path, requestID);
		outgoingHandlers.put(requestID, handler);
		return handler.getHeader();
	}
	
	public PacketFileRequest onDirectoryContentsMismatch(String playerID, File directory, String[] paths, String[] digests) {
		//remove old files that should no longer exist
		String[] current = FileSystemUtils.getRelativeFilePathsForDirectoryContents(directory);
		for (int i = 0; i < current.length; i++) {
			boolean fileExists = false;
			for (int j = 0; j < paths.length; j++) {
				if (current[i] == paths[j]) {
					fileExists = true;
				}
			}
			if (!fileExists) {
				File f = new File(directory, paths[i]);
				MDLog.debug("Removing old file %s", f.getAbsolutePath());
				f.delete();
			}
		}
		
		ArrayList<File> files = new ArrayList<File>();
		for (int i = 0; i < paths.length; i++) {
			File f = new File(directory, paths[i]);
			if (!f.exists() || FileSystemUtils.getFileDigest(f) != digests[i]) {
				MDLog.debug("Adding new file for download: %s", f.getAbsolutePath());
				files.add(f);
			}
		}
		FileFetchingQueue queue = new FileFetchingQueue(directory.toPath(), files);
		return nextFile(queue);
	}
	
	private PacketFileRequest nextFile(FileFetchingQueue queue) {
		String path = queue.getNextPath();
		if (path != null) {
			PacketFileRequest packet = new PacketFileRequest(path, this.fileRequestID);
			requestQueues.put(this.fileRequestID, queue);
			this.fileRequestID++;
			return packet;
		} else {
			//done fetching files!
			return null;
		}
	}
}
