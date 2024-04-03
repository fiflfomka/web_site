package ru.th.models;

import lombok.*;
import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "Actor")
@NoArgsConstructor
@AllArgsConstructor
@IdClass(ActorPK.class)
public class Actor {
    @Id
    @Column(name="play_id")
    private Integer play_id;

    @Id
    @Column(name="man_id")
    private Integer man_id;

    @Column(name="actor_role")
    private String actor_role;
}
