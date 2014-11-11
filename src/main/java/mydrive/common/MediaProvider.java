package mydrive.common;

import mydrive.common.item.MyDiskItem;
import net.minecraft.item.ItemStack;
import dan200.computercraft.api.media.IMedia;
import dan200.computercraft.api.media.IMediaProvider;

public class MediaProvider implements IMediaProvider {

	@Override
	public IMedia getMedia(ItemStack stack) {
		if (stack.getItem() instanceof MyDiskItem) {
			return (IMedia) stack.getItem();
		}
		return null;
	}

}
