package mydrive.common.handler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.HashMap;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import mydrive.MyDrive;
import mydrive.common.network.PacketHandler;
import mydrive.common.network.packet.PacketFileContents;
import mydrive.common.network.packet.PacketFileHeader;
import mydrive.common.util.FileSystemUtils;
import mydrive.common.util.MDLog;

public class OutgoingFileHandler {

	private String path;
	private int chunks;
	private HashMap<Integer, byte[]> data = new HashMap<Integer, byte[]>();
	private int chunksSent = 0;
	private boolean headerSent = false;
	//file transfer request ID
	private int request;
	private boolean valid = false;
	
	public OutgoingFileHandler(String path, int request) {
		this.path = path;
		File f = new File(new File(MyDrive.proxy.getFolder(), MyDrive.proxy.getDrivePath()), path);
		MDLog.debug("Requested File: %s", f.getAbsolutePath());
		byte[] allData = null;
		try {
			allData = Files.readAllBytes(f.toPath());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (allData != null) {
			valid = true;
			int chunkSize = MyDrive.Config.chunkSize;
			chunks = (int) Math.ceil((double)allData.length / (double)chunkSize);
			MDLog.debug("Chunks: %d; Data Len: %d, Chunk Size: %d", chunks, allData.length, chunkSize);
			
			for (int i = 0; i < chunks; i++) {
				chunkSize = Math.min(allData.length - i * MyDrive.Config.chunkSize, MyDrive.Config.chunkSize);
				MDLog.debug("Copying chunk of size %d", chunkSize);
				byte[] chunk = Arrays.copyOfRange(allData, i * MyDrive.Config.chunkSize, i * MyDrive.Config.chunkSize + chunkSize - 1);
				this.data.put(i, chunk);
			}
			if (chunks == 0) {
				MDLog.debug("File was successfully opened, but is empty.");
				chunks = 1;
				this.data.put(0, new byte[0]);
			}
		}
	}
	
	public PacketFileHeader getHeader() {
		if (this.valid) {
			return new PacketFileHeader(this.path, this.request, this.chunks);
		} else {
			return new PacketFileHeader(this.path, this.request, 0);
		}	
	}
	
	public PacketFileContents getNextPacket() {
		if (!this.data.isEmpty()) {
			byte[] chunk = this.data.remove(0);
			PacketFileContents packet = new PacketFileContents(this.path, this.request, this.chunksSent, chunk);
			this.chunksSent++;
			return packet;
		} else {
			return null;
		}
	}
}
