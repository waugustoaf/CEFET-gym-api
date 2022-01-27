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
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "exercises")
public class Exercise {
    public enum Type {
        gain,
        loss
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, unique = true, nullable = false)
    @org.hibernate.annotations.Type(type = "uuid-char")
    public UUID id;

    @NotNull(message = "diets.type.null")
    @Column(nullable = false, columnDefinition = "ENUM('gain', 'loss')")
    @Enumerated(EnumType.STRING)
    public Type type;

    @Column(nullable = false)
    public String name;

    public String description;

    @NotBlank(message = "diets.duration.null")
    @Column(nullable = false)
    public String duration;

    @NotNull
    @Column(nullable = false)
    public int repetitions;

    @CreationTimestamp()
    @Temporal(TemporalType.TIMESTAMP)
    public Date created_at;

    @UpdateTimestamp()
    @Temporal(TemporalType.TIMESTAMP)
    public Date updated_at;
}
