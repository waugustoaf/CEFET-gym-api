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
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "notifications")
public class Notification {
    public enum Type {
        urgent,
        normal
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, unique = true, nullable = false)
    @org.hibernate.annotations.Type(type = "uuid-char")
    public UUID id;

    @NotBlank(message = "diets.title.null")
    @Column(nullable = false)
    public String title;

    @NotBlank(message = "diets.message.null")
    @Column(nullable = false)
    public String message;

    @NotBlank(message = "diets.type.null")
    @Column(nullable = false, columnDefinition = "ENUM('urgent', 'normal')")
    @Enumerated(EnumType.STRING)
    public Type type;

    @NotBlank(message = "diets.repetitions.null")
    @Column(nullable = false, columnDefinition = "decimal(10, 2)")
    public double value;

    @CreationTimestamp()
    @Temporal(TemporalType.TIMESTAMP)
    public Date created_at;

    @UpdateTimestamp()
    @Temporal(TemporalType.TIMESTAMP)
    public Date updated_at;
}
