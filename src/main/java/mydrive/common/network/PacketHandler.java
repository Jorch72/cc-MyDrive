package mydrive.common.network;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import mydrive.common.network.*;

public class PacketHandler {

	public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel("mydrive");
	private static int id = 0;
	
	public static void init() {
		INSTANCE.registerMessage(HandlerFileComparison.class, PacketFileComparison.class, id++, Side.CLIENT);
		INSTANCE.registerMessage(HandlerDirectoryComparison.class, PacketDirectoryComparison.class, id++, Side.CLIENT);
		INSTANCE.registerMessage(HandlerComparisonResponse.class, PacketComparisonResponse.class, id++, Side.SERVER);
		INSTANCE.registerMessage(HandlerDirectoryStructureRequest.class, PacketDirectoryStructureRequest.class, id++, Side.CLIENT);
		INSTANCE.registerMessage(HandlerDirectoryStructure.class, PacketDirectoryStructure.class, id++, Side.SERVER);
		INSTANCE.registerMessage(HandlerFileRequest.class, PacketFileRequest.class, id++, Side.CLIENT);
		INSTANCE.registerMessage(HandlerFileHeader.class, PacketFileHeader.class, id++, Side.SERVER);
		INSTANCE.registerMessage(HandlerFileContents.class, PacketFileContents.class, id++, Side.SERVER);
		INSTANCE.registerMessage(HandlerFileContinue.class, PacketFileContinue.class, id++, Side.CLIENT);
	}
}
