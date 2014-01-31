/**
 * a MouseHandler for a JList that handles multi-clicks
 * 
 * @author Simon Seyer
 * @author Martin Juhasz
 * @author Julia Kraft
 * @author Moritz Moeller
 * 
 */
package helper;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Action;
import javax.swing.InputMap;
import javax.swing.JList;
import javax.swing.KeyStroke;

/**
 * A handler for keyboard and mouse actions on a list.
 * 
 * @author Simon Seyer
 * @author Geronimoo https://github.com/Geronimoo/Geronimoo-s-Dominion-Simulator
 */
public class ListAction extends MouseAdapter {

	private static final KeyStroke ENTER = KeyStroke.getKeyStroke(
			KeyEvent.VK_ENTER, 0);

	private JList list;
	private KeyStroke keyStroke;

	/**
	 * Add an Action to the JList bound by the default KeyStroke.
	 * 
	 * @param list the target list
	 * @param action the action to apply
	 */
	public ListAction(JList list, Action action) {
		this(list, action, ENTER, true);
	}

	/**
	 * Add an Action to the JList bound by the specified KeyStroke.
	 * 
	 * @param list the target list
	 * @param action the action to apply
	 * @param keyStroke the KeyStroke that should by bound
	 * @param handleMouseEvent if a double click should also be recognized for
	 *            this action
	 */
	public ListAction(JList list, Action action, KeyStroke keyStroke,
			boolean handleMouseEvent) {
		this.list = list;
		this.keyStroke = keyStroke;

		setAction(action);

		if (handleMouseEvent) {
			// Handle mouse double click
			list.addMouseListener(this);
		}
	}

	/**
	 * Bind the action to the keystroke.
	 * 
	 * @param action the action
	 */
	public void setAction(Action action) {
		// Add the KeyStroke to the InputMap
		InputMap im = list.getInputMap();
		im.put(keyStroke, keyStroke);

		// Add the Action to the ActionMap
		list.getActionMap().put(keyStroke, action);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		// Only recognize double clicks, single clicks are normal selection
		if (e.getClickCount() >= 2) {
			Action action = list.getActionMap().get(keyStroke);

			if (action != null) {
				ActionEvent event = new ActionEvent(list,
						ActionEvent.ACTION_PERFORMED, "");
				action.actionPerformed(event);
			}
		}
	}
}
