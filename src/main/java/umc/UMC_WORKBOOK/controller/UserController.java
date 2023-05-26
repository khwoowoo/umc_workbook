package umc.UMC_WORKBOOK.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import umc.UMC_WORKBOOK.domain.User;
import umc.UMC_WORKBOOK.repository.UserRepository;
import umc.UMC_WORKBOOK.service.UserSerivce;

import java.util.List;

@Controller
public class UserController {

    private UserSerivce userSerivce;

    @Autowired
    public UserController(UserRepository userRepository) {
        userSerivce = new UserSerivce(userRepository);
    }

    @GetMapping("/")
    public String home(){
        return "home";
    }

    @PostMapping("login")
    public String Login(LoginForm loginForm, Model model){
        if(userSerivce.findOne(loginForm.getUserId()).isEmpty())
            return "redirect:/";
        User loginUser = userSerivce.findOne(loginForm.getUserId()).get();
        LoginUser.getInstance().setInstance(loginUser);
        model.addAttribute("obj", loginUser.getName());
        return "user";
    }

    @GetMapping("/users/new")
    public String createForm(){
        return "users/CreateUserForm";
    }

    @PostMapping("/users/new")
    public String create(UserForm form){
        User user = new User();
        user.setId(form.getUserId());
        user.setPassword(form.getPassword());
        user.setName(form.getUsername());
        user.setBirthDate(form.getBirthDate());
        userSerivce.join(user);

        return "redirect:/";
    }

    @GetMapping("/users")
    public String list(Model model){
        List<User> users = userSerivce.findUsers();
        model.addAttribute("users", users);
        return "users/userList";
    }
}
