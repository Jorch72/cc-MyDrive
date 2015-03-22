package mydrive.common.network;

import java.io.UnsupportedEncodingException;

import io.netty.buffer.ByteBuf;

public class PacketFileHeader extends PacketFileRequest {
	public int chunkCount;
	
	public PacketFileHeader() {
		
	}
	
	public PacketFileHeader(String path, int requestID, int numChunks) {
		this.filePath = path;
		this.request = requestID;
		this.chunkCount = numChunks;
	}
	
	@Override
	public void toBytes(ByteBuf buf) {
		super.toBytes(buf);
		buf.writeInt(chunkCount);
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		super.fromBytes(buf);
		chunkCount = buf.readInt();
	}
}
