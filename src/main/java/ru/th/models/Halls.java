package ru.th.models;

import lombok.*;
import javax.persistence.*;

@Entity
@Table(name = "Halls")
@NoArgsConstructor
@AllArgsConstructor
public class Halls {
    
    @Id
    @Column(nullable = false, name = "hall_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer hall_id;

    @Column(nullable = false, name = "theater_id")
    public Integer theater_id;
    
    @Column(nullable = false, name = "plane_size_x")
    public Integer plane_size_x;
    
    @Column(nullable = false, name = "plane_size_y")
    public Integer plane_size_y;
    
}
