package com.jhsoft.SofBank.controllers;

import com.jhsoft.SofBank.domains.dtos.UsersDTO;
import com.jhsoft.SofBank.domains.entities.Users;
import com.jhsoft.SofBank.domains.services.UsersServices;
import com.jhsoft.SofBank.exceptions.UsersNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UsersController {

    @Autowired
    private UsersServices usersServices;

    @PostMapping
    public Mono<ResponseEntity<List<Users>>> createUsers(@RequestBody List<UsersDTO> usersDTOs) {

        return Flux.fromIterable(usersDTOs)
                .flatMap(usersServices::createUser)
                .collectList()
                .map(ResponseEntity::ok);
    }

    @GetMapping("/{identification}")
    public ResponseEntity<Users> getUserByIdentification(@PathVariable String identification ){
        Users userAccount = usersServices.getUserByIdentification(identification);
        return ResponseEntity.ok(userAccount);
    }

    @DeleteMapping("/delete/{identification}")
    public ResponseEntity<Void> deleteUser(@PathVariable String identification){
        try{
            usersServices.deleteUser(identification);
            return ResponseEntity.noContent().build();
        } catch (UsersNotFoundException e) {
            return ResponseEntity.noContent().build();
        }
    }

    @PutMapping("/update/{identification}")
    public ResponseEntity<Users> updateUser(@PathVariable String identification, @RequestBody UsersDTO usersDTO ){
        Users userModify = usersServices.updateUser(identification, usersDTO);
        return ResponseEntity.ok(userModify);
    }
}
