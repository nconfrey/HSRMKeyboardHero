package gui;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.io.File;
import java.util.List;

import javax.swing.TransferHandler;

import model.Track;
import controller.player.Playlist;


public class PlaylistTransferHandler extends TransferHandler {
	
	private Playlist playlist;
	
	public PlaylistTransferHandler(Playlist playlist) {
		this.playlist = playlist;
	}
	
	@Override
	public boolean importData(TransferSupport info) {
		
		if(!canImport(info)) {
			return false;
		}
		
		Transferable transfer = info.getTransferable();
		try {
			Object data = transfer.getTransferData(DataFlavor.javaFileListFlavor);
			List<File> list = extracted(data);
			for(File file : list) {
				importTrack(file);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
		
	}
	
	@Override
	public boolean canImport(TransferSupport info) {
		boolean copySupported;
		
		if(!info.isDrop()) {
			return false;
		}
		
		if(!info.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
			return false;
		}
		
		copySupported = (COPY & info.getSourceDropActions()) == COPY;
		if (copySupported) {
			info.setDropAction(COPY);
			return true;
		}
		
		return false;
	}
	
	private void importTracks(File path) {
        for (File file : path.listFiles()) {
            importTrack(file);
        }
    }

    private void importTrack(File file) {
        if (file.isDirectory()) {
            importTracks(file);
        } else {
            if (file.getName().endsWith(".mp3")) {
            	Track track = new Track(file.getAbsolutePath());
				this.playlist.addTrack(track);
            }
        }
    }

	@SuppressWarnings("unchecked")
	private List<File> extracted(Object data) {
		return (List<File>)data;
	}
	
}

