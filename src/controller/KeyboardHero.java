package controller;


import gui.BaseFrame;
import gui.MenuPanel;
import gui.NavigationController;

import java.lang.instrument.Instrumentation;

import model.PersistenceHandler;



public class KeyboardHero {
		
	public static void main(String[] args) {
		BaseFrame baseFrame = new BaseFrame();
		NavigationController navCon = new NavigationController(baseFrame);
		MenuPanel menu = new MenuPanel();
		navCon.pushPanel(menu);
		
		Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                PersistenceHandler.savePlaylist();
            }
        });
	}
}
