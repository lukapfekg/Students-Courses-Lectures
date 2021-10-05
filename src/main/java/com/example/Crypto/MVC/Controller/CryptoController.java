package com.example.Crypto.MVC.Controller;

import com.example.Crypto.MVC.Model.Market;
import com.example.Crypto.MVC.Service.CryptoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/crypto")
public class CryptoController {
    CryptoService cryptoService;

    @Autowired
    CryptoController(CryptoService cryptoService) {
        this.cryptoService = cryptoService;
    }

    @GetMapping
    public List<Market> getNewPrices() {
        return cryptoService.getNewPrices();
    }

}
