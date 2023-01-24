package net.ictcampus.Controller.services;

import net.ictcampus.Model.ApplicationUser;
import net.ictcampus.Controller.repositories.ApplicationUserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ApplicationUserService {

    private ApplicationUserRepository applicationUserRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public ApplicationUserService(ApplicationUserRepository applicationUserRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.applicationUserRepository = applicationUserRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public void signUp(ApplicationUser applicationUser) {
        applicationUser.setPassword(bCryptPasswordEncoder.encode(applicationUser.getPassword()));
        applicationUserRepository.save(applicationUser);
    }

    public Iterable<ApplicationUser> findAll() {
        return applicationUserRepository.findAll();
    }

    public Optional<ApplicationUser> findById(Integer id) {
        return applicationUserRepository.findById(id);
    }

    public void update(ApplicationUser applicationUser) {
        applicationUser.setPassword(bCryptPasswordEncoder.encode(applicationUser.getPassword()));
        applicationUserRepository.save(applicationUser);
    }

    public void deleteById(Integer id) {
        applicationUserRepository.deleteById(id);
    }
}


