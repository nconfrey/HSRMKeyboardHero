package controller;

import model.StrokeKey;

public interface GuitarStringListener {
    
    public void guitarStringPressed(StrokeKey strokeKey);
    public void guitarStringReleased(StrokeKey strokeKey);
    
    public void guitarStrokePressed(StrokeKey strokeKey);
    public void guitarStrokeReleased(StrokeKey strokeKey);
    
}