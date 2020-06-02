package projekti.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import projekti.model.Account;
import projekti.service.AccountService;
import projekti.service.ConnectionService;

@Controller
public class AccountController {
    
    @Autowired
    private AccountService accountService;
    
    @Autowired
    private ConnectionService connectionService;
            
            
    @GetMapping("/account/{handle}")
    public String showAccount(@PathVariable String handle, Model model) {
        Account account = accountService.getAccountByHandle(handle);
        
        if(account == null) {
            return "error";
        }
        
        String loggedUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        if(account.getUsername().equals(loggedUsername)) {
            model.addAttribute("own_profile", true);
        }
        else {
            Account sender = accountService.getAccountByUsername(loggedUsername);
            model.addAttribute("own_profile", false);
            model.addAttribute("connection_request_sent", 
                    connectionService.getConnectionRequestBySenderAndReceiver(sender, account) != null);
        }
        
        model.addAttribute("account", account);
        
        return "account";
    }
    
}
