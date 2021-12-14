package br.com.waugustoaf.school.gym.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "user_diets")
public class UserDiet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, unique = true, nullable = false)
    @org.hibernate.annotations.Type(type = "uuid-char")
    public UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotNull(message = "userDiets.user.null")
    public User user;

    @ManyToOne
    @JoinColumn(name = "diet_id")
    @NotNull(message = "userDiets.diet.null")
    public Diet diet;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    @NotNull(message = "userDiets.employee.null")
    public Employee employee;

    @CreationTimestamp()
    @Temporal(TemporalType.TIMESTAMP)
    public Date start_date;

    @CreationTimestamp()
    @Temporal(TemporalType.TIMESTAMP)
    public Date end_date;

    @NotNull(message = "userDiets.active.null")
    public boolean active;

    @CreationTimestamp()
    @Temporal(TemporalType.TIMESTAMP)
    public Date created_at;

    @CreationTimestamp()
    @Temporal(TemporalType.TIMESTAMP)
    public Date updated_at;
}
