package ru.th.models;

import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import javax.persistence.*;

@Entity
@Table(name = "FreeSeats")
@NoArgsConstructor
@AllArgsConstructor
@IdClass(FreeSeatsPK.class)
public class FreeSeats {
    
    @Id
    @Column(nullable = false, name = "performance_id")
    public Integer performance_id;

    @Id
    @Column(nullable = false, name = "seat_id")
    public Integer seat_id;

    @Column(nullable = false, name = "price")
    public int price;
    
    @Column(nullable = false, name = "seat_raw")
    public int seat_raw;
    
    @Column(nullable = false, name = "seat_place")
    public int seat_place;
    
    @Column(nullable = false, name = "seat_type")
    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    public EnumHallSeatType seat_type;
    
    @Column(nullable = false, name = "plane_x")
    public int plane_x;
    
    @Column(nullable = false, name = "plane_y")
    public int plane_y;
    
}
