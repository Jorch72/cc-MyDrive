package mydrive.common.handler;

import java.io.File;
import java.io.IOException;
import java.nio.file.attribute.*;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import static java.nio.file.LinkOption.*;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.HashMap;
import java.util.Map;

import mydrive.MyDrive;
import mydrive.common.network.PacketHandler;
import mydrive.common.network.packet.PacketFileChanged;
import mydrive.common.util.MDLog;
import static java.nio.file.StandardWatchEventKinds.*;
import cpw.mods.fml.relauncher.Side;

public class FileChangesHandler {

	private WatchService watcher;
	private final Path mainDir = new File(MyDrive.proxy.getFolder(), MyDrive.proxy.getDrivePath()).toPath();
	private HashMap<String, Long> fileTimes = new HashMap();
	private final Map<WatchKey, Path> keys;
	private boolean trace = false;

	public FileChangesHandler() {
		this.keys = new HashMap<WatchKey, Path>();
		try {
			this.watcher = FileSystems.getDefault().newWatchService();
		} catch (IOException e) {
			this.watcher = null;
			e.printStackTrace();
		}
	}
	
	private void register(Path dir) throws IOException {
		WatchKey key = dir.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
		if (trace) {
            Path prev = keys.get(key);
            if (prev == null) {
                System.out.format("register: %s\n", dir);
            } else {
                if (!dir.equals(prev)) {
                    System.out.format("update: %s -> %s\n", prev, dir);
                }
            }
        }
        keys.put(key, dir);
	}
	
	private void registerAll(final Path start) throws IOException {
		Files.walkFileTree(start, new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
				register(dir);
				return FileVisitResult.CONTINUE;
			}
		});
	}
    
	public void init() {
		if (this.watcher != null) {
			try {
				registerAll(mainDir);
				MDLog.debug("Spinning off watcher thread");
				(new WatcherThread()).start();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
    
	public static final FileChangesHandler instance = new FileChangesHandler();


	private class WatcherThread extends Thread {

		@Override
		public void run() {
			MDLog.debug("Watcher thread started");
			for (;;) {

	            // wait for key to be signaled
				WatchKey key;
				try {
					sleep(250);
					key = watcher.take();
				} catch (InterruptedException x) {
					return;
				}
				
				Path dir = keys.get(key);
				if (dir == null) {
					MDLog.warn("WatchKey not recognized!");
					continue;
				}

				for (WatchEvent<?> event: key.pollEvents()) {
					WatchEvent.Kind kind = event.kind();
					MDLog.debug("New event, type %s", kind.name());

					if (kind == OVERFLOW) {
						continue;
					}

					//The filename is the context of the event.
					WatchEvent<Path> ev = (WatchEvent<Path>)event;
					Path filename = ev.context();
					Path child = dir.resolve(filename);
					
					if (kind == ENTRY_CREATE) {
						try {
							if (Files.isDirectory(child, NOFOLLOW_LINKS)) {
								registerAll(child);
							}
						} catch (IOException x) {
	                        x.printStackTrace();
	                    }
					}

					int changeType = 0;
					if (kind == ENTRY_DELETE) {
						changeType = 1;
					} else if (kind == ENTRY_MODIFY || kind == ENTRY_CREATE) {
						long timestamp = child.toFile().lastModified();
						if(fileTimes.containsKey(child.toString())) {
							if (fileTimes.get(child.toString()) == timestamp) {
								continue;
							}
						}
						fileTimes.put(child.toString(), timestamp);
						changeType = 2;
					}

					MDLog.debug("Modified: %d", child.toFile().lastModified());

					MDLog.debug("Sending packet to server with type %d for file %s", changeType, mainDir.relativize(child).toString());
					PacketFileChanged packet = new PacketFileChanged(mainDir.relativize(child).toString(), changeType);
					PacketHandler.INSTANCE.sendToServer(packet);
				}

				//Reset the key -- this step is critical if you want to receive
				//further watch events. If the key is no longer valid, the directory
				//is inaccessible so exit the loop.
				boolean valid = key.reset();
				if (!valid) {
					break;
				}
			}
		}

	}
}
