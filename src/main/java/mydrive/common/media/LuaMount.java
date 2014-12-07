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
	
	protected String name;
	
	public LuaMount(String name) {
		this.name = name;
	}
	
	@Override
    public boolean exists(String path) throws IOException {
            File file = new File(new File(MyDrive.SERVER_DRIVES_PATH, name), path);
            MDLog.info("Mount for player %s checking existence of file %s.", this.name, file.toString());
            return file.exists();
    }

    @Override
    public boolean isDirectory(String path) throws IOException {
            File file = new File(new File(MyDrive.SERVER_DRIVES_PATH, name), path);
            MDLog.info("Mount for player %s checking if file %s is a directory.", this.name, file.toString());
            return file.isDirectory();
    }

    @Override
    public void list(String path, List<String> contents) throws IOException {
            File directory = new File(new File(MyDrive.SERVER_DRIVES_PATH, name), path);
            MDLog.info("Mount for player %s listing files in directory %s", this.name, directory.toString());
            for (File file : directory.listFiles()) {
                    contents.add(file.getName());
            }
    }

    @Override
    public long getSize(String path) throws IOException {
            File file = new File(new File(MyDrive.SERVER_DRIVES_PATH, name), path);
            MDLog.info("Mount for player %s checking size of file %s", this.name, file.toString());
            return file.length();
    }

    @Override
    public InputStream openForRead(String path) throws IOException {
            File file = new File(new File(MyDrive.SERVER_DRIVES_PATH, name), path);
            MDLog.info("Mount for player %s opening of file %s", this.name, file.toString());
            return new FileInputStream(file);
    }
	
}