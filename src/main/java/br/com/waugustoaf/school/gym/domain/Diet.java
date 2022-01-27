package br.com.waugustoaf.school.gym.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "diets")
public class Diet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, unique = true, nullable = false)
    @org.hibernate.annotations.Type(type = "uuid-char")
    public UUID id;

    @NotNull(message = "diets.feed_time.null")
    @Column(nullable = false)
    public String feed_time;

    @NotBlank(message = "diets.food_type.null")
    @Column(nullable = false)
    public String food_type;

    @NotBlank(message = "diets.instructions.null")
    @Column(nullable = false, columnDefinition = "text")
    public String instructions;

    @CreationTimestamp()
    @Temporal(TemporalType.TIMESTAMP)
    public Date created_at;

    @UpdateTimestamp()
    @Temporal(TemporalType.TIMESTAMP)
    public Date updated_at;
}
