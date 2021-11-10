package br.com.waugustoaf.school.gym.domain;

import br.com.waugustoaf.school.gym.validation.constraints.BirthDate;
import br.com.waugustoaf.school.gym.validation.constraints.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "users")
@SQLDelete(sql = "UPDATE users SET deleted_at = CURRENT_TIMESTAMP WHERE id=?")
@Where(clause = "deleted_at is null")
public class User {
    public enum UserType {
        administrator,
        employee,
        client
    }

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(nullable = false)
    @NotBlank(message = "Name cannot be null")
    public String name;

    @Column(nullable = false)
    @Email(message = "Email is invalid")
    public String email;

    @Size(min=11, message = "Invalid CPF")
    @Column(unique = true)
    public String cpf;

    @Size(min=15, message = "Invalid phone")
    public String phone;

    public String avatar;

    @BirthDate()
    public Date birth_date;

    @br.com.waugustoaf.school.gym.validation.constraints.UserType
    @Enumerated(EnumType.STRING)
    public UserType user_type;

    @CreationTimestamp()
    @Temporal(TemporalType.TIMESTAMP)
    public Date created_at;

    @UpdateTimestamp()
    @Temporal(TemporalType.TIMESTAMP)
    public Date updated_at;

    @Column(nullable = true)
    public Date deleted_at;
}
