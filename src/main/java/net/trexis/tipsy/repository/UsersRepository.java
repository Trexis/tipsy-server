package net.trexis.tipsy.repository;

import net.trexis.tipsy.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersRepository extends CrudRepository<User, Long> {

    User findByEmailAddress(String emailAddress);

    @Query("select u from User u where id=:userId")
    User findByUserId(@Param("userId")Long userId);
}