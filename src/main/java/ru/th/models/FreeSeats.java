package ru.th.models;

import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "FreeSeats")
@NoArgsConstructor
@AllArgsConstructor
@IdClass(FreeSeatsPK.class)
public class FreeSeats {
    
    @Id
    @Column(nullable = false, name = "performance_id")
    private Integer performance_id;

    @Id
    @Column(nullable = false, name = "seat_id")
    private Integer seat_id;

    @Column(nullable = false, name = "price")
    private Integer price;
    
    @Column(nullable = false, name = "seat_raw")
    private Integer seat_raw;
    
    @Column(nullable = false, name = "seat_place")
    private Integer seat_place;
    
    @Column(nullable = false, name = "seat_type")
    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    private EnumHallSeatType seat_type;
    
    @Column(nullable = false, name = "plane_x")
    private Integer plane_x;
    
    @Column(nullable = false, name = "plane_y")
    private Integer plane_y;
    
}
