package com.oladecode.bankapi.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.oladecode.bankapi.model.Bank
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder.json
import org.springframework.test.web.servlet.*


@SpringBootTest
@AutoConfigureMockMvc
internal class BankControllerTest @Autowired constructor(
    private val mockMvc: MockMvc,

    private val objectMapper: ObjectMapper
){



    val baseUrl = "/api/banks"

    @Nested
    @DisplayName("GET /api/bank")
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
    @DisplayName("GET /api/bank/{accountNumber}")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)

    inner  class GetBank{
        @Test
        fun `should return the bank with given account number`(){
            // given
            val accountNumber = "1233"
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


    @Nested
    @DisplayName("POST /api/banks")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class PostNewBank{

        @Test
        fun `should `(){
            // given
            val newBank = Bank("abcd",1.3,20)


            //when
            val performPost = mockMvc.post(baseUrl)
                {
                contentType = MediaType.APPLICATION_JSON
                    content =objectMapper.writeValueAsString(newBank)
                }

                    //then
                performPost
                    .andDo { print() }
                    .andExpect {
                        status { isCreated() }
                        content { contentType(MediaType.APPLICATION_JSON_VALUE)
                        json(objectMapper.writeValueAsString(newBank))
                    }
                    }

            mockMvc.get("$baseUrl/${newBank.accountNumber}")
                .andExpect { content { json(objectMapper.writeValueAsString(newBank)) } }


        }





    }
    
    @Test
    fun `should return BAD REQUEST if bank with given account number already exists`(){

        // given
        val invalidBank = Bank("1233",1.0,1)
        
        //when
        val performPost = mockMvc.post(baseUrl)
        {
            contentType = MediaType.APPLICATION_JSON
            content =objectMapper.writeValueAsString(invalidBank)
        }
        
        //then
        performPost
            .andDo { print() }
            .andExpect { status { isBadRequest() } }
        
        
        }
    
    
    @Nested
    @DisplayName("PATCH /api/banks")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class PatchExistingBank {
        
        @Test
        fun `should update an existing bank `(){
            // given
            val updatedBank = Bank("1233",3.142,1)
            //when
            val performPatchRequest = mockMvc.patch(baseUrl)
            {
                contentType = MediaType.APPLICATION_JSON
                content =objectMapper.writeValueAsString(updatedBank)
            }
            //then

            performPatchRequest
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON_VALUE)
                    json(objectMapper.writeValueAsString(updatedBank))
                    }
                }

            mockMvc.get("$baseUrl/${updatedBank.accountNumber}")
                .andExpect { content { json(objectMapper.writeValueAsString(updatedBank)) } }
            
            }

    
    }


    @Test
    fun `should return BAD REQUEST if no bank with given account number exists`(){
        // given
        val invalidBank = Bank("not-valid",1.0,1)

        //when
        val performPatchRequest = mockMvc.patch(baseUrl){
            contentType = MediaType.APPLICATION_JSON
            content =objectMapper.writeValueAsString(invalidBank)
        }
        //then
        performPatchRequest
            .andDo { print() }
            .andExpect { status {
                isNotFound()
            } }




        }

    @Nested
    @DisplayName("DELETE /api/banks/{accountNumber}")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class DeleteExistingBank{
        
        @Test
        fun `should delete exiting bank with provide account number`(){
            // given
            val accountNumber  = "1233"
            //when/then
            mockMvc.delete("$baseUrl/$accountNumber")

                .andDo { print() }
                .andExpect {
                    status { isNoContent() }
                }
            mockMvc.get("$baseUrl/$accountNumber")
                .andExpect { status { isNotFound() } }
            
            }

        @Test
        fun `should return NOT FOUND if no bank with given account number exists`(){

            // given
            val  invalidAccountNumber  = "does_not_exist"
            //when/then

            mockMvc.delete("$baseUrl/$invalidAccountNumber")
                .andDo { print() }
                .andExpect { status { isNotFound() } }


            }




    }
        
    
    
    
    

        




        
}