package br.com.waugustoaf.school.gym.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, unique = true, nullable = false)
    @org.hibernate.annotations.Type(type = "uuid-char")
    public UUID id;

    @Column(name = "hours_per_day")
    @NotNull(message = "employees.hoursPerDay.null")
    @Min(value = 0, message = "employees.payment_day.except_min")
    @Max(value = 24, message = "employees.payment_day.except_max")
    public int hoursPerDay;

    @Column(columnDefinition = "decimal(10, 2)")
    @NotNull(message = "employees.wage.null")
    public double wage;

    @NotNull(message = "employees.education.null")
    public String education;

    @NotNull(message = "employees.payment_day.null")
    @Min(value = 31, message = "employees.payment_day.except_min")
    @Max(value = 31, message = "employees.payment_day.except_max")
    public int payment_day;

    @CreationTimestamp()
    @Temporal(TemporalType.TIMESTAMP)
    public Date created_at;

    @CreationTimestamp()
    @Temporal(TemporalType.TIMESTAMP)
    public Date updated_at;
}
