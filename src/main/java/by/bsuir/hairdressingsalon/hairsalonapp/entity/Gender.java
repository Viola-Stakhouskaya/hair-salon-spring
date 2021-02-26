package by.bsuir.hairdressingsalon.hairsalonapp.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
@Entity
@Table(name = "gender")
public class Gender {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "value")
    private String name;

}
