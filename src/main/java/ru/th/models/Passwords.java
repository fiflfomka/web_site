package ru.th.models;

import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "Passwords")
@NoArgsConstructor
@AllArgsConstructor
@IdClass(PasswordsPK.class)
public class Passwords {

    @Column(nullable = false, name="theater_id")
    private Integer theater_id;

    @Column(nullable = false, name="user_role")
    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    private EnumProfession user_role;

    @Id
    @Column(nullable = false, name="user_login")
    private String user_login;

    @Id
    @Column(nullable = false, name="user_password")
    private String user_password;
}
