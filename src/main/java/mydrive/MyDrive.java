package mydrive;

import mydrive.common.CommonProxy;
import mydrive.common.item.MyDiskItem;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod( modid = "MyDrive", name = "MyDrive", version = "0.0.1")
public class MyDrive {

	public static class Items {
		public static MyDiskItem diskItem;
	}
	
	public static String SERVER_DRIVES_PATH;
	public static String CLIENT_FILES_PATH;
	
	@Instance(value = "MyDrive")
	public static MyDrive instance;
	
	@SidedProxy (clientSide = "mydrive.client.ClientProxy", serverSide = "mydrive.common.CommonProxy")
	public static CommonProxy proxy;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent evt) {
		SERVER_DRIVES_PATH = "mods/mydrive/cache";
		CLIENT_FILES_PATH = "mods/mydrive/files";
	}
	
	@EventHandler
	public void init(FMLInitializationEvent evt) {
		proxy.init();

	}
}
