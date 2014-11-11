package mydrive.common;

import mydrive.MyDrive;
import mydrive.common.item.MyDiskItem;

public class CommonProxy {

	public void init() {
		
	}
	
	private void registerItems() {
		MyDrive.Items.diskItem = new MyDiskItem();
	}

}
