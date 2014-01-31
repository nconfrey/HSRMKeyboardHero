/**
 * Handles Drag and Drop for SongList
 * 
 * @author Simon Seyer
 * @author Martin Juhasz
 * @author Julia Kraft
 * @author Moritz Moeller
 * 
 */
package controller;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.io.File;
import java.util.List;

import javax.swing.TransferHandler;

import model.Playlist;
import model.Track;

public class PlaylistTransferHandler extends TransferHandler {

	private Playlist playlist;

	/**
	 * Instantiates a new playlist transfer handler.
	 * 
	 * @param playlist the playlist
	 */
	public PlaylistTransferHandler(Playlist playlist) {
		this.playlist = playlist;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.TransferHandler#importData(javax.swing.TransferHandler.
	 * TransferSupport)
	 */
	@Override
	public boolean importData(TransferSupport info) {

		if (!canImport(info)) {
			return false;
		}

		Transferable transfer = info.getTransferable();
		try {
			Object data = transfer
					.getTransferData(DataFlavor.javaFileListFlavor);
			List<File> list = extracted(data);
			for (File file : list) {
				importTrack(file);
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return true;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.TransferHandler#canImport(javax.swing.TransferHandler.
	 * TransferSupport)
	 */
	@Override
	public boolean canImport(TransferSupport info) {
		boolean copySupported;

		if (!info.isDrop()) {
			return false;
		}

		if (!info.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
			return false;
		}

		copySupported = (COPY & info.getSourceDropActions()) == COPY;
		if (copySupported) {
			info.setDropAction(COPY);
			return true;
		}

		return false;
	}

	/**
	 * Import tracks.
	 * 
	 * @param path the path
	 */
	private void importTracks(File path) {
		for (File file : path.listFiles()) {
			importTrack(file);
		}
	}

	/**
	 * Import track.
	 * 
	 * @param file the file
	 */
	private void importTrack(File file) {
		if (file.isDirectory()) {
			importTracks(file);
		} else {
			if (file.getName().endsWith(".mp3")
					|| file.getName().endsWith(".MP3")) {
				Track track = new Track(file.getAbsolutePath());
				this.playlist.addTrack(track);
			}
		}
	}

	/**
	 * Extracted.
	 * 
	 * @param data the data
	 * @return the list
	 */
	@SuppressWarnings("unchecked")
	private List<File> extracted(Object data) {
		return (List<File>) data;
	}

}
