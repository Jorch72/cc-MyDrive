package mydrive.common;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.registry.GameRegistry;
import mydrive.MyDrive;
import mydrive.common.item.MyDiskItem;

public class CommonProxy {

	public void preInit() {
		registerItems();
	}
	
	public void init() {
		registerRecipes();
	}
	
	private void registerItems() {
		MyDrive.Items.diskItem = new MyDiskItem();
		GameRegistry.registerItem(MyDrive.Items.diskItem, "myDiskItem");
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
}
