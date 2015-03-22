package mydrive.client;

import java.io.File;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.client.Minecraft;
import mydrive.common.CommonProxy;

public class ClientProxy extends CommonProxy {

	@Override
	public File getFolder() {
		return Minecraft.getMinecraft().mcDataDir;
	}
	
	@Override
	public String getDrivePath() {
		if (FMLCommonHandler.instance().getEffectiveSide()==Side.SERVER) {
			return "mods/mydrive/cache";
		}
		return "mods/mydrive/files";
	}
}
