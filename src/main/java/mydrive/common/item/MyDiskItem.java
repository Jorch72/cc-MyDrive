package mydrive.common.item;

import dan200.computercraft.api.filesystem.IMount;
import dan200.computercraft.api.media.IMedia;
import mydrive.common.media.LuaMount;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class MyDiskItem extends Item implements IMedia {
	private String owner = "";
	
	@Override
	public void onCreated(ItemStack stack, World world, EntityPlayer player) {
		this.owner = player.getCommandSenderName();
	}

	@Override
	public String getLabel(ItemStack stack) {
		return this.owner + "'s files";
	}

	@Override
	public boolean setLabel(ItemStack stack, String label) {
		return false;
	}

	@Override
	public String getAudioTitle(ItemStack stack) {
		return null;
	}

	@Override
	public String getAudioRecordName(ItemStack stack) {
		return null;
	}

	@Override
	public IMount createDataMount(ItemStack stack, World world) {
		return new LuaMount(this.owner);
	}

}
