package br.com.waugustoaf.school.gym.domain;

import br.com.waugustoaf.school.gym.validation.constraints.BirthDate;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.Email;
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
    @NotBlank(message = "users.name.null")
    public String name;

    @Column(nullable = false)
    @Email(message = "users.email.null")
    public String email;

    @Column(nullable = false)
    @NotBlank(message = "users.password.null")
    @Size(min=6, message = "users.password.small")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public String password;

    @Size(min=11, message = "users.cpf.small")
    @Column(unique = true)
    public String cpf;

    @Size(min=15, message = "users.phone.small")
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
