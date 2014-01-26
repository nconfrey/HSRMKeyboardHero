package controller;

import model.StrokeKey;

/**
 *
 * @author sseye001
 */
public interface ScoreListener {
    
    public void scoringDidStart(StrokeKey key);
    public void scoringDidEnd(StrokeKey key);
    
}
