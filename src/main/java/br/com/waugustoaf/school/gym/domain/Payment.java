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
@Entity(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, unique = true, nullable = false)
    @org.hibernate.annotations.Type(type = "uuid-char")
    public UUID id;

    @Column(nullable = true)
    public String receipt_image;

    @ManyToOne
    @JoinColumn(name = "user_id")
    public User user;

    @Column(columnDefinition = "decimal(14, 2)")
    public float value;

    @CreationTimestamp()
    @Temporal(TemporalType.TIMESTAMP)
    public Date created_at;

    @CreationTimestamp()
    @Temporal(TemporalType.TIMESTAMP)
    public Date updated_at;
}
