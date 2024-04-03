package ru.th.models;

import lombok.*;
import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "Theater")
@NoArgsConstructor
@AllArgsConstructor
public class Theater {
    
    @Id
    @Column(nullable = false, name = "theater_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer theater_id;

    @Column(nullable = false, name = "name")
    private String name;

    @Column(name = "address")
    private String address = "";

    @Column(name = "phone_number")
    private String phone_number = "";

}
