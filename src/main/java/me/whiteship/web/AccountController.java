package me.whiteship.web;

import me.whiteship.domain.Account;
import me.whiteship.dto.AccountDto;
import me.whiteship.exception.UsernameDuplicatedException;
import me.whiteship.repository.AccountRepository;
import me.whiteship.security.CurrentUser;
import me.whiteship.security.UserDetailsImpl;
import me.whiteship.service.AccountService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Baik KeeSun
 */
@Controller
public class AccountController {

    @Autowired
    AccountService service;

    @Autowired
    AccountRepository repository;

    @Autowired
    ModelMapper modelMapper;

    // 아무나
    @RequestMapping(value = "/accounts", method = RequestMethod.POST)
    public ResponseEntity createAccount(
            @RequestBody @Valid AccountDto.CreateRequest createRequest,
            BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Account newAccount = service.createAccount(createRequest);
        AccountDto.Response response = modelMapper.map(newAccount, AccountDto.Response.class);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // 로그인한 유저만 접근
    @RequestMapping(value = "/accounts", method = RequestMethod.GET)
    public ResponseEntity findAll(@CurrentUser UserDetailsImpl userDetails) {
        System.out.println("====================================");
        System.out.println(userDetails.getAccount().getUsername());

        List<AccountDto.Response> allAccounts = repository.findAll().stream()
                .map(a -> modelMapper.map(a, AccountDto.Response.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(allAccounts, HttpStatus.OK);
    }

}
