package mydrive.common.network;

import io.netty.buffer.ByteBuf;

import java.io.UnsupportedEncodingException;

public class PacketComparisonResponse extends PacketFilePath {
	public boolean response;
	
	public PacketComparisonResponse() {
		
	}
	
	public PacketComparisonResponse(String path, boolean response) {
		this.filePath = path;
		this.response = response;
	}
	
	@Override
	public void toBytes(ByteBuf buf) {
		super.toBytes(buf);
		buf.writeBoolean(response);

	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		super.fromBytes(buf);
		response = buf.readBoolean();
	}
}
