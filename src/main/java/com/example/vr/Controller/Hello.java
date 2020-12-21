package com.example.vr.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class Hello {

    @RequestMapping(value = "/")
    public String index(){
        return "index";
    }

}
