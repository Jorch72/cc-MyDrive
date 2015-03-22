package mydrive.common.network.packet;

public class PacketFileContinue extends PacketFileRequest {

	public PacketFileContinue() {
		
	}
	
	public PacketFileContinue(String path, int request) {
		this.filePath = path;
		this.request = request;
	}
}
