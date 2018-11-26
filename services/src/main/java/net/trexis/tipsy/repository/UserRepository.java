package net.trexis.tipsy.repository;

import net.trexis.tipsy.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    User findByEmailAddress(String emailAddress);
}