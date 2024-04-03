package ru.th.models;

import lombok.*;
import java.util.List;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import javax.persistence.*;
import com.vladmihalcea.hibernate.type.array.ListArrayType;

@Getter
@Setter
@Entity
@Table(name = "Performance")
@NoArgsConstructor
@AllArgsConstructor
@TypeDef(name = "list-array", typeClass = ListArrayType.class)
public class Performance {
    
    @Id
    @Column(nullable = false, name = "performance_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer performance_id;
    
    @Column(nullable = false, name = "play_id")
    private Integer play_id;
    
    @Column(nullable = false, name = "hall_id")
    private Integer hall_id;
    
    @Column(name = "theater_id")
    private Integer theater_id;
    
    @Column(name="start_time")
    private java.sql.Timestamp start_time;
    
    @Column(name="end_time")
    private java.sql.Timestamp end_time;

    @Column(name="places_price_array", columnDefinition = "integer[]")
    @Type(type = "list-array")
    private List<Integer> places_price_array;
}
