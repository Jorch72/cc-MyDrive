package mydrive.common.item;

import dan200.computercraft.api.filesystem.IMount;
import dan200.computercraft.api.media.IMedia;
import mydrive.MyDrive;
import mydrive.common.media.LuaMount;
import mydrive.common.media.LuaMountWriteable;
import mydrive.common.util.MDLog;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class MyDiskItem extends Item implements IMedia {
	
	public MyDiskItem() {
		super();
		setMaxDamage(0);
		setMaxStackSize(1);
		setCreativeTab(CreativeTabs.tabMisc);
	}
	
	@Override
	public void registerIcons(IIconRegister register) {
		register.registerIcon("mydrive:mydisk");
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return "myDiskItem";
	}
	
	@Override
	public void onCreated(ItemStack stack, World world, EntityPlayer player) {
		MDLog.info("New disk created by player %s.", player.getCommandSenderName());
		stack.stackTagCompound = new NBTTagCompound();
		stack.stackTagCompound.setString("owner", player.getCommandSenderName());
	}

	@Override
	public String getLabel(ItemStack stack) {
		return stack.stackTagCompound.getString("owner") + "'s files";
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
		if (MyDrive.Config.writeableMounts) {
			MDLog.info("New read-write mount created for player %s.", stack.stackTagCompound.getString("owner"));
			return new LuaMountWriteable(stack.stackTagCompound.getString("owner"));
		} else {
			MDLog.info("New read-only mount created for player %s.", stack.stackTagCompound.getString("owner"));
			return new LuaMount(stack.stackTagCompound.getString("owner"));
		}
	}

}
