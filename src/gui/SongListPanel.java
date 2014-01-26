package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.DropMode;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import view.KeyboardHeroConstants;
import view.MenuButton;
import net.miginfocom.swing.MigLayout;
import model.KeyboardHeroFontModel;
import model.PersistenceHandler;
import model.Track;
import controller.player.Playlist;

public class SongListPanel extends GHPanel {

	private Playlist playlist;
	private JList<Track> songlist;
	private JButton mainMenuButton;
	private ListAction selectAction;
	private PlaylistTransferHandler transferHandler;
	private JScrollPane scrollPane;

	public SongListPanel() {

		this.setLayout(new MigLayout("insets 50 200 50 200, fill"));
		this.setBackground(Color.WHITE);
		
		JLabel titleLabel = new JLabel("Select a Track");
		titleLabel.setFont(KeyboardHeroFontModel.getInstance().getFont(KeyboardHeroFontModel.FONT_NIGHTMARE).deriveFont(82f));
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setForeground(new Color(KeyboardHeroConstants.FONT_COLOR_PRIMARY));
		this.add(titleLabel, "wrap, grow");
		
		this.fillPlaylist();
		
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

		mainMenuButton = new MenuButton("Back to menu", new Color(KeyboardHeroConstants.FONT_COLOR_SECONDARY));
		mainMenuButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				getNavigationController().popPanel();
			}
		});
		
		this.add(scrollPane, "wrap, growx, pushy, growy");
		
		this.add(mainMenuButton, "growx, height 60!");

	}
	
	public void fillPlaylist(){
		playlist = PersistenceHandler.loadPlaylist();

		if(PlayerController.getInstance().isRecording()){
			songlist = new MenuSongList<Track>(playlist);
			transferHandler = new PlaylistTransferHandler(playlist);
			songlist.setDropMode(DropMode.ON);
			songlist.setTransferHandler(transferHandler);
		}
		else{
			Playlist gamePlaylist = new Playlist("Game Playlist");
			for(Track track: playlist.getTracks()){
				if(track.getStrokeSet() != null){
					gamePlaylist.addTrack(track);
				}
			}
			playlist = gamePlaylist;
			songlist = new MenuSongList<Track>(playlist);
		}
		
		songlist.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
		scrollPane = new JScrollPane(songlist);
		scrollPane.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
			
	}
}
