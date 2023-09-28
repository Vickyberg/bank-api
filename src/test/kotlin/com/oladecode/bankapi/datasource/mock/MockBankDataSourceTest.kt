package com.oladecode.bankapi.datasource.mock

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class MockBankDataSourceTest{

    private  val mockDataSource =  MockBankDataSource()
    
    @Test
    fun `should provide a collection of banks`() {

        val banks = mockDataSource.retrieveBanks()
        assertThat(banks).isNotEmpty
        assertThat(banks.size).isEqualTo(3)

    }
    
    @Test
    fun `should provide some mock data`(){
        // given
        
        //when
        val banks = mockDataSource.retrieveBanks()
        //then
        assertThat(banks).allMatch{it.accountNumber.isNotBlank()}
        assertThat(banks).anyMatch{it.trust != 0.0}
        assertThat(banks).anyMatch{it.transactionFee != 0}

        
        }
        
        


}