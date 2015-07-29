package me.whiteship.security;

import me.whiteship.domain.Account;
import me.whiteship.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author Baik KeeSun
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    AccountRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = repository.findByUsername(username);
        if (account == null) {
            throw new UsernameNotFoundException(username);
        }
        return new UserDetailsImpl(account);
    }
}
