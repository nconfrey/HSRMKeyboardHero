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
		this.addPanelToBaseFrame(stack.peek());
	}
	
	public void popPanel(){
		if(stack.size() > 1){
			baseFrame.remove(stack.pop());
			this.addPanelToBaseFrame(stack.peek());
		}
		else{
			System.out.println("Can't remove root panel");
		}
	}
	
	public void popToRootPanel(){
		while(stack.size() > 1) {
			stack.pop();
		}
		addPanelToBaseFrame(stack.peek());
	}
	
	public void addPanelToBaseFrame(GHPanel panel){
		baseFrame.getContentPane().add(panel);
		baseFrame.getContentPane().revalidate();
		baseFrame.getContentPane().repaint();
	}
	
}