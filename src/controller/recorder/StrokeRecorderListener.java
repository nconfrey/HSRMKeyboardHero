package controller.recorder;

import model.Stroke;

public interface StrokeRecorderListener {
	
	public void redcorderDidOpenStroke(StrokeRecorder recorder, Stroke stroke);
	
	public void redcorderDidCloseStroke(StrokeRecorder recorder, Stroke stroke);
}
