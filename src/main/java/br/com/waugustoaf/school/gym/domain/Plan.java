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
@Entity(name = "plans")
public class Plan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, unique = true, nullable = false)
    @org.hibernate.annotations.Type(type = "uuid-char")
    public UUID id;

    @NotBlank(message = "'schedule' é obrigatório")
    @Column(nullable = false)
    public String schedule;

    @NotBlank(message = "'days' é obrigatório")
    @Column(nullable = false)
    public String days;

    @NotBlank(message = "'name' é obrigatório")
    @Column(nullable = false)
    public String name;

    @NotNull(message = "'value' é obrigatório")
    @Column(nullable = false, columnDefinition = "decimal(10, 2)")
    public double value;

    @CreationTimestamp()
    @Temporal(TemporalType.TIMESTAMP)
    public Date created_at;

    @UpdateTimestamp()
    @Temporal(TemporalType.TIMESTAMP)
    public Date updated_at;
}
