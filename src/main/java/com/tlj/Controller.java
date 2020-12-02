package com.tlj;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    @GetMapping(value ="/sockethtml")
    public String sockethtml(){
        return "lliaotianshi";
    }
}
