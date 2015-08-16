package mydrive.common.network.packet;

import io.netty.buffer.ByteBuf;

public class PacketFileChanged extends PacketFilePath {
	public int type;
	
	public PacketFileChanged() {
		
	}

	public PacketFileChanged(String path, int changeType) {
		this.filePath = path;
		this.type = changeType;
	}
	
	@Override
	public void toBytes(ByteBuf buf) {
		super.toBytes(buf);
		buf.writeInt(this.type);

	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		super.fromBytes(buf);
		this.type = buf.readInt();
	}
}
