package by.bsuir.hairdressingsalon.hairsalonapp.repository;

import by.bsuir.hairdressingsalon.hairsalonapp.entity.Gender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenderRepository extends JpaRepository<Gender, Long> {
}
