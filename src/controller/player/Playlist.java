package controller.player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.AbstractListModel;

import model.Track;

/**
 *
 * @author sseye001
 */
public class Playlist extends AbstractListModel<Track> implements Serializable{
    
    private long id;
    private String name;
    private Date creationDate;
    private List<Track> tracks;

    public Playlist(String name) {
        tracks = new ArrayList<>();

        this.name = name;
    }   
    

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getCreationDate() {
        return creationDate;
    }
    
    public int getTrackCount(){
        return tracks.size();
    }
    
    public Track getTrack(int number){
        return tracks.get(number);
    }
    
    public void addTrack(Track track){
        this.tracks.add(track);
        fireContentsChanged(track, this.tracks.size()-1,  this.tracks.size()-1);
    }

    @Override
    public int getSize() {
        return tracks.size();
    }

    @Override
    public Track getElementAt(int index) {
        return tracks.get(index);
    }
}
