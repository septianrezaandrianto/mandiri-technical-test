package com.technical.testmandiri.controller;

import com.google.gson.Gson;
import com.technical.testmandiri.dto.PageResponse;
import com.technical.testmandiri.dto.ResponseDetail;
import com.technical.testmandiri.dto.UserRequest;
import com.technical.testmandiri.dto.UserResponse;
import com.technical.testmandiri.entity.User;
import com.technical.testmandiri.entity.UserSetting;
import com.technical.testmandiri.service.UserService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    private static SimpleDateFormat FORMAT_REQUEST_DB = new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat ISO_FORMAT_8601 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");


    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("should return success create_success")
    @Disabled
    public void create_success() throws Exception {
        when(userService.create(userRequest()))
                .thenReturn(responseDetail(true, false));

        MockHttpServletResponse result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/v1/users")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .characterEncoding("utf-8")
                        .content(new Gson().toJson(userRequest())))
                .andReturn().getResponse();

        assertEquals(HttpStatus.CREATED.value(), result.getStatus());
    }

    @Test
    @DisplayName("should return success getUserById_success")
    public void getUserById_success() throws Exception {
        when(userService.getUserById(1L))
                .thenReturn(responseDetail(true, false));

        MockHttpServletResponse result = mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/users/1")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .characterEncoding("utf-8"))
                .andReturn().getResponse();

        assertEquals(HttpStatus.OK.value(), result.getStatus());
    }

    @Test
    @DisplayName("should return success getAllData_success")
    public void getAllData_success() throws Exception {
        when(userService.getAllData(anyString(), anyString()))
                .thenReturn(pageResponse(true, false));

        MockHttpServletResponse result = mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/users")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .param("max_records", "10")
                        .param("offset", "0")
                        .characterEncoding("utf-8"))
                .andReturn().getResponse();

        assertEquals(HttpStatus.OK.value(), result.getStatus());
    }

    @Test
    @DisplayName("should return success updateUser_success")
    @Disabled
    public void updateUser_success() throws Exception {
        when(userService.updateUser(1L, userRequest()))
                .thenReturn(responseDetail(true, false));

        MockHttpServletResponse result = mockMvc.perform(MockMvcRequestBuilders
                        .put("/v1/users/1")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .characterEncoding("utf-8")
                        .content(new Gson().toJson(userRequest())))
                .andReturn().getResponse();

        assertEquals(HttpStatus.OK.value(), result.getStatus());
    }

    @Test
    @DisplayName("should return success updateUserSetting_success")
    public void updateUserSetting_success() throws Exception {
        when(userService.updateUserSetting(1L, mappingUserSettingList(userSettingList(true,
                false))))
                .thenReturn(responseDetail(true, false));

        MockHttpServletResponse result = mockMvc.perform(MockMvcRequestBuilders
                        .put("/v1/users/1/settings")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .characterEncoding("utf-8")
                        .content(new Gson().toJson(mappingUserSettingList(userSettingList(true,
                                false)))))
                .andReturn().getResponse();

        assertEquals(HttpStatus.OK.value(), result.getStatus());
    }

    @Test
    @DisplayName("should return success deleteData_success")
    public void deleteData_success() throws Exception {
        MockHttpServletResponse result = mockMvc.perform(MockMvcRequestBuilders
                        .delete("/v1/users/1")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .characterEncoding("utf-8"))
                .andReturn().getResponse();

        assertEquals(HttpStatus.NO_CONTENT.value(), result.getStatus());
        verify( userService, times(1)).deleteData(1L);
    }

    @Test
    @DisplayName("should return success refreshUser_success")
    public void refreshUser_success() throws Exception {
        when(userService.refreshUser(1L))
                .thenReturn(responseDetail(true, false));

        MockHttpServletResponse result = mockMvc.perform(MockMvcRequestBuilders
                        .put("/v1/users/1/refresh")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .characterEncoding("utf-8"))
                .andReturn().getResponse();

        assertEquals(HttpStatus.OK.value(), result.getStatus());
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
