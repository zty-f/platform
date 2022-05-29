package com.platform.controller;

import com.platform.base.BaseController;
import com.platform.base.ResponseCode;
import com.platform.base.RestResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @author by Hchier
 * @Date 2022/5/29 16:23
 */
@RestController
public class UserController extends BaseController {
    @GetMapping("/getCurrentUser")
    public RestResponse getUser(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("JSESSIONID")) {
                    System.out.println("存在JSESSIONIDJSESSIONIDJSESSIONIDJSESSIONIDJSESSIONID");
                    System.out.println(cookie.getValue());
                }
            }
        }
        String[] currentUser = super.getCurrentUser(request);
        System.out.println(Arrays.toString(currentUser));
        if (currentUser[0] == null || currentUser[1] == null) {
            return RestResponse.fail(ResponseCode.NOT_LOGGED_IN.getCode(), ResponseCode.NOT_LOGGED_IN.getMessage());
        }
        return RestResponse.ok(currentUser);
    }

}
