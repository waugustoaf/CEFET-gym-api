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
@Entity(name = "user_exercises")
public class UserExercise {
    public enum Focus{
        gain,
        loss
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, unique = true, nullable = false)
    @org.hibernate.annotations.Type(type = "uuid-char")
    public UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    public User user;

    @ManyToOne
    @JoinColumn(name = "exercise_id")
    public Exercise exercise;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    public Employee employee;

    @NotNull(message = "userExercise.active.null")
    public boolean active;

    @Column(columnDefinition = "enum('gain', 'loss')")
    @NotNull(message = "userExercise.focus.null")
    public Focus focus;

    @CreationTimestamp()
    @Temporal(TemporalType.TIMESTAMP)
    public Date start_date;

    @CreationTimestamp()
    @Temporal(TemporalType.TIMESTAMP)
    public Date end_date;

    public String schedule;

    @CreationTimestamp()
    @Temporal(TemporalType.TIMESTAMP)
    public Date created_at;

    @CreationTimestamp()
    @Temporal(TemporalType.TIMESTAMP)
    public Date updated_at;
}
