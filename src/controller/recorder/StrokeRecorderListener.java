package controller.recorder;

import model.Track;

public interface StrokeRecorderListener {
	
	public void recorderDidStartRecording(StrokeRecorder recorder, Track track);
	public void recorderDidStopRecording(StrokeRecorder recorder, Track track);
	
}
