package com.oladecode.bankapi.datasource.mock

import com.oladecode.bankapi.datasource.BankDataSource
import com.oladecode.bankapi.model.Bank
import org.springframework.stereotype.Repository
import java.lang.IllegalArgumentException


@Repository
class MockBankDataSource : BankDataSource {
    val banks =  mutableListOf(Bank("1233",3.142,1),
        Bank("1433",17.0,0),
                Bank("3772",42.3,1))
    override fun retrieveBanks(): Collection<Bank>  = banks
    override fun retrieveBank(accountNumber: String): Bank {

        return banks.firstOrNull()
        { it.accountNumber == accountNumber }
            ?: throw NoSuchElementException("could not find a bank with account number $accountNumber")

    }

    override fun createBank(bank: Bank): Bank {

        if (banks.any{it.accountNumber == bank.accountNumber}){
            throw IllegalArgumentException("Bank with account number ${bank.accountNumber} already exists")
        }

        banks.add(bank)
        return bank;


    }


}