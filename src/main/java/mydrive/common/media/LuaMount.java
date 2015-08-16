package mydrive.common.media;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import mydrive.MyDrive;
import mydrive.common.util.MDLog;
import dan200.computercraft.api.filesystem.IMount;

public class LuaMount implements IMount {
	
	protected File directory;
	
	public LuaMount(String name) {
		this.directory = new File(new File(MyDrive.proxy.getFolder(), MyDrive.proxy.getDrivePath()), name);
		MDLog.debug("Initialized new LuaMount for %s", directory.toString());
	}
	
	@Override
    public boolean exists(String path) throws IOException {
            File file = new File(this.directory, path);
            return file.exists();
    }

    @Override
    public boolean isDirectory(String path) throws IOException {
            File file = new File(this.directory, path);
            return file.isDirectory();
    }

    @Override
    public void list(String path, List<String> contents) throws IOException {
            File directory = new File(this.directory, path);
            for (File file : directory.listFiles()) {
                    contents.add(file.getName());
            }
    }

    @Override
    public long getSize(String path) throws IOException {
            File file = new File(this.directory, path);
            return file.length();
    }

    @Override
    public InputStream openForRead(String path) throws IOException {
            File file = new File(this.directory, path);
            return new FileInputStream(file);
    }
	
}