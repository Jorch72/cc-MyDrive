package mydrive.common.network.packet;

import io.netty.buffer.ByteBuf;

import java.io.UnsupportedEncodingException;

import mydrive.common.network.PacketGeneric;

public class PacketDirectoryComparison extends PacketGeneric {
	public String md5Digest;
	
	public PacketDirectoryComparison() {
		
	}
	
	public PacketDirectoryComparison(String digest) {
		this.md5Digest = digest;
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
