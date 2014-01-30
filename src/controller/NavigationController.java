package controller;

import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.util.Stack;

import view.BaseFrame;
import view.GHPanel;

public class NavigationController implements KeyEventDispatcher{

	private BaseFrame baseFrame;
	private Stack<GHPanel> stack;

	public NavigationController(BaseFrame baseFrame) {
		this.baseFrame = baseFrame;
		this.baseFrame.navigationController = this;
		this.stack = new Stack<GHPanel>();
		
		KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
		manager.addKeyEventDispatcher(this);
	}

	public void pushPanel(GHPanel panel) {
		panel.setNavigationController(this);
		if (!stack.isEmpty()) {
			hide(stack.peek());
		}
		stack.push(panel);
		show(stack.peek());
	}
	
	public void replacePanel(GHPanel panel) {
		panel.setNavigationController(this);
		if (!stack.isEmpty()) {
			hide(stack.pop());
		}
		stack.push(panel);
		show(stack.peek());
	}

	public void popPanel() {
		if (stack.size() > 1) {
			hide(stack.pop());
			show(stack.peek());
		} else {
			System.out.println("Can't remove root panel");
		}
	}

	public void popToRootPanel() {
		if (stack.size() > 1) {
			hide(stack.pop());
			while (stack.size() > 1) {
				stack.pop();
			}
			show(stack.peek());
		}
	}

	private void show(GHPanel panel) {
		panel.panelWillAppear();
		baseFrame.display(panel);

	}

	private void hide(GHPanel panel) {
		panel.panelWillDisappear();
		baseFrame.getContentPane().remove(panel);
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE && e.getID() == KeyEvent.KEY_PRESSED) {
			if (stack.size() > 0) {
				stack.peek().didPressBack(e);
				return true;
			}
		}
		return false;
	}
	
	public BaseFrame getBaseFrame() {
		return baseFrame;
	}

}