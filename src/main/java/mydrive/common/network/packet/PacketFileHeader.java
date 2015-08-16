package mydrive.common.network.packet;

import java.io.UnsupportedEncodingException;

import io.netty.buffer.ByteBuf;

public class PacketFileHeader extends PacketFileRequest {
	public int chunkCount;
	public boolean isDirectory;
	
	public PacketFileHeader() {
		
	}
	
	public PacketFileHeader(String path, int requestID, int numChunks, boolean directory) {
		this.filePath = path;
		this.request = requestID;
		this.chunkCount = numChunks;
		this.isDirectory = directory;
	}
	
	@Override
	public void toBytes(ByteBuf buf) {
		super.toBytes(buf);
		buf.writeInt(chunkCount);
		buf.writeBoolean(isDirectory);
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		super.fromBytes(buf);
		chunkCount = buf.readInt();
		isDirectory = buf.readBoolean();
	}
}
