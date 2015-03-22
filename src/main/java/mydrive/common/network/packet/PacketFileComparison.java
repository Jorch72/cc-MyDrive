package mydrive.common.network.packet;

import java.io.UnsupportedEncodingException;

import io.netty.buffer.ByteBuf;

public class PacketFileComparison extends PacketFilePath {
	public String md5Digest;
	
	public PacketFileComparison() {
		
	}
	
	@Override
	public void toBytes(ByteBuf buf) {
		super.toBytes(buf);
		byte[] digestStr = md5Digest.getBytes();
		buf.writeInt(digestStr.length);
		buf.writeBytes(digestStr);
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		super.fromBytes(buf);
		int digestLen = buf.readInt();
		byte[] digestStr = new byte[digestLen];
		buf.readBytes(digestStr, 0, digestLen);
		try {
			md5Digest = new String(digestStr, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
}
