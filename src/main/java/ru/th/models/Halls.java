package ru.th.models;

import lombok.*;
import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "Halls")
@NoArgsConstructor
@AllArgsConstructor
public class Halls {
    
    @Id
    @Column(nullable = false, name = "hall_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer hall_id;

    @Column(nullable = false, name = "theater_id")
    private Integer theater_id;
    
    @Column(nullable = false, name = "plane_size_x")
    private Integer plane_size_x;
    
    @Column(nullable = false, name = "plane_size_y")
    private Integer plane_size_y;
    
}
