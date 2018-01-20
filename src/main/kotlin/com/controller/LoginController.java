package com.controller;

import com.config.WebSecurityConfig;
import com.database.UserService;
import com.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Objects;

@RestController
public class LoginController {

    @Autowired
    UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtils;

    @RequestMapping(value = "/auth/login",method = RequestMethod.POST)
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password, HttpServletResponse httpResponse) throws Exception {
        //通过用户名和密码创建一个 Authentication 认证对象，实现类为 UsernamePasswordAuthenticationToken


        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,password);
        //如果认证对象不为空
        if (Objects.nonNull(authenticationToken)){
           if (userService.getUser(authenticationToken.getPrincipal().toString())==null){
               throw  new Exception("用户不存在");
           };
        }
        try {
            //通过 AuthenticationManager（默认实现为ProviderManager）的authenticate方法验证 Authentication 对象
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            //将 Authentication 绑定到 SecurityContext
            SecurityContextHolder.getContext().setAuthentication(authentication);
            //生成Token
            String token = jwtTokenUtils.createToken(authentication,false);
            //将Token写入到Http头部
            httpResponse.addHeader(WebSecurityConfig.AUTHORIZATION_HEADER,"Bearer "+token);
            return "Bearer "+token;
        }catch (BadCredentialsException authentication){
            throw new Exception("密码错误");
        }
    }

    @RequestMapping(value = "/auth/n",method = RequestMethod.POST)
    public String signs(){

       return  "succress";
    }
}