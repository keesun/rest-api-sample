package me.whiteship.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.whiteship.Application;
import me.whiteship.domain.Account;
import me.whiteship.dto.AccountDto;
import me.whiteship.service.AccountService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.Filter;
import javax.transaction.Transactional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Baik KeeSun
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Application.class, loader = SpringApplicationContextLoader.class)
@WebAppConfiguration
@Transactional
public class AccountControllerTest {

    @Autowired
    WebApplicationContext wac;

    @Autowired
    Filter springSecurityFilterChain;

    @Autowired
    ObjectMapper objectMapper;

    MockMvc mockMvc;

    @Autowired
    AccountService accountService;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .addFilter(springSecurityFilterChain)
                .build();
    }

    @Test
    public void createAccount() throws Exception {
        // Given
        AccountDto.CreateRequest createRequest = new AccountDto.CreateRequest();
        createRequest.setUsername("whiteship");
        createRequest.setPassword("keesun");

        // When
        ResultActions result = mockMvc.perform(post("/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createRequest)));

        // Then
        result.andDo(print());
        result.andExpect(status().isCreated());
        result.andExpect(jsonPath("$.id", is(notNullValue())));
        result.andExpect(jsonPath("$.username", is("whiteship")));
        result.andExpect(jsonPath("$.joined", is(notNullValue())));

        // When
        result = mockMvc.perform(post("/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createRequest)));

        // Then
        result.andDo(print());
        result.andExpect(status().isBadRequest());
    }

    @Test
    public void createAccount_fail() throws Exception {
        // Given
        AccountDto.CreateRequest createRequest = new AccountDto.CreateRequest();
        createRequest.setUsername("1234");
        createRequest.setPassword("keesun");

        // When
        ResultActions result = mockMvc.perform(post("/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createRequest)));

        // Then
        result.andDo(print());
        result.andExpect(status().isBadRequest());
    }

    @Test
    public void findAll() throws Exception {
        // Given
        AccountDto.CreateRequest request = new AccountDto.CreateRequest();
        request.setPassword("keesun");
        request.setUsername("whiteship");

        Account newAccount = accountService.createAccount(request);

        // When
        ResultActions result = mockMvc.perform(get("/accounts")
                .with(httpBasic(request.getUsername(), request.getPassword())));

        // Then
        result.andDo(print());
        result.andExpect(status().isOk());
    }

}