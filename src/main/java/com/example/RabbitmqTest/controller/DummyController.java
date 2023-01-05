package com.example.RabbitmqTest.controller;

import com.example.RabbitmqTest.module.DbFetcher;
import com.example.RabbitmqTest.module.SampleListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DummyController {

    @Autowired
    private DbFetcher dbFetcher;

    @Autowired
    private SampleListener sampleListener;


//    @GetMapping("/excel/stop")
//    public String excelStop(){
//        dbFetcher.stopMessageProcess();
//
//        return "";
//    }
}
