package com.oladecode.bankapi.controller

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get


@SpringBootTest
@AutoConfigureMockMvc
internal class BankControllerTest{

    
    @Autowired
    lateinit var mockMvc: MockMvc

    val baseUrl = "/api/banks"

    @Nested
    @DisplayName("getBanks()")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetBanks{

        @Test
        fun `should return all banks`(){
            //when-then

            mockMvc.get(baseUrl)
                .andDo { print() }
                .andExpect { status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON_VALUE) }
                    jsonPath("$[0].accountNumber"){value("1233")}
                }
        }

    }

    @Nested
    @DisplayName("getBank()")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)

    inner  class GetBank{
        @Test
        fun `should return the bank with given account number`(){
            // given
            val accountNumber = 1233
            //when/then
            mockMvc.get("$baseUrl/$accountNumber")
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON_VALUE)
                        jsonPath("$.trust") {value("3.142")}
                        jsonPath("$.transactionFee") {value("1")}
                    }
                }



        }

    }

    @Nested
    @DisplayName("notFound()")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class NotFound{

        @Test
        fun `should return NOT FOUND if account number does not exist`(){
            // given

            val accountNumber = "does_not_exist"

            //when/then
            mockMvc.get("$baseUrl/$accountNumber")
                .andExpect { status { isNotFound() } }




        }


    }
    

        




        
}