package projekti.controller;

import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import projekti.model.Account;
import projekti.model.ConnectionRequest;
import projekti.service.AccountService;
import projekti.service.ConnectionService;

@Controller
public class ConnectionController {
    
    @Autowired
    private ConnectionService connectionService;
    
    @Autowired
    private AccountService accountService;
    
    @GetMapping("/connections/{account_handle}")
    public String showConnections(@PathVariable String account_handle, Model model) {
        Account connection_account = accountService.getAccountByHandle(account_handle);
        if(connection_account == null) return "error";
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        
        List<Account> connections = connection_account.getConnections();
        System.out.println(connections.size());
        model.addAttribute("connections", connections);
        if(connection_account.getUsername().equals(username)) {
            List<Account> connectionRequests = 
                    connectionService.getConnectionRequestsByReceiver(connection_account)
                    .stream().map(cr -> cr.getSender()).collect(Collectors.toList());
            model.addAttribute("connectionRequests", connectionRequests);
            
            model.addAttribute("own_profile", true);
        } else {
            model.addAttribute("own_profile", false);
        }
        
        return "connections";
    }
    
    @PostMapping("/connections/add/{handle}")
    public String addConnection(@PathVariable String handle) {
        Account receiver = accountService.getAccountByHandle(handle);
        
        if(receiver == null) return "redirect:/account/" + handle;
        
        String senderUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        Account sender =  accountService.getAccountByUsername(senderUsername);
        if(sender == null) return "redirect:/login";
        else if(sender.equals(receiver)) return "redirect:/account/" + handle;

        connectionService.sendConnectionRequest(sender, receiver);
        
        return "redirect:/account/" + handle;
    }
    
    @PostMapping("/connections/accept/{handle}")
    public String acceptConnection(@PathVariable String handle) {
        Account sender = accountService.getAccountByHandle(handle);
        if(sender == null) return "redirect:/account/"+handle;
        
        String receiverUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        Account receiver = accountService.getAccountByUsername(receiverUsername);
        if(receiver == null) return "redirect:/login";
        else if(sender.getUsername().equals(receiverUsername)) return "redirect:/connections/" + receiver.getHandle();
        
        connectionService.acceptConnectionRequest(sender, receiver);
        return "redirect:/connections/"+receiver.getHandle();
    }
    
    @PostMapping("/connections/reject/{handle}")
    public String rejectConnection(@PathVariable String handle) {
        Account sender = accountService.getAccountByHandle(handle);
        if(sender == null) return "redirect:/account/"+handle;
        
        String receiverUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        Account receiver = accountService.getAccountByUsername(receiverUsername);
        if(receiver == null) return "redirect:/login";
        else if(sender.getUsername().equals(receiverUsername)) return "redirect:/connections/" + receiver.getHandle();
        
        connectionService.rejectConnectionRequest(sender, receiver);
        return "redirect:/connections/"+receiver.getHandle();
    }
}
