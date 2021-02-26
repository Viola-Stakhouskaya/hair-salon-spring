package by.bsuir.hairdressingsalon.hairsalonapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
@ToString(of = {"id", "name", "surname", "login", "roles"})
@Entity
@Table(name = "customer")
public class Customer implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Size(min = 3, message = "Длина должна быть минимум 3 символа")
    @Column(name = "login", nullable = false, unique = true)
    private String login;

    @Email
    @Size(min = 5, message = "Введите корректный имейл")
    @Column(name = "customer_email", nullable = false, unique = true)
    private String email;

    @JsonIgnore
    @NotEmpty(message = "Пароль не может быть пустым")
    @Size(min = 5, message = "Минимум 5 символов")
    @Column(name = "customer_password", nullable = false)
    private String password;

    @Size(min = 2, message = "Длина должна быть минимум 3 символа")
    @Pattern(regexp = "^[А-Яа-я]*$", message = "Имя не может содержать числа и символы")
    @Column(name = "customer_name")
    private String name;

    @Size(min = 3, message = "Длина должна быть минимум 3 символа")
    @Pattern(regexp = "^[А-Яа-я]*$", message = "Фамилия не может содержать числа и символы")
    @Column(name = "customer_surname")
    private String surname;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "active", nullable = false)
    private boolean active;

    // @OneToOne
    // @JoinColumn(name = "gender_id", referencedColumnName = "id")
    // private Gender gender;

    @JsonIgnore
    @OneToMany(
            mappedBy = "signedUpCustomer",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<ProcedureAppointment> scheduledAppointments = new HashSet<>();

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.getRoles();
    }

    @Override
    public String getUsername() {
        return this.login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return active;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.isActive();
    }
}
