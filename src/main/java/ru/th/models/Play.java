package ru.th.models;

import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import javax.persistence.*;


@Entity
@Table(name = "Play")
@NoArgsConstructor
@AllArgsConstructor
public class Play {
    
    @Id
    @Column(nullable = false, name = "play_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer play_id;
    
    @Column(nullable = false, name = "name")
    public String name;
    
    @Column(name = "regisseur")
    public Integer regisseur;
    
    @Column(name = "release_year")
    public Integer release_year;

    @Column(nullable = false, name = "genre")
    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    public EnumPlayGenre genre;

    @Column(name = "annotation")
    public String annotation;

}
