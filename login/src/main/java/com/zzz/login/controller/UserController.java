package com.zzz.login.controller;

import com.zzz.login.entity.User;
import com.zzz.login.utils.JwtUtls;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lingqu
 * @date 2021/11/12
 * @apiNote
 */
@RestController
public class UserController {
    private final String USERNAME = "admin";
    private final String PASSWORD = "123456";

    @PostMapping("/login")
    public User login(@RequestBody User user) {
        System.out.println(user);
        if(USERNAME.equals(user.getUsername()) && PASSWORD.equals(user.getPassword())){
            //添加token
            user.setToken(JwtUtls.createToken());
            return user;
        }
        return null;
    }

    @GetMapping("/checkToken")
    public Boolean checkToken(HttpServletRequest request){
        String token = request.getHeader("token");
        return JwtUtls.checkToken(token);
    }


}
