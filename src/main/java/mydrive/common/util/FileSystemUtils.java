package mydrive.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.SequenceInputStream;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;

import mydrive.MyDrive;

import org.apache.commons.codec.digest.DigestUtils;

public class FileSystemUtils {
	
	public static String[] getRelativeFilePathsForDirectoryContents(File directory) {
		assert (directory.isDirectory());
		ArrayList<File> files = getFilesInDirectory(directory);
		String[] result = new String[files.size()];
		for (int i = 0; i < result.length; i++) {
			result[i] = directory.toPath().relativize(files.get(i).toPath()).toString();
		}
		return result;
	}
	
	public static ArrayList<File> getDirectoryContents(File directory) {
		assert (directory.isDirectory());
		return getFilesInDirectory(directory);
	}
	
	public static String calcMD5HashForDir(File dirToHash) {
		
	    assert (dirToHash.isDirectory());
	    ArrayList<File> files = getFilesInDirectory(dirToHash);
	    String fileInformation = "";

	    for (File f : files) {
	    	
	    	fileInformation += dirToHash.toPath().relativize(f.toPath()).toString();
	    	if (f.isFile()) {
	    		fileInformation += getFileDigest(f);
	    	}
	    }

	    return DigestUtils.md5Hex(fileInformation);

	}
	
	private static ArrayList<File> getFilesInDirectory(File directory) {
		ArrayList<File> files = new ArrayList<File>();
		
		File[] fileList = directory.listFiles();
		 Arrays.sort(fileList,               // Need in reproducible order
	        new Comparator<File>() {
	            public int compare(File f1, File f2) {                       
	                return f1.getName().compareTo(f2.getName());
	            }
	        });
		
		for (File f : fileList) {
	        if (f.isDirectory()) {
	        	files.add(f);
	        	files.addAll(getFilesInDirectory(f));
	        }
	        else {
	        	files.add(f);
	        }
	    }
		
		return files;
	}
	
	public static String getFileDigest(File f) {
		String digest = null;
		byte[] allData = null;
		if (f.exists()) {
			if (f.isDirectory()) {
				digest = "dir";
			} else {
				try {
					allData = Files.readAllBytes(f.toPath());
					digest = DigestUtils.md5Hex(allData);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else {
			digest = "null";
		}

		return digest;
	}
}
