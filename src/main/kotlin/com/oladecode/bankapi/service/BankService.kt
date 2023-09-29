package com.oladecode.bankapi.service

import com.oladecode.bankapi.datasource.BankDataSource
import com.oladecode.bankapi.model.Bank
import org.springframework.stereotype.Service


@Service
class BankService(private val dataSource: BankDataSource) {
    fun getBanks(): Collection<Bank>  = dataSource.retrieveBanks()

    fun getBank(accountNumber:String):Bank = dataSource.retrieveBank(accountNumber)
    fun addBank(bank: Bank): Bank = dataSource.createBank(bank)
    fun updateBank(bank: Bank): Bank {

      return  dataSource.updateBank(bank)
    }

    fun deleteBank(accountNumber: String): Unit = dataSource.deleteBank(accountNumber)

}
