package mydrive.common.network.packet;

import io.netty.buffer.ByteBuf;

public class PacketFileRequest extends PacketFilePath {
	public int request;
	
	public PacketFileRequest() {
		
	}

	public PacketFileRequest(String path, int requestID) {
		this.filePath = path;
		this.request = requestID;
	}
	
	@Override
	public void toBytes(ByteBuf buf) {
		super.toBytes(buf);
		buf.writeInt(this.request);

	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		super.fromBytes(buf);
		this.request = buf.readInt();
	}
}
