package com.oladecode.bankapi.controller

import com.oladecode.bankapi.model.Bank
import com.oladecode.bankapi.service.BankService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/banks")
class BankController(private  val service: BankService) {


    @GetMapping
    fun getBanks(): Collection<Bank> = service.getBanks()
}