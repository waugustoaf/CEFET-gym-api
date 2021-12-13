package br.com.waugustoaf.school.gym.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "user_exercise")
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

    public boolean active;

    @Column(columnDefinition = "enum('gain', 'loss')")
    public Focus focus;

    public Date start_date;

    public Date end_date;

    public String schedule;

    public Date created_at;

    public Date updated_at;
}
