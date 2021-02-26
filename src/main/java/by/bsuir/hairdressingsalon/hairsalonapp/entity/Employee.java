package by.bsuir.hairdressingsalon.hairsalonapp.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
@ToString(of = {"id", "name", "surname", "payRate", "gender"})
@Entity
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotEmpty
    @Size(min = 2, message = "Минимум 2 символа")
    @Column(name = "name")
    private String name;

    @NotEmpty
    @Size(min = 2, message = "Минимум 2 символа")
    @Column(name = "surname")
    private String surname;

    @NotNull
    @Positive
    @Digits(integer = 100000, fraction = 2)
    @Column(name = "payrate")
    private Double payRate;

    @OneToOne
    @JoinColumn(name = "gender_id", referencedColumnName = "id")
    private Gender gender;

    @ManyToMany(cascade = CascadeType.DETACH, fetch = FetchType.LAZY)
    @JoinTable(name = "employee_procedure",
               joinColumns = @JoinColumn(name = "employee_id"),
               inverseJoinColumns = @JoinColumn(name = "procedure_id"))
    private Set<SalonProcedure> canPerformProcedures = new HashSet<>();

    @OneToMany(
            mappedBy = "performingEmployee",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<ProcedureAppointment> employeeAppointments = new HashSet<>();
}
