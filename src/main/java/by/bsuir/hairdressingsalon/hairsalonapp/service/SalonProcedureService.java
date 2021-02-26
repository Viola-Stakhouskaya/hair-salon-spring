package by.bsuir.hairdressingsalon.hairsalonapp.service;

import by.bsuir.hairdressingsalon.hairsalonapp.entity.SalonProcedure;
import by.bsuir.hairdressingsalon.hairsalonapp.repository.SalonProcedureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalonProcedureService {

    private final SalonProcedureRepository procedureRepository;

    @Autowired
    public SalonProcedureService(SalonProcedureRepository procedureRepository) {
        this.procedureRepository = procedureRepository;
    }

    public List<SalonProcedure> getAllProcedures() {
        return procedureRepository.findAll();
    }
}
