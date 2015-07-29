package me.whiteship.security;

import me.whiteship.domain.Account;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Baik KeeSun
 */
public class UserDetailsImpl extends User {

    private Account account;

    public UserDetailsImpl(Account account) {
        super(account.getUsername(), account.getPassword(), authorities(account));
        this.account = account;
    }

    private static Collection<? extends GrantedAuthority> authorities(Account account) {
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        return authorities;
    }

    public Account getAccount() {
        return account;
    }
}
