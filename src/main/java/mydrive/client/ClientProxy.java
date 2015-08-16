package mydrive.client;

import java.io.File;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.client.Minecraft;
import mydrive.common.CommonProxy;
import mydrive.common.handler.FileChangesHandler;
import mydrive.common.util.MDLog;

public class ClientProxy extends CommonProxy {

	@Override
	public File getFolder() {
		return Minecraft.getMinecraft().mcDataDir;
	}
	
	@Override
	public String getDrivePath() {
		if (FMLCommonHandler.instance().getEffectiveSide()==Side.SERVER) {
			return "mods/mydrive/cache";
		} else if (Thread.currentThread().getName().startsWith("Thread")) {
			//fragile workaround for ComputerCraft's propensity for mounting things from the wrong thread on singleplayer world load.
			return "mods/mydrive/cache";
		}
		return "mods/mydrive/files";
	}
	
	@Override
	public void initFileWatcher() {
		FileChangesHandler.instance.init();
	}
}
