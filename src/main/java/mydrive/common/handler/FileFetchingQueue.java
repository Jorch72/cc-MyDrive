package mydrive.common.handler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import mydrive.MyDrive;

import org.apache.commons.io.FileUtils;

public class FileFetchingQueue {
	
	ArrayList<File> files = new ArrayList<File>();
	Path directory;

	public FileFetchingQueue(Path directory, ArrayList<File> files) {
		this.directory = directory;
		this.files = files;
	}
	
	public String getNextPath() {
		if (!this.files.isEmpty() && FileUtils.sizeOfDirectory(this.directory.toFile()) <= MyDrive.Config.mountSize) {
			Path p = this.files.remove(0).toPath();
			File parent = p.getParent().toFile();
			if (!parent.exists()) {
				parent.mkdirs();
			}
			return this.directory.relativize(p).toString();
		}
		return null;
	}
}
