package com.justdoit.keller.controller;

import com.justdoit.keller.common.response.Response;
import com.justdoit.keller.common.util.StringUtils;
import com.justdoit.keller.entity.UserInfo;
import com.justdoit.keller.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 不需要登录就能调用的接口
 * @author yangkaile
 * @date 2019-10-09 09:24:19
 */
@RestController
@RequestMapping("/base")
@CrossOrigin(origins = "*",allowedHeaders="*", maxAge = 3600)
public class BaseController {
    @Resource
    private UserService userService;

    /**
     * 注册功能
     */
    @PostMapping("/register")
    public ResponseEntity register(int type,String email,String password,String code){
//        String email = params.get("email");
//        String password = params.get("password");
//        String code = params.get("code");
//        int type = Integer.parseInt(params.get("type"));
        if(StringUtils.isEmpty(email,password,code)){
            return Response.badRequest();
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setEmail(email);
        userInfo.setPassword(password);
        userInfo.setType(type);

        return Response.ok(userService.register(userInfo,code));
    }

    @GetMapping("/check")
    public ResponseEntity check(int type,String email){
        if(type < 0 || StringUtils.isEmpty(email)){
            return Response.badRequest();
        }
        return Response.ok(userService.checkRegister(type,email));
    }

    /**
     * 获取验证码
     * @param type
     * @param email
     * @return
     */
    @GetMapping("/getCode")
    public ResponseEntity getCode(int type,String email){
        if(type < 0 || StringUtils.isEmpty(email)){
            return Response.badRequest();
        }
        return Response.ok(userService.sendRegisterCode(type,email));
    }
}
