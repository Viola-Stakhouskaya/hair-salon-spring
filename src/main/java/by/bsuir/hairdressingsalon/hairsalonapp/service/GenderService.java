package by.bsuir.hairdressingsalon.hairsalonapp.service;

import by.bsuir.hairdressingsalon.hairsalonapp.entity.Gender;
import by.bsuir.hairdressingsalon.hairsalonapp.repository.GenderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenderService {

    private final GenderRepository genderRepository;

    public GenderService(GenderRepository genderRepository) {
        this.genderRepository = genderRepository;
    }

    public List<Gender> getAllGenders() {
        return genderRepository.findAll();
    }
}
