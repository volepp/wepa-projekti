package projekti.service;

import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import projekti.model.Account;
import projekti.repository.AccountRepository;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    
    @Autowired
    private AccountRepository accountRepository;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByHandle(username);
        if(account == null) throw new UsernameNotFoundException("User not found: " + username);
        
        return new org.springframework.security.core.userdetails.User(
                account.getUsername(),
                account.getPassword(),
                true, true, true, true,
                Arrays.asList(new SimpleGrantedAuthority("USER")));
    }
}
