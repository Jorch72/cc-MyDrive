package mydrive.common;

import java.io.File;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import mydrive.MyDrive;
import mydrive.common.handler.PlayerPresenceHandler;
import mydrive.common.item.MyDiskItem;
import mydrive.common.network.PacketHandler;

public class CommonProxy {

	public void preInit() {
		FMLCommonHandler.instance().bus().register(PlayerPresenceHandler.instance);
		registerItems();
	}
	
	public void init() {
		registerRecipes();
		PacketHandler.init();
		FileManager.instance.init();
	}
	
	private void registerItems() {
		MyDrive.Items.diskItem = new MyDiskItem();
		GameRegistry.registerItem(MyDrive.Items.diskItem, "myDiskItem", "mydrive");
	}

	private void registerRecipes() {
		ItemStack myDisk = new ItemStack(MyDrive.Items.diskItem);
		ItemStack paper = new ItemStack((Item)Item.itemRegistry.getObject("paper"));
		ItemStack diamond = new ItemStack((Item)Item.itemRegistry.getObject("diamond"));
		ItemStack redstone = new ItemStack((Item)Item.itemRegistry.getObject("redstone"));
		GameRegistry.addRecipe(myDisk,
				" r ",
				" p ",
				" d ",
		        'p', paper, 'd', diamond, 'r', redstone);
	}
	
	public File getFolder() {
		return MinecraftServer.getServer().getFile("");
	}
	
	public String getDrivePath() {
		return "mods/mydrive/cache";
	}
}
