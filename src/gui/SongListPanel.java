package gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.DropMode;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import model.PersistenceHandler;
import model.Track;
import net.miginfocom.swing.MigLayout;
import view.KeyboardHeroConstants;
import view.MenuButton;
import view.TitleLabel;
import controller.player.Playlist;

public class SongListPanel extends GHPanel {
	
	public static final int MODE_PLAY = 0;
	public static final int MODE_RECORD = 1;
	public static final int MODE_HIGHSCORE = 2;

	private Playlist playlist;
	private JList<Track> songlist;
	private JButton mainMenuButton;
	private ListAction selectAction;
	private PlaylistTransferHandler transferHandler;
	private JScrollPane scrollPane;
	private int mode;
	private JTextField searchField;
	private JButton button;
	
	public SongListPanel() {
		this.mode = MODE_PLAY;
		init();
	}
	
	public SongListPanel(int mode) {
		this.mode = mode;
		init();
	}

	public void init() {
		this.setLayout(new MigLayout("insets 50 200 50 200, fill"));
		this.setBackground(Color.WHITE);

		JLabel titleLabel = new TitleLabel("Select a Track");
		this.add(titleLabel, "wrap, grow");

		searchField = new JTextField();
		add(searchField, "wrap, grow");
		button = new JButton("search");
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						final Playlist list = PlayerController.getInstance().getSoundCloud().search(searchField.getText());
						SwingUtilities.invokeLater(new Runnable() {
							
							@Override
							public void run() {
								songlist.setModel(list);
							}
						});
					}
				}).start();
				
			}
		});
		add(button, "wrap, grow");
		
		this.fillPlaylist();

		selectAction = new ListAction(songlist, new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				PlayerController.getInstance().stop();
				if (songlist.getSelectedValue() != null) {
					if(mode == MODE_PLAY || mode == MODE_RECORD) {
						Track selectedTrack = songlist.getSelectedValue();
						Playlist playlist = PersistenceHandler.loadPlaylist();
						//TODO: wrong location to do this
						playlist.addTrack(selectedTrack);
						PlayerController.getInstance().setTrack(selectedTrack);
						GamePanel gameFrame = new GamePanel();
						getNavigationController().pushPanel(gameFrame);
					} else if(mode == MODE_HIGHSCORE) {
						HighscorePanel highscorePanel = new HighscorePanel(songlist.getSelectedValue());
						getNavigationController().pushPanel(highscorePanel);
					}
				}
			}
		});

		mainMenuButton = new MenuButton("Back to menu", new Color(
				KeyboardHeroConstants.FONT_COLOR_SECONDARY));
		mainMenuButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				getNavigationController().popPanel();
			}
		});

		this.add(scrollPane, "wrap, growx, pushy, growy");

		this.add(mainMenuButton, "growx, height 60!");

	}

	public void fillPlaylist() {
		playlist = PersistenceHandler.loadPlaylist();

		if(mode == MODE_RECORD){
			songlist = new MenuSongList<Track>(playlist);
			transferHandler = new PlaylistTransferHandler(playlist);
			songlist.setDropMode(DropMode.ON);
			songlist.setTransferHandler(transferHandler);
		} else {
			Playlist gamePlaylist = new Playlist("Game Playlist");
			for (Track track : playlist.getTracks()) {
				if (track.getStrokeSet() != null) {
					gamePlaylist.addTrack(track);
				}
			}
			playlist = gamePlaylist;
			songlist = new MenuSongList<Track>(playlist);
		}

		songlist.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		scrollPane = new JScrollPane(songlist);
		scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

	}
	
	@Override
	public void didPressBack(KeyEvent e) {
		getNavigationController().popToRootPanel();
	}
}
