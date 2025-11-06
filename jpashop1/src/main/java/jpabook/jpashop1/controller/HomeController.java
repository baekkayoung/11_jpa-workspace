package jpabook.jpashop1.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller // bean에 관리 대상으로 올라감
@Slf4j // = Logger log = LoggerFactory.getLogger(getClass()); : 로그 찍기
public class HomeController {

    // Logger log = LoggerFactory.getLogger(getClass()); : 로그 찍기

    @RequestMapping("/")
    public String Home(){
        log.info("home controller");
        return "home"; // home.html로 return
    }




}
