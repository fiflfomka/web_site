package ru.th.models;

import java.io.Serializable;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class FreeSeatsPK implements Serializable{
    public int performance_id;
    public int seat_id;
}
