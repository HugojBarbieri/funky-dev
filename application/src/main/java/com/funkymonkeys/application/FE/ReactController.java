package com.funkymonkeys.application.FE;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ReactController {

    @RequestMapping(value = { "/", "/{path:[^\\.]*}" })
    public String forwardReactApp() {
        // Forward to the React app
        return "forward:/index.html";
    }
}
