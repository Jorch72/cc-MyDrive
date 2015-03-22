package mydrive.common.item;

import java.util.List;

import com.mojang.authlib.GameProfile;

import dan200.computercraft.api.filesystem.IMount;
import dan200.computercraft.api.media.IMedia;
import mydrive.MyDrive;
import mydrive.common.media.LuaMount;
import mydrive.common.media.LuaMountWritable;
import mydrive.common.util.MDLog;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
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
		return "item.mydrive.myDiskItem";
	}
	
	@Override
	public void onCreated(ItemStack stack, World world, EntityPlayer player) {
		stack.stackTagCompound = new NBTTagCompound();
		stack.stackTagCompound.setString("owner", player.getUniqueID().toString());
		stack.stackTagCompound.setString("ownerName", player.getDisplayName());
	}

	@Override
	public String getLabel(ItemStack stack) {
		return stack.stackTagCompound.getString("ownerName") + "'s files";
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
		//ensure player is online and config allows writable mounts.
		MDLog.debug("Creating data mount for disk, owner %s", stack.stackTagCompound.getString("owner"));
		GameProfile[] profiles = MinecraftServer.getServer().getConfigurationManager().func_152600_g();
		boolean playerFound = false;
		for (int i = 0; i < profiles.length; i++) {
			if (profiles[i].getId() != null) {
				MDLog.debug("Profile ID is non-null, value %s", profiles[i].getId().toString());
				if (profiles[i].getId().toString().equals(stack.stackTagCompound.getString("owner"))) {
					MDLog.debug("Found player");
					playerFound = true;
					break;
				}
			}
		}
		MDLog.debug("Config: %b; Found: %b", MyDrive.Config.writeableMounts, playerFound);
		if (MyDrive.Config.writeableMounts && playerFound) {
			MDLog.debug("Creating read/write mount");
			return new LuaMountWritable(stack.stackTagCompound.getString("owner"));
		} else {
			MDLog.debug("Creating read-only mount");
			return new LuaMount(stack.stackTagCompound.getString("owner"));
		}
	}

}
