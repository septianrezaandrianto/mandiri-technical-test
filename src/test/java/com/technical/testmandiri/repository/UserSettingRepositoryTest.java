package com.technical.testmandiri.repository;

import com.technical.testmandiri.entity.User;
import com.technical.testmandiri.entity.UserSetting;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import  static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
public class UserSettingRepositoryTest {

    private static SimpleDateFormat FORMAT_REQUEST_DB = new SimpleDateFormat("yyyy-MM-dd");
    
    @Autowired
    private UserSettingRepository userSettingRepository;
    
    @Test
    @DisplayName("should return repository findByUserId")
    public void findByUserId() throws ParseException {
        List<UserSetting> result = userSettingRepository.findByUserId(1L);
        assertNotNull(result);
    }

    @Test
    @DisplayName("should return repository updateUserSetting")
    public void updateUserSetting() throws ParseException {
        for(int i= 0; i< userSettingList(true, false).size(); i++) {
            int result = userSettingRepository.updateUserSetting(userSettingList(true, false).get(i).getKey(),
                    userSettingList(true, false).get(i).getValue());
            assertNotNull(result);
        }
    }

    private List<UserSetting> userSettingList(boolean isActive, boolean isDeleted) throws ParseException {
        List<UserSetting> result = new ArrayList<>();
        result.add(userSetting("biometric_login", "false", isActive, isDeleted));
        result.add(userSetting("push_notification", "false", isActive, isDeleted));
        result.add(userSetting("sms_notification", "false", isActive, isDeleted));
        result.add(userSetting("show_onboarding", "false", isActive, isDeleted));
        result.add(userSetting("widget_order", "1,2,3,4,5", isActive, isDeleted));
        return result;
    }

    private UserSetting userSetting(String key, String value, boolean isActive, boolean isDeleted) throws ParseException {
        return UserSetting.builder()
                .userId(user(isActive, isDeleted))
                .key(key)
                .value(value)
                .build();
    }

    private User user(boolean isActive, boolean isDeleted) throws ParseException {
        return User.builder()
                .id(1L)
                .ssn("0000000000000001")
                .firstName("Septian")
                .middleName("Reza")
                .lastName("Andrianto")
                .birthDate(FORMAT_REQUEST_DB.parse("2000-01-01"))
                .createdTime(new Date())
                .updatedTime(new Date())
                .createdBy("SYSTEM")
                .updatedBy("SYSTEM")
                .isActive(isActive)
                .deletedTime(isDeleted ? new Date() : null)
                .build();
    }

}
