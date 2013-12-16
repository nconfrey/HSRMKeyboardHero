package controller;

import model.StrokeKey;

public interface GuitarStringListener {
    
    public void guitarStringPressed(StrokeKey strokeKey);
    public void GuitarStringReleased(StrokeKey strokeKey);
    
}