package sc.solar.publicapp.controllers;


import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.LinkedCaseInsensitiveMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
//@RequestMapping("api")
public class Test {



    @GetMapping("/hello")
    public String hello() {
        System.out.println("Hello from Logback");
        return "Greetings from Spring Boot!";
    }

    @GetMapping("/index")
    public String index(ModelMap model, @RequestHeader Map<String, String> headers) {

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Accept-CH", "ckdshksakasbcksabskcab");

        LinkedCaseInsensitiveMap<String> requestHeaders = new LinkedCaseInsensitiveMap<>();
        requestHeaders.putAll(headers);

        return "index";
    }
}
