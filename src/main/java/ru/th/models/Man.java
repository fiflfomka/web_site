package ru.th.models;

import lombok.*;
import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "Man")
@NoArgsConstructor
@AllArgsConstructor
public class Man {

    @Id
    @Column(name="man_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer man_id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;
    
}
