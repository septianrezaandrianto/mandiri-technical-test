package com.technical.testmandiri.repository;

import com.technical.testmandiri.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import  static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class UserRepositoryTest {

    private static SimpleDateFormat FORMAT_REQUEST_DB = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("should return repository findAllData")
    public void findAllData() {
        int offset = 0;
        int maxRecords = 10;
        PageRequest pageable = PageRequest.of(offset, maxRecords);
        Page<User> usersPage = userRepository.findAllData(pageable);
        assertNotNull(usersPage);
    }

    @Test
    @DisplayName("should return repository findByUserId")
    public void findByUserId() {
        User user = User.builder()
                .isActive(true)
                .firstName("Septian")
                .lastName("Reza")
                .build();
        user = userRepository.save(user);
        User foundUser = userRepository.findByUserId(true, user.getId());
        assertNotNull(foundUser);
        assertEquals(user.getId(), foundUser.getId());
        assertEquals(user.getFirstName(), foundUser.getFirstName());
        assertEquals(user.getLastName(), foundUser.getLastName());
    }

    @Test
    @DisplayName("should return repository findBySsn")
    public void findBySsn() {
        User user = User.builder()
                .isActive(true)
                .ssn("000000000000001")
                .firstName("Septian")
                .lastName("Reza")
                .build();
        user = userRepository.save(user);
        User foundUser = userRepository.findBySsn(user.getSsn());
        assertNotNull(foundUser);
        assertEquals(user.getSsn(), foundUser.getSsn());
    }

    @Test
    @DisplayName("should return repository updateUser")
    public void updateUser() throws ParseException {
        User user = User.builder()
                .isActive(true)
                .ssn("000000000000001")
                .firstName("Septian")
                .middleName("Reza")
                .lastName("Andrianto")
                .birthDate(FORMAT_REQUEST_DB.parse("2000-01-01"))
                .updatedTime(new Date())
                .build();
        user = userRepository.save(user);
        int updateUser = userRepository.updateUser(user.getFirstName(), user.getMiddleName(),
                user.getLastName(), user.getBirthDate(), user.getUpdatedTime(), user.getId());
        assertEquals(1, updateUser);
    }

    @Test
    @DisplayName("should return repository updateDeleteUser")
    public void updateDeleteUser() throws ParseException {
        User user = User.builder()
                .isActive(true)
                .ssn("000000000000001")
                .firstName("Septian")
                .middleName("Reza")
                .lastName("Andrianto")
                .birthDate(FORMAT_REQUEST_DB.parse("2000-01-01"))
                .updatedTime(new Date())
                .build();
        user = userRepository.save(user);
        int updateDeleteUser = userRepository.updateDeleteUser(false, new Date(), user.getId());
        assertEquals(1, updateDeleteUser);
    }
}
