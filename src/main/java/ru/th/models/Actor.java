package ru.th.models;

import lombok.*;
import javax.persistence.*;

@Entity
@Table(name = "Actor")
@NoArgsConstructor
@AllArgsConstructor
@IdClass(ActorPK.class)
public class Actor {
    @Id
    @Column(name="play_id")
    public Integer play_id;

    @Id
    @Column(name="man_id")
    public Integer man_id;

    @Column(name="actor_role")
    public String actor_role;
}
