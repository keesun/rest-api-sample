package me.whiteship.repository;

import me.whiteship.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author Baik KeeSun
 */
public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findByUsername(String username);
}
