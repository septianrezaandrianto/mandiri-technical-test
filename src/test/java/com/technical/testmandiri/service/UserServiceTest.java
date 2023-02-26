package com.technical.testmandiri.service;

import com.technical.testmandiri.dto.PageResponse;
import com.technical.testmandiri.dto.ResponseDetail;
import com.technical.testmandiri.dto.UserRequest;
import com.technical.testmandiri.dto.UserResponse;
import com.technical.testmandiri.entity.User;
import com.technical.testmandiri.entity.UserSetting;
import com.technical.testmandiri.exception.NotFoundException;
import com.technical.testmandiri.repository.UserRepository;
import com.technical.testmandiri.repository.UserSettingRepository;
import com.technical.testmandiri.repository.UserSettingRepositoryTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import  static org.mockito.Mockito.*;
import  static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    private static SimpleDateFormat FORMAT_REQUEST_DB = new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat ISO_FORMAT_8601 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");


    @Mock
    private UserRepository userRepository;
    @Mock
    private UserSettingRepository userSettingRepository;
    @InjectMocks
    private UserService userService;

    @Test
    @DisplayName("should return success create_success")
    public void create_success() throws ParseException {
        when(userRepository.save(any(User.class)))
                .thenReturn(user(true, false));
        ResponseDetail result = userService.create(userRequest());
        assertEquals(responseDetail(true, false), result);
    }

    @Test
    @DisplayName("should return success getUserById_success")
    public void getUserById_success() throws ParseException {
        when(userRepository.findByUserId(true, 1L))
                .thenReturn(user(true, false));
        when(userSettingRepository.findByUserId(1L))
                .thenReturn(userSettingList(true, false));
        ResponseDetail result = userService.getUserById(1L);
        assertEquals(responseDetail(true, false), result);
    }

    @Test
    @DisplayName("should return success getUserById_notFound")
    public void getUserById_notFound() {
        when(userRepository.findByUserId(true, 5L))
                .thenReturn(null);
        assertThrows(NotFoundException.class, () -> {
            userService.getUserById(5L);
        });
    }

    @Test
    @DisplayName("should return success getAllData_success")
    public void getAllData_success() throws ParseException {
        when(userRepository.findAllData(PageRequest.of(0, 10)))
                .thenReturn(userPage(true, false, true));

        PageResponse pageResponse = userService.getAllData("10", "0");
        assertEquals(1, pageResponse.getUser_data().size());
        assertEquals(10, pageResponse.getMax_records());
        assertEquals(0, pageResponse.getOffset());
    }

    @Test
    @DisplayName("should return success getAllData_emptyData")
    public void getAllData_emptyData() throws ParseException {
        when(userRepository.findAllData(PageRequest.of(0, 10)))
                .thenReturn(userPage(true, false, false));
        assertThrows(NotFoundException.class, () -> {
            userService.getAllData("10", "0");
        });
    }

    @Test
    @DisplayName("should return success updateUser_success")
    public void updateUser_success() throws ParseException {
        when(userRepository.findByUserId(true, 1L))
                .thenReturn(user(true, false));
        when(userSettingRepository.findByUserId(1L))
                .thenReturn(userSettingList(true, false));
        ResponseDetail result = userService.updateUser(1L, userRequestUpdate());
        assertNotNull(result);
    }

    @Test
    @DisplayName("should return success updateUser_notFound")
    public void updateUser_notFound() {
        when(userRepository.findByUserId(true, 5L))
                .thenReturn(null);
        assertThrows(NotFoundException.class, () -> {
            userService.updateUser(5L, userRequestUpdate());
        });
    }

    @Test
    @DisplayName("should return success updateUserSetting_success")
    public void updateUserSetting_success() throws ParseException {
        when(userSettingRepository.findByUserId(1L))
                .thenReturn(userSettingList(true, false));
        when(userRepository.findByUserId(true, 1L))
                .thenReturn(user(true, false));
        ResponseDetail result = userService.updateUserSetting(1L,
                mappingUserSettingList(userSettingList(true, false)));
        assertNotNull(result);
    }

    @Test
    @DisplayName("should return success updateUserSetting_notFound")
    public void updateUserSetting_notFound() {
        when(userSettingRepository.findByUserId(5L))
                .thenReturn(new ArrayList<>());
        assertThrows(NotFoundException.class, ()-> {
           userService.updateUserSetting(5L,
                   mappingUserSettingList(userSettingList(true, false)));
        });
    }

    @Test
    @DisplayName("should return success deleteData_success")
    public void deleteData_success() throws ParseException {
        when(userRepository.findByUserId(true, 1L))
                .thenReturn(user(true, false));
        userService.deleteData(1L);
    }

    @Test
    @DisplayName("should return success deleteData_notFound")
    public void deleteData_notFound() {
        when(userRepository.findByUserId(true, 1L)).thenReturn(null);
        assertThrows(NotFoundException.class, () -> {
            userService.deleteData(1L);
        });
    }

    @Test
    @DisplayName("should return success refreshUser_success")
    public void refreshUser_success() throws ParseException {
        when(userRepository.findByUserId(false, 1L))
                .thenReturn(user(true, false));
        when(userSettingRepository.findByUserId(1L))
                .thenReturn(userSettingList(true, false));
        ResponseDetail result = userService.refreshUser(1L);
        assertNotNull(result);
    }

    @Test
    @DisplayName("should return success refreshUser_notFound")
    public void refreshUser_notFound() {
        when(userRepository.findByUserId(false, 1L)).thenReturn(null);
        assertThrows(NotFoundException.class, () -> {
            userService.refreshUser(1L);
        });
    }

    private UserRequest userRequestUpdate() {
        return UserRequest.builder()
                .ssn("1")
                .first_name("Septianto")
                .middle_name("Rezaa")
                .last_name("Andriantoo")
                .birth_date("2000-01-02")
                .build();
    }

    private UserRequest userRequest() {
        return UserRequest.builder()
                .ssn("1")
                .first_name("Septian")
                .middle_name("Reza")
                .last_name("Andrianto")
                .birth_date("2000-01-01")
                .build();
    }

    private PageResponse pageResponse(boolean isActive, boolean isDeleted) {
        return PageResponse.builder()
                .user_data(userResponseList(isActive, isDeleted))
                .max_records(10)
                .offset(0)
                .build();
    }

    private ResponseDetail responseDetail(boolean isActive, boolean isDeleted) throws ParseException {
        return ResponseDetail.builder()
                .user_data(userResponse(isActive, isDeleted))
                .user_settings(mappingUserSettingList(userSettingList(isActive, isDeleted)))
                .build();
    }

    List<UserResponse> userResponseList(boolean isActive, boolean isDeleted) {
        List<UserResponse> result = new ArrayList<>();
        result.add(userResponse(isActive, isDeleted));
        return result;
    }

    private UserResponse userResponse(boolean isActive, boolean isDeleted) {
        return UserResponse.builder()
                .id(1L)
                .ssn("0000000000000001")
                .first_name("Septian")
                .middle_name("Reza")
                .last_name("Andrianto")
                .birth_date("2000-01-01")
                .created_time(ISO_FORMAT_8601.format(new Date()))
                .updated_time(ISO_FORMAT_8601.format(new Date()))
                .created_by("SYSTEM")
                .updated_by("SYSTEM")
                .is_active(isActive)
                .deleted_time(isDeleted ? ISO_FORMAT_8601.format(new Date()) : null)
                .build();
    }

    private List<Map<String, String>> mappingUserSettingList(List<UserSetting> userSettingList) {
        List<Map<String, String>> mapList = new ArrayList<>();
        for(UserSetting userSetting : userSettingList) {
            Map<String, String> map = new TreeMap<>();
            map.put(userSetting.getKey(), userSetting.getValue());
            mapList.add(map);
        }
        return mapList;
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

    private Page<User> userPage(boolean isActive, boolean isDleeted, boolean isData) throws ParseException {
        return new PageImpl<>(isData ? userList(isActive, isDleeted) : new ArrayList<>(),
                PageRequest.of(0, 10), isData ? userList(isActive, isDleeted).size() : 0);
    }

    private List<User> userList(boolean isActive, boolean isDeleted) throws ParseException {
        List<User> result = new ArrayList<>();
        result.add(user(isActive, isDeleted));
        return result;
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
