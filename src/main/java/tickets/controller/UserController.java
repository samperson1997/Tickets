package tickets.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import tickets.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> login(HttpServletRequest request, String email, String password) {
        Map<String, Object> map = userService.login(email, password);
        if (map.get("msg").equals("success")) {
            HttpSession session = request.getSession();
            session.setAttribute("email", email);
        }
        return map;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public String register(String email, String password) {
        return userService.register(email, password) ? "success" : "fail";
    }

    @RequestMapping(value = "/verify", method = RequestMethod.GET)
    public void verify(HttpServletResponse response, @RequestParam("code") String code) throws Exception {
        boolean ifSuccess = userService.verify(code);
        if (ifSuccess) {
            response.sendRedirect("/login.html");
        } else {
            response.sendRedirect("/register.html");
        }
    }

}
