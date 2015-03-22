package mydrive.common.network.packet;

import io.netty.buffer.ByteBuf;

import java.io.UnsupportedEncodingException;

import mydrive.common.network.PacketGeneric;

public class PacketFilePath extends PacketGeneric {
	public String filePath;
	
	@Override
	public void toBytes(ByteBuf buf) {
		super.toBytes(buf);
		byte[] pathStr = filePath.getBytes();
		buf.writeInt(pathStr.length);
		buf.writeBytes(pathStr);
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		super.fromBytes(buf);
		int pathlen = buf.readInt();
		byte[] pathStr = new byte[pathlen];
		buf.readBytes(pathStr, 0, pathlen);
		try {
			filePath = new String(pathStr, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

}
