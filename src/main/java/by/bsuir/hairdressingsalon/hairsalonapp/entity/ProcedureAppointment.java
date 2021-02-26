package by.bsuir.hairdressingsalon.hairsalonapp.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.FutureOrPresent;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
@Entity
@Table(name = "procedure_appointment")
public class ProcedureAppointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @FutureOrPresent(message = "Вы не можете записаться на прошедшее число.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "appointment_date")
    private LocalDate date;

    @DateTimeFormat(pattern = "HH:mm")
    @Column(name = "appointment_time")
    private LocalTime startTime;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "procedure_id")
    private SalonProcedure salonProcedure;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    private Customer signedUpCustomer;

    // @OneToOne(cascade = {CascadeType.DETACH, CascadeType.REMOVE}, orphanRemoval = true)
    // @LazyToOne(LazyToOneOption.PROXY)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "performing_employee_id", referencedColumnName = "id", nullable = false)
    private Employee performingEmployee;

    @Override
    public String toString() {
        return "ProcedureAppointment{" +
                "id=" + id +
                ", date=" + date +
                ", startTime=" + startTime +
                ", signedUpCustomer=" + signedUpCustomer +
                '}';
    }
}
