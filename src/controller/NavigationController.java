/**
 * This controller handles switching between the different views/GHPanels of the
 * game. These panels are held in a stack.
 * 
 * @author Simon Seyer
 * @author Martin Juhasz
 * @author Julia Kraft
 * @author Moritz Moeller
 * 
 **/

package controller;

import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.util.Stack;

import view.BaseFrame;
import view.GHPanel;

public class NavigationController implements KeyEventDispatcher {

	private BaseFrame baseFrame;
	private Stack<GHPanel> stack;

	/**
	 * Creates a NavigationController with the main frame and adds a
	 * KeyboardFocusManager.
	 * 
	 * @param baseFrame the main frame of keyboard hero.
	 */
	public NavigationController(BaseFrame baseFrame) {
		this.baseFrame = baseFrame;
		this.baseFrame.navigationController = this;
		this.stack = new Stack<GHPanel>();

		KeyboardFocusManager manager = KeyboardFocusManager
				.getCurrentKeyboardFocusManager();
		manager.addKeyEventDispatcher(this);
	}

	/**
	 * Takes a GHPanel hides current GHPanel via hide(), push the new GHPanel to
	 * Stack and displays it via show().
	 * 
	 * @param panel which should be pushed to the view
	 */
	public void pushPanel(GHPanel panel) {
		panel.setNavigationController(this);
		if (!stack.isEmpty()) {
			hide(stack.peek());
		}
		stack.push(panel);
		show(stack.peek());
	}

	/**
	 * Takes a GHPanel removes current GHPanel, push the new GHPanel to Stack
	 * and displays it via show().
	 * 
	 * @param panel which should replace the current panel
	 */
	public void replacePanel(GHPanel panel) {
		panel.setNavigationController(this);
		if (!stack.isEmpty()) {
			hide(stack.pop());
		}
		stack.push(panel);
		show(stack.peek());
	}

	/**
	 * Removes current GHPanel if there are more than one in stack and displays
	 * the GHPanel below the poped panel.
	 */
	public void popPanel() {
		if (stack.size() > 1) {
			hide(stack.pop());
			show(stack.peek());
		} else {
			System.out.println("Can't remove root panel");
		}
	}

	/**
	 * Removes all panels from stack except the first one (root)..
	 */
	public void popToRootPanel() {
		if (stack.size() > 1) {
			hide(stack.pop());
			while (stack.size() > 1) {
				stack.pop();
			}
			show(stack.peek());
		}
	}

	/**
	 * Adds a panel to BaseFrame.
	 * 
	 * @param panel which will be added to BaseFrame
	 */
	private void show(GHPanel panel) {
		panel.panelWillAppear();
		baseFrame.display(panel);

	}

	/**
	 * Removes a panel from BaseFrame.
	 * 
	 * @param panel which will be added to BaseFrame
	 */
	private void hide(GHPanel panel) {
		panel.panelWillDisappear();
		baseFrame.hide(panel);
	}

	/**
	 * KeyListener for the escape button. ESC button calls the didPressBack
	 * method from GHPanel
	 * 
	 * @param e the e
	 * @return true, if successful
	 */
	@Override
	public boolean dispatchKeyEvent(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE
				&& e.getID() == KeyEvent.KEY_PRESSED) {
			if (stack.size() > 0) {
				stack.peek().didPressBack(e);
				return true;
			}
		}
		return false;
	}

	/**
	 * Gets the base frame.
	 * 
	 * @return the BaseFrame
	 */
	public BaseFrame getBaseFrame() {
		return baseFrame;
	}

}