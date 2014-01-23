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
			baseFrame.remove(stack.peek());
		}
		stack.push(panel);
		baseFrame.display(stack.peek());
	}
	
	public void popPanel(){
		if(stack.size() > 1){
			baseFrame.getContentPane().remove(stack.pop());
			baseFrame.display(stack.peek());
		}
		else{
			System.out.println("Can't remove root panel");
		}
	}
	
	public void popToRootPanel(){
		if(stack.size() > 1){
			baseFrame.getContentPane().remove(stack.pop());
			while(stack.size() > 1) {
				stack.pop();
			}
			baseFrame.display(stack.peek());
		}
	}
	
}