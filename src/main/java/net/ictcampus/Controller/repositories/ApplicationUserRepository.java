package net.ictcampus.Controller.repositories;

import net.ictcampus.Model.ApplicationUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ApplicationUserRepository extends CrudRepository<ApplicationUser, Integer> {
    ApplicationUser findByUsername(String username);
}
