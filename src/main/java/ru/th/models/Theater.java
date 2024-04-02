package ru.th.models;

import lombok.*;
import javax.persistence.*;

@Entity
@Table(name = "Theater")
@NoArgsConstructor
@AllArgsConstructor
public class Theater {
    
    @Id
    @Column(nullable = false, name = "theater_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer theater_id;
    
    @Column(nullable = false, name = "name")
    public String name;
    
    @Column(name = "address")
    public String address = "";
    
    @Column(name = "phone_number")
    public String phone_number = "";
    
}
