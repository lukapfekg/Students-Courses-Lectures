package com.example.Job.Controller;

import com.example.Job.Service.JobService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/job")
public class JobController {

    @GetMapping(path = "/crypto/get-Values")
    public void getValues() {
        JobService.runGetCryptoValue();
    }

}
