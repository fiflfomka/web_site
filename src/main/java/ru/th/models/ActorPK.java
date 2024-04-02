package ru.th.models;

import java.io.Serializable;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class ActorPK implements Serializable{
    private int play_id;
    private int man_id;
    
    public int get_play_id() {
        return play_id;
    }
    
    public void set_play_id(int play_id) {
        this.play_id = play_id;
    }
    
    public int get_man_id() {
        return man_id;
    }
    
    public void set_man_id(int man_id) {
        this.man_id = man_id;
    }
}
