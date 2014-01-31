/**
 * a panel that displays the songs that you can play/record
 * 
 * @author Simon Seyer
 * @author Martin Juhasz
 * @author Julia Kraft
 * @author Moritz Moeller
 * 
 */
package view;

import helper.KeyboardHeroConstants;
import helper.ListAction;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import model.Playlist;
import model.Track;
import net.miginfocom.swing.MigLayout;
import view.TextPrompt.Show;
import controller.PlayerController;
import controller.PlaylistTransferHandler;

public class SongListPanel extends GHPanel {

	public static final int MODE_PLAY = 0;
	public static final int MODE_RECORD = 1;
	public static final int MODE_HIGHSCORE = 2;

	private PlayerController playerController;
	private JList<Track> songlist;
	private JButton mainMenuButton;
	private ListAction selectAction;
	private PlaylistTransferHandler transferHandler;
	private JScrollPane scrollPane;
	private int mode;
	private JTextField searchField;
	private TextPrompt textPrompt;

	/**
	 * Instantiates a new song list panel.
	 * 
	 * @param playerController the player controller
	 */
	public SongListPanel(PlayerController playerController) {
		this(playerController, MODE_PLAY);
	}

	/**
	 * Instantiates a new song list panel.
	 * 
	 * @param playerController the player controller
	 * @param mode the mode
	 */
	public SongListPanel(PlayerController playerController, int mode) {
		this.playerController = playerController;
		this.mode = mode;
		init();
	}

	/**
	 * Inits the components.
	 */
	public void init() {
		this.setLayout(new MigLayout("insets 50 200 50 200, fill"));
		this.setBackground(Color.WHITE);

		JLabel titleLabel = new TitleLabel(
				KeyboardHeroConstants.getString("select_track"));
		this.add(titleLabel, "wrap, grow");

		if (mode == MODE_RECORD) {
			
			JLabel infoLabel = new JLabel(KeyboardHeroConstants.getString("record_notice"));
			infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
			infoLabel.setForeground(new Color(KeyboardHeroConstants.COLOR_SECONDARY));
			this.add(infoLabel, "wrap, grow");
			
			initSearchField();
		}
		initPlaylist();

		selectAction = new ListAction(songlist, new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (songlist.getSelectedValue() != null) {
					if (mode == MODE_PLAY || mode == MODE_RECORD) {
						Track selectedTrack = songlist.getSelectedValue();
						Playlist playlist = playerController
								.getPlaylistController().getPlaylist();
						// TODO: wrong location to do this
						playlist.addTrack(selectedTrack);
						playerController.setTrack(selectedTrack);
						GamePanel gameFrame = new GamePanel(playerController);
						getNavigationController().pushPanel(gameFrame);
					} else if (mode == MODE_HIGHSCORE) {
						HighscorePanel highscorePanel = new HighscorePanel(
								songlist.getSelectedValue());
						getNavigationController().pushPanel(highscorePanel);
					}
				}
			}
		});

		mainMenuButton = new MenuButton(
				KeyboardHeroConstants.getString("back_to_menu"), new Color(
						KeyboardHeroConstants.COLOR_SECONDARY));
		mainMenuButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				getNavigationController().popPanel();
			}
		});
		add(mainMenuButton, "growx, height 60!");
	}

	/**
	 * Inits the playlist.
	 */
	public void initPlaylist() {
		songlist = new JList<Track>();
		songlist.setFixedCellHeight(60);
		songlist.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

		setDefaultPlaylistModel();

		scrollPane = new JScrollPane(songlist);
		scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		add(scrollPane, "wrap, growx, pushy, growy");
	}

	/**
	 * Inits the search field.
	 */
	private void initSearchField() {
		searchField = new InputTextField();

		// Placeholder
		textPrompt = new TextPrompt(
				KeyboardHeroConstants.getString("search_sound_cloud"),
				searchField, Show.FOCUS_LOST);
		textPrompt.setBorder(null);

		searchField.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				final String search = searchField.getText();

				if (search.trim().length() <= 0) {
					setDefaultPlaylistModel();
					textPrompt.setText(KeyboardHeroConstants
							.getString("search_sound_cloud"));
					return;
				}

				searchField.setText("");
				songlist.requestFocus();
				textPrompt.setText(KeyboardHeroConstants.getString("loading")
						+ " '" + search + "'");
				textPrompt.setHorizontalAlignment(SwingConstants.CENTER);
				new Thread(new Runnable() {

					@Override
					public void run() {
						final Playlist list = playerController
								.getPlaylistController().getPlaylistForSearch(
										search);
						SwingUtilities.invokeLater(new Runnable() {

							@Override
							public void run() {
								songlist.setModel(list);
								textPrompt.setText(KeyboardHeroConstants
										.getString("search_sound_cloud"));
								textPrompt
										.setHorizontalAlignment(SwingConstants.LEFT);
								searchField.setText(search);
								searchField.requestFocus();
							}
						});
					}
				}).start();
			}
		});

		add(searchField, "wrap, grow, gapy 10");
	}

	/**
	 * Sets the default playlist model.
	 */
	private void setDefaultPlaylistModel() {
		boolean playable = mode == MODE_PLAY || mode == MODE_HIGHSCORE;
		Playlist playlist = playerController.getPlaylistController()
				.getPlaylist(playable);
		songlist.setModel(playlist);

		if (mode == MODE_RECORD) {
			songlist.setCellRenderer(new RecordListCellRenderer());
			transferHandler = new PlaylistTransferHandler(playlist);

			// songlist.setDropMode(DropMode.ON);
			// songlist.setTransferHandler(transferHandler);

			this.setTransferHandler(transferHandler);
		} else {
			songlist.setCellRenderer(new PlayListCellRenderer());
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see view.GHPanel#didPressBack(java.awt.event.KeyEvent)
	 */
	@Override
	public void didPressBack(KeyEvent e) {
		getNavigationController().popToRootPanel();
	}
}
