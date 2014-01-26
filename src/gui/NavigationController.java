package gui;

import java.util.Stack;

public class NavigationController {
	
	private BaseFrame baseFrame;
	private Stack<GHPanel> stack;
	
	public NavigationController(BaseFrame baseFrame){
		this.baseFrame = baseFrame;
		this.baseFrame.navigationController = this;
		this.stack = new Stack<GHPanel>();
	}
	
	
	public void pushPanel(GHPanel panel){
		panel.setNavigationController(this);
		if(!stack.isEmpty()){
			hide(stack.peek());
		}
		stack.push(panel);
		show(stack.peek());
	}
	
	public void popPanel(){
		if(stack.size() > 1){
			hide(stack.pop());
			show(stack.peek());
		}
		else{
			System.out.println("Can't remove root panel");
		}
	}
	
	public void popToRootPanel(){
		if(stack.size() > 1){
			hide(stack.pop());
			while(stack.size() > 1) {
				stack.pop();
			}
			show(stack.peek());
		}
	}
	
	private void show(GHPanel panel){
		panel.panelWillAppear();
		baseFrame.display(panel);

	}
	
	private void hide(GHPanel panel){
		panel.panelWillDisappear();
		baseFrame.getContentPane().remove(panel);
	}
	
}