package ru.th.models;

import lombok.*;
import javax.persistence.*;

@Entity
@Table(name = "Man")
@NoArgsConstructor
@AllArgsConstructor
public class Man {

    @Id
    @Column(name="man_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer man_id;

    @Column(name = "name")
    public String name;

    @Column(name = "description")
    public String description;
    
}
