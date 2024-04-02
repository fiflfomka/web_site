package ru.th.models;

import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import javax.persistence.*;

@Entity
@Table(name = "Passwords")
@NoArgsConstructor
@AllArgsConstructor
@IdClass(PasswordsPK.class)
public class Passwords {
    
    @Id
    @Column(nullable = false, name="theater_id")
    public Integer theater_id;

    @Id
    @Column(nullable = false, name="user_role")
    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    public EnumProfession user_role;

    @Column(nullable = false, name="user_login")
    public String user_login;

    @Column(nullable = false, name="user_password")
    public String user_password;
}
