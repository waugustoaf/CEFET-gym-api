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

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "diets")
@SQLDelete(sql = "UPDATE diets SET deleted_at = CURRENT_TIMESTAMP WHERE id=?")
@Where(clause = "deleted_at is null")
public class Diet {
    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @NotNull(message = "diets.feed_time.null")
    public Date feed_time;

    @NotBlank(message = "diets.food_type.null")
    public String food_type;

    @NotBlank(message = "diets.guidance.null")
    public String guidance;

    @CreationTimestamp()
    @Temporal(TemporalType.TIMESTAMP)
    public Date created_at;

    @UpdateTimestamp()
    @Temporal(TemporalType.TIMESTAMP)
    public Date updated_at;

    @Column(nullable = true)
    public Date deleted_at;
}
