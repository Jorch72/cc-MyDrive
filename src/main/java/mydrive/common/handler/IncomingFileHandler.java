package mydrive.common.handler;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Arrays;

import mydrive.MyDrive;
import mydrive.common.util.MDLog;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.lang3.ArrayUtils;

public class IncomingFileHandler {
	
	private String path;
	private int chunks;
	private int size = 0;
	private HashMap<Integer, byte[]> data = new HashMap<Integer, byte[]>();

	public IncomingFileHandler(String path, int chunks) {
		this.path = path;
		this.chunks = chunks;
	}
	
	public boolean receiveChunk(int chunkID, byte[] contents) {
		data.put(chunkID, contents);
		this.size += contents.length;
		File dir = new File(new File(MyDrive.proxy.getFolder(), MyDrive.proxy.getDrivePath()), new File(path).toPath().subpath(0,1).toString());
		if (this.size + FileUtils.sizeOfDirectory(dir) >= MyDrive.Config.mountSize) {
			return false;
		}
		for (int i = 0; i < this.chunks; i++) {
			if (!data.containsKey(i)) {
				return false;
			}
		}
		//All chunks accounted for, output file.
		byte[] output = new byte[this.size];
		for (int i = 0; i < this.chunks; i++) {
			byte[] current = data.get(i);
			System.arraycopy(current, 0, output, i * MyDrive.Config.chunkSize, current.length);
		}

		try {
			Files.write(new File(new File(MyDrive.proxy.getFolder(), MyDrive.proxy.getDrivePath()), path).toPath(), output);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}
}
