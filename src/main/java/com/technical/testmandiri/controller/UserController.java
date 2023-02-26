package com.technical.testmandiri.controller;

import com.technical.testmandiri.dto.PageResponse;
import com.technical.testmandiri.dto.ResponseDetail;
import com.technical.testmandiri.dto.UserRequest;
import com.technical.testmandiri.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/users")
    public ResponseEntity<ResponseDetail> create(@Validated @RequestBody UserRequest userRequest) throws ParseException {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.create(userRequest));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<ResponseDetail> getUserById(@PathVariable("id")Long id)  {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping("/users")
    public ResponseEntity<PageResponse> getAllData(@RequestParam(value = "max_records",
            defaultValue = "5")String max_records, @RequestParam(value = "offset",
            defaultValue = "0")String offset)  {
        return ResponseEntity.ok(userService.getAllData(max_records, offset));
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<ResponseDetail> updateUser(@PathVariable("id")Long id,
             @Validated @RequestBody UserRequest userRequest) throws ParseException {
        return ResponseEntity.ok(userService.updateUser(id, userRequest));
    }

    @PutMapping("/users/{id}/settings")
    public ResponseEntity<ResponseDetail> updateUserSetting(@PathVariable("id")Long id,
                @Validated @RequestBody List<Map<String, String>> mapRequestList) {
        return ResponseEntity.ok(userService.updateUserSetting(id, mapRequestList));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteData(@PathVariable("id")Long id) {
        userService.deleteData(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/users/{id}/refresh")
    public ResponseEntity<ResponseDetail> refreshUser(@PathVariable("id")Long id) {
        return ResponseEntity.ok(userService.refreshUser(id));
    }
}
