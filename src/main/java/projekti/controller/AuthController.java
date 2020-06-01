package projekti.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import projekti.model.Account;
import projekti.service.AccountService;

@Controller
public class AuthController {

    @Autowired
    AccountService accountService;
    
    
    @GetMapping("/login")
    public String login(Model model) {
        if(!model.containsAttribute("account")) {
            model.addAttribute("account", new Account());
        }
        
        return "login";
    }
    
    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("account") Account account, BindingResult bindingResult, @RequestParam String passwordAgain, RedirectAttributes attr, HttpSession session) {
        if(bindingResult.hasErrors()) {
            attr.addFlashAttribute("org.springframework.validation.BindingResult.account", bindingResult);
            attr.addFlashAttribute("account", account);
            return "redirect:/login?error=true#register";
        } else {
            int ret = accountService.createAccount(account, passwordAgain);
            if(ret == 1) {
                // Success!
                return "redirect:/";
            } else {
                attr.addFlashAttribute("account", account);
                switch(ret) {
                    case -1:
                        // Passwords don't match
                        System.out.println("password");
                        attr.addFlashAttribute("passwordsDontMatch", true);
                        break;
                    case -2:
                        // Username already in use
                        System.out.println("username");
                        attr.addFlashAttribute("usernameInUse", true);
                        break;
                    case -3:
                        // Handle already in use
                        System.out.println("handle");
                        attr.addFlashAttribute("handleInUse", true);
                        break;
                }
                return "redirect:/login?error=true#register";
            }
        }
    }
}
