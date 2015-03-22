package mydrive.common.media;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

import mydrive.MyDrive;
import mydrive.common.util.MDLog;
import dan200.computercraft.api.filesystem.IWritableMount;

public class LuaMountWritable extends LuaMount implements IWritableMount {

	public LuaMountWritable(String name) {
		super(name);
	}

	@Override
	public void makeDirectory(String path) throws IOException {
		File file = new File(new File(MyDrive.proxy.getDrivePath(), this.name), path);
        file.mkdir();
	}

	@Override
	public void delete(String path) throws IOException {
		File file = new File(new File(MyDrive.proxy.getDrivePath(), this.name), path);
        file.delete();
		
	}

	@Override
	public OutputStream openForWrite(String path) throws IOException {
		File file = new File(new File(MyDrive.proxy.getDrivePath(), name), path);
        return new FileOutputStream(file);
	}

	@Override
	public OutputStream openForAppend(String path) throws IOException {
		File file = new File(new File(MyDrive.proxy.getDrivePath(), name), path);
        return Files.newOutputStream(file.toPath(), StandardOpenOption.APPEND);
	}

	@Override
	public long getRemainingSpace() throws IOException {
		// TODO Auto-generated method stub
		return 1000000;
	}

}
