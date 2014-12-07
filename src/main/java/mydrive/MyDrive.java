package mydrive;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import mydrive.common.CommonProxy;
import mydrive.common.item.MyDiskItem;
import mydrive.common.util.MDLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod( modid = "MyDrive", name = "MyDrive", version = "0.0.1", dependencies = "required-after:ComputerCraft")
public class MyDrive {

	public static class Items {
		public static MyDiskItem diskItem;
	}
	
	public static class Config {
		public static boolean writeableMounts;
	}
	
	public static String SERVER_DRIVES_PATH;
	public static String CLIENT_FILES_PATH;
	
	@Instance(value = "MyDrive")
	public static MyDrive instance;
	
	@SidedProxy (clientSide = "mydrive.client.ClientProxy", serverSide = "mydrive.common.CommonProxy")
	public static CommonProxy proxy;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent evt) {
		MDLog.init();
		
		SERVER_DRIVES_PATH = "mods/mydrive/cache";
		CLIENT_FILES_PATH = "mods/mydrive/files";
		
		Configuration configFile = new Configuration(evt.getSuggestedConfigurationFile());

		Property prop = configFile.get("general", "writeableMounts", false);
		prop.comment = "Server-side player mounts are writeable";
		Config.writeableMounts = prop.getBoolean(false);

		configFile.save();
		
		proxy.preInit();
	}
	
	@EventHandler
	public void init(FMLInitializationEvent evt) {
		proxy.init();
	}
}
