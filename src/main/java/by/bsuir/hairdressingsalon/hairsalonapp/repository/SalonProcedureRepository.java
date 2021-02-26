package by.bsuir.hairdressingsalon.hairsalonapp.repository;

import by.bsuir.hairdressingsalon.hairsalonapp.entity.SalonProcedure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalonProcedureRepository extends JpaRepository<SalonProcedure, Long> {

    List<SalonProcedure> findAll();
}
