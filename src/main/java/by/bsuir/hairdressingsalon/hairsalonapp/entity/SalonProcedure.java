package by.bsuir.hairdressingsalon.hairsalonapp.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
@Entity
@Table(name = "salon_procedure")
public class SalonProcedure {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String name;

    @Column(name = "cost")
    private Double cost;

    @Column(name = "duration")
    private Integer duration;

    @JsonBackReference
    @OneToMany(
            mappedBy = "salonProcedure",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<ProcedureAppointment> procedureAppointments = new HashSet<>();

    // @JsonBackReference
    @ManyToMany(mappedBy = "canPerformProcedures")
    private Set<Employee> availableEmployees = new HashSet<>();
}
