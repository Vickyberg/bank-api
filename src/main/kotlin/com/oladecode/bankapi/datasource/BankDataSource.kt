package com.oladecode.bankapi.datasource

import com.oladecode.bankapi.model.Bank

interface BankDataSource {

    fun retrieveBanks(): Collection<Bank>
}