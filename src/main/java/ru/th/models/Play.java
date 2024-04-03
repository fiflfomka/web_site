package ru.th.models;

import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "Play")
@NoArgsConstructor
@AllArgsConstructor
public class Play {
    
    @Id
    @Column(nullable = false, name = "play_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer play_id;
    
    @Column(nullable = false, name = "name")
    private String name;
    
    @Column(name = "regisseur")
    private Integer regisseur;
    
    @Column(name = "release_year")
    private Integer release_year;

    @Column(nullable = false, name = "genre")
    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    private EnumPlayGenre genre;

    @Column(name = "annotation")
    private String annotation;

}
