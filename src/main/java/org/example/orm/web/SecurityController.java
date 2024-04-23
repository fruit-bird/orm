package org.example.orm.web;

import org.springframework.stereotype.Controller;

@Controller
public class SecurityController {
    public String notAuthorized() {
        return "notAuthorized";
    }

    public String login() {
        return "login";
    }
}
