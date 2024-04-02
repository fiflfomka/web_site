package ru.th.models;

import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import javax.persistence.*;

@Entity
@Table(name = "HallSeats")
@NoArgsConstructor
@AllArgsConstructor
public class HallSeats {
    
    @Id
    @Column(nullable = false, name = "seat_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer seat_id;
    
    @Column(nullable = false, name = "hall_id")
    public Integer hall_id;
    
    @Column(nullable = false, name = "seat_group")
    public Integer seat_group;
    
    @Column(nullable = false, name = "seat_raw")
    public Integer seat_raw;
    
    @Column(nullable = false, name = "seat_place")
    public Integer seat_place;
    
    @Column(nullable = false, name = "seat_type")
    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    public EnumHallSeatType seat_type;
    
    @Column(nullable = false, name = "plane_x")
    public Integer plane_x;
    
    @Column(nullable = false, name = "plane_y")
    public Integer plane_y;
    
}
