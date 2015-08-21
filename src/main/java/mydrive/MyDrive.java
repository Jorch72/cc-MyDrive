package mydrive;

import java.io.File;

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

@Mod( modid = "MyDrive", name = "MyDrive", version = "0.0.2", dependencies = "required-after:ComputerCraft")
public class MyDrive {

	public static class Items {
		public static MyDiskItem diskItem;
	}
	
	public static class Config {
		public static boolean writeableMounts = false;
		public static int chunkSize = 4096;
		public static long mountSize;
	}
	
	@Instance(value = "MyDrive")
	public static MyDrive instance;
	
	@SidedProxy (clientSide = "mydrive.client.ClientProxy", serverSide = "mydrive.common.CommonProxy")
	public static CommonProxy proxy;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent evt) {
		long time = System.nanoTime();
		MDLog.init();
		MDLog.info("Starting pre-init");
		
		Configuration configFile = new Configuration(evt.getSuggestedConfigurationFile());

		Property prop = configFile.get("general", "mountSizeLimit", 1048576);
		prop.comment = "Size limit for player folders";
		Config.mountSize = (long) prop.getInt(1048576);

		configFile.save();

		proxy.preInit();

		MDLog.info("Finished pre-init in %d ms", (System.nanoTime() - time) / 1000000);
	}
	
	@EventHandler
	public void init(FMLInitializationEvent evt) {
		long time = System.nanoTime();
		MDLog.info("Starting init");
		
		new File(proxy.getFolder(), proxy.getDrivePath()).mkdirs();
		
		proxy.init();
		
		MDLog.info("Finished init in %d ms", (System.nanoTime() - time) / 1000000);
	}
}
