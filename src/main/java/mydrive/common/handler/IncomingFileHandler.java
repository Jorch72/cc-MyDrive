package mydrive.common.handler;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Arrays;

import mydrive.MyDrive;

import org.apache.commons.io.output.ByteArrayOutputStream;

public class IncomingFileHandler {
	
	private String path;
	private int chunks;
	private HashMap<Integer, byte[]> data = new HashMap<Integer, byte[]>();

	public IncomingFileHandler(String path, int chunks) {
		this.path = path;
		this.chunks = chunks;
	}
	
	public boolean receiveChunk(int chunkID, byte[] contents) {
		data.put(chunkID, contents);
		for (int i = 0; i < this.chunks; i++) {
			if (!data.containsKey(i)) {
				return false;
			}
		}
		//All chunks accounted for, output file.
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		try {
			for (int i = 0; i < this.chunks; i++) {
				stream.write(data.get(i));
			}
			Files.write(new File(new File(MyDrive.proxy.getFolder(), MyDrive.proxy.getDrivePath()), path).toPath(), stream.toByteArray());
			stream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
}
