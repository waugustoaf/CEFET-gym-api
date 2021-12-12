package br.com.waugustoaf.school.gym.domain;

import br.com.waugustoaf.school.gym.validation.constraints.BirthDate;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "users")
public class User {
    public enum Role {
        administrator,
        employee,
        client
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, unique = true, nullable = false)
    @org.hibernate.annotations.Type(type = "uuid-char")
    public UUID id;

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
    @Column(unique = true, nullable = false)
    public String cpf;

    @Size(min=15, message = "users.phone.small")
    @Column(nullable = false)
    public String phone;

    public String avatar;

    @BirthDate()
    @Column(nullable = false)
    public Date birth_date;

    @br.com.waugustoaf.school.gym.validation.constraints.Role
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "ENUM('administrator', 'employee', 'client')")
    public Role role;

    @Column(nullable = false)
    @NotNull(message = "users.active.null")
    public boolean active = true;

    @CreationTimestamp()
    @Temporal(TemporalType.TIMESTAMP)
    public Date created_at;

    @UpdateTimestamp()
    @Temporal(TemporalType.TIMESTAMP)
    public Date updated_at;
}
