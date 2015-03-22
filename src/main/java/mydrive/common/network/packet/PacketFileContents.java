package mydrive.common.network.packet;

import java.io.UnsupportedEncodingException;

import io.netty.buffer.ByteBuf;

public class PacketFileContents extends PacketFileRequest {
	public byte[] contents;
	public int chunkNum;
	
	public PacketFileContents() {
		
	}
	
	public PacketFileContents(String path, int requestID, int chunk, byte[] data) {
		this.filePath = path;
		this.request = requestID;
		this.chunkNum = chunk;
		this.contents = data;
	}
	
	@Override
	public void toBytes(ByteBuf buf) {
		super.toBytes(buf);
		buf.writeInt(contents.length);
		buf.writeBytes(contents);
		buf.writeInt(chunkNum);
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		super.fromBytes(buf);
		int contentsLen = buf.readInt();
		contents = new byte[contentsLen];
		buf.readBytes(contents, 0, contentsLen);
		chunkNum = buf.readInt();
	}
}
