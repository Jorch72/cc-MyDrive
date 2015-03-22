package mydrive.common.network;

import io.netty.buffer.ByteBuf;

import java.io.UnsupportedEncodingException;

public class PacketDirectoryStructure extends PacketGeneric {
	public String[] paths;
	public String[] md5Digests;
	
	public PacketDirectoryStructure() {
		
	}
	
	public PacketDirectoryStructure(String[] paths, String[] digests) {
		this.paths = paths.clone();
		this.md5Digests = digests.clone();
	}
	
	@Override
	public void toBytes(ByteBuf buf) {
		super.toBytes(buf);
		buf.writeInt(paths.length);
		for (int i = 0; i < paths.length; i++) {
			byte[] path = paths[i].getBytes();
			buf.writeInt(path.length);
			buf.writeBytes(path);
		}
		buf.writeInt(md5Digests.length);
		for (int i = 0; i < md5Digests.length; i++) {
			byte[] digest = md5Digests[i].getBytes();
			buf.writeInt(digest.length);
			buf.writeBytes(digest);
		}
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		super.fromBytes(buf);
		int numPaths = buf.readInt();
		this.paths = new String[numPaths];
		for (int i = 0; i < numPaths; i++) {
			int pathLen = buf.readInt();
			byte[] path = new byte[pathLen];
			buf.readBytes(path, 0 ,pathLen);
			try {
				paths[i] = new String(path, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		int numDigests = buf.readInt();
		this.md5Digests = new String[numDigests];
		for (int i = 0; i < numDigests; i++) {
			int digestLen = buf.readInt();
			byte[] digest = new byte[digestLen];
			buf.readBytes(digest, 0 ,digestLen);
			try {
				md5Digests[i] = new String(digest, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
	}
}
