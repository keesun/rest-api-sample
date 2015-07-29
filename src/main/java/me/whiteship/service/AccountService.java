package me.whiteship.service;

import me.whiteship.domain.Account;
import me.whiteship.dto.AccountDto;
import me.whiteship.exception.UsernameDuplicatedException;
import me.whiteship.repository.AccountRepository;
import me.whiteship.security.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Date;

/**
 * @author Baik KeeSun
 */
@Service
public class AccountService {

    @Autowired
    private AccountRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public Account createAccount(AccountDto.CreateRequest createRequest) {
        if (repository.findByUsername(createRequest.getUsername()) != null) {
            throw new UsernameDuplicatedException();
        }

        Account account = new Account();
        account.setJoined(new Date());
        account.setUsername(createRequest.getUsername());
        account.setPassword(passwordEncoder.encode(createRequest.getPassword()));

        return repository.save(account);
    }

    public Account currentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetailsImpl) {
            Account account = ((UserDetailsImpl) principal).getAccount();
            return account;
        }
        throw new UsernameNotFoundException("no current user");
        // return NullUser();
    }

    @PostConstruct
    public void setup() {

    }

    @PreDestroy
    public void clear() {
        //
    }
}
