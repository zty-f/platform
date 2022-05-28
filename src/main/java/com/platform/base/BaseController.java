package com.platform.base;

import com.platform.entity.Student;
import org.modelmapper.ModelMapper;

import javax.servlet.http.HttpServletRequest;

/**
 * @author by Hchier
 * @Date 2022/5/20 10:39
 */
public class BaseController {
    protected final static ModelMapper MODEL_MAPPER = new ModelMapper();
    //    @Autowired
//    private WebContext webContext;

    public BaseController() {

    }

    protected void setCurrentUser(HttpServletRequest request, String role, String username) {
        request.getSession().setAttribute("role", role);
        request.getSession().setAttribute("username", username);

    }

    protected String[] getCurrentUser(HttpServletRequest request) {
        return new String[]{(String) request.getSession().getAttribute("role"), (String) request.getSession().getAttribute("username")};
    }

    protected void removeCurrentUser(HttpServletRequest request) {
        request.getSession().removeAttribute("role");
        request.getSession().removeAttribute("username");
    }
}
