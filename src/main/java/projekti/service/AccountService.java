package projekti.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import projekti.model.Account;
import projekti.repository.AccountRepository;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public int createAccount(Account account, String passwordAgain) {
        if(!account.getPassword().equals(passwordAgain)) return -1;
        Account a = accountRepository.findByUsername(account.getUsername());
        if(a != null) return -2;
        Account b = accountRepository.findByHandle(account.getHandle());
        if(b != null) return -3;
        
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        
        accountRepository.save(account);
        
        return 1;
    }
    
}
