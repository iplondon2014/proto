package org.fcg.proto;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProtoController {

    @RequestMapping(value = "/ping", produces = "text/plain")
    public String ping(){
        return "ok";
    }
}
