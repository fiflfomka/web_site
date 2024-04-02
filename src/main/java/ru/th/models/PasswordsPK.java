package ru.th.models;

import java.io.Serializable;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.IdClass;

@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class PasswordsPK implements Serializable {
    public Integer theater_id;
    public EnumProfession user_role;

    public String user_to_str(EnumProfession e) {
        if (e == EnumProfession.cashier) {
            return "cashier";
        }
        if (e == EnumProfession.content_manager) {
            return "cashier";
        }
        return "expert";
    }
}
