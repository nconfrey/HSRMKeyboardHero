package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import model.PersistenceHandler;
import model.Track;
import controller.player.Playlist;

public class SongViewer extends GHPanel {

	private Playlist playlist;
	private JList<Track> songlist;
	private JButton backToMenu;
	private ListAction selectAction;

	public SongViewer() {

		this.setLayout(new BorderLayout());

		playlist = PersistenceHandler.loadPlaylist();
		songlist = new JList<Track>(playlist);

		selectAction = new ListAction(songlist, new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (songlist.getSelectedValue() != null) {
					PlayerController.getInstance().setTrack(
							songlist.getSelectedValue());
					GamePanel gameFrame = new GamePanel();
					getNavigationController().pushPanel(gameFrame);
				}
			}
		});

		backToMenu = new JButton("Back to menu");
		backToMenu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				getNavigationController().popPanel();
			}
		});

		this.add(songlist, BorderLayout.CENTER);
		this.add(backToMenu, BorderLayout.SOUTH);

	}
}
