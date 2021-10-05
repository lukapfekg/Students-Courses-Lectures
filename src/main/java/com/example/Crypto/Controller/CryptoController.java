package com.example.Crypto.Controller;

import com.example.Crypto.Model.Market;
import com.example.Crypto.Service.CryptoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
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
