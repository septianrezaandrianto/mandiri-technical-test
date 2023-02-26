package com.technical.testmandiri.service;

import com.technical.testmandiri.dto.*;
import com.technical.testmandiri.entity.User;
import com.technical.testmandiri.entity.UserSetting;
import com.technical.testmandiri.exception.NotFoundException;
import com.technical.testmandiri.repository.UserRepository;
import com.technical.testmandiri.repository.UserSettingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class UserService {

    private static SimpleDateFormat FORMAT_REQUEST_DB = new SimpleDateFormat("yyyy-MM-dd");
    private static SimpleDateFormat ISO_FORMAT_8601 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserSettingRepository userSettingRepository;

    public ResponseDetail create(UserRequest userRequest) throws ParseException {
        User user = User.builder()
                .ssn(String.format("%16s", userRequest.getSsn()).replace(" ", "0"))
                .firstName(userRequest.getFirst_name())
                .middleName(userRequest.getMiddle_name())
                .lastName(userRequest.getLast_name())
                .birthDate(FORMAT_REQUEST_DB.parse(userRequest.getBirth_date()))
                .createdTime(new Date())
                .updatedTime(new Date())
                .updatedBy("SYSTEM")
                .createdBy("SYSTEM")
                .isActive(true)
                .build();
        User savedUser = userRepository.save(user);
        List<String> listKey = List.of("biometric_login", "push_notification", "sms_notification" ,
                "show_onboarding", "widget_order");

        List<UserSetting> userSettingList = new ArrayList<>();
        for(int i = 0; i< listKey.size(); i++) {
            UserSetting userSetting = new UserSetting();
            userSetting.setUserId(savedUser);
            userSetting.setKey(listKey.get(i));
            if(listKey.get(i).equals("widget_order")) {
                userSetting.setValue("1,2,3,4,5");
            } else {
                userSetting.setValue("false");
            }
            userSettingList.add(userSetting);
        }
        userSettingRepository.saveAll(userSettingList);

        UserResponse userResponse =  UserResponse.builder()
                .id(savedUser.getId())
                .ssn(savedUser.getSsn())
                .first_name(userRequest.getFirst_name())
                .middle_name(userRequest.getMiddle_name())
                .last_name(userRequest.getLast_name())
                .birth_date(userRequest.getBirth_date())
                .is_active(savedUser.getIsActive())
                .created_by(savedUser.getCreatedBy())
                .updated_by(savedUser.getUpdatedBy())
                .updated_time(ISO_FORMAT_8601.format(savedUser.getUpdatedTime()))
                .created_time(ISO_FORMAT_8601.format(savedUser.getCreatedTime()))
                .build();

        return ResponseDetail.builder()
                .user_data(userResponse)
                .user_settings(mappingUserSettingList(userSettingList))
                .build();
    }

    public ResponseDetail getUserById(Long id) {
        User user = userRepository.findByUserId(true, id);
        if(user == null) {
            throw new NotFoundException(String.valueOf(id));
        }

        UserResponse userResponse = UserResponse.builder()
                .id(user.getId())
                .ssn(user.getSsn())
                .first_name(user.getFirstName())
                .middle_name(user.getMiddleName())
                .last_name(user.getLastName())
                .birth_date(FORMAT_REQUEST_DB.format(user.getBirthDate()))
                .is_active(user.getIsActive())
                .created_by(user.getCreatedBy())
                .updated_by(user.getUpdatedBy())
                .updated_time(ISO_FORMAT_8601.format(user.getUpdatedTime()))
                .created_time(ISO_FORMAT_8601.format(user.getCreatedTime()))
                .build();

        List<UserSetting> userSettingList = userSettingRepository.findByUserId(id);

        return ResponseDetail.builder()
                .user_data(userResponse)
                .user_settings(mappingUserSettingList(userSettingList))
                .build();
    }

    public PageResponse getAllData(String max_records, String offset) {
        Pageable pageable = PageRequest.of(Integer.valueOf(offset),
                Integer.valueOf(max_records));
        Page<User> userPage = userRepository.findAllData(pageable);
        if(userPage.getContent().isEmpty()) {
            throw new NotFoundException();
        }

        List<UserResponse> userResponseList = new ArrayList<>();
        UserResponse userResponse = new UserResponse();
        for(User data : userPage.getContent()) {
            userResponse = mappinguserResponse(data, userResponse);
            userResponseList.add(userResponse);
        }

        return PageResponse.builder()
                .user_data(userResponseList)
                .max_records(Integer.valueOf(max_records))
                .offset(Integer.valueOf(offset))
                .build();
    }

    public ResponseDetail updateUser(Long id, UserRequest userRequest) throws ParseException {
        User user = userRepository.findByUserId(true, id);
        if(user == null ) {
            throw new NotFoundException(String.valueOf(id));
        }

        boolean isUpdate = false;
        if(!user.getFirstName().equalsIgnoreCase(userRequest.getFirst_name()) ||
                !user.getMiddleName().equalsIgnoreCase(userRequest.getMiddle_name()) ||
                !user.getLastName().equalsIgnoreCase(userRequest.getLast_name()) ||
                !FORMAT_REQUEST_DB.format(user.getBirthDate()).equalsIgnoreCase(userRequest.getBirth_date())) {
            isUpdate = true;
        }

        if(isUpdate) {
            user.setFirstName(userRequest.getFirst_name());
            user.setMiddleName(userRequest.getMiddle_name());
            user.setLastName(userRequest.getLast_name());
            user.setBirthDate(FORMAT_REQUEST_DB.parse(userRequest.getBirth_date()));
            userRepository.updateUser(user.getFirstName(), user.getMiddleName(),
                    user.getLastName(), user.getBirthDate(), new Date(), id);
        }

        UserResponse userResponse = new UserResponse();
        userResponse = mappinguserResponse(user, userResponse);
        return ResponseDetail.builder()
                .user_data(userResponse)
                .user_settings(mappingUserSettingList(userSettingRepository.findByUserId(id)))
                .build();
    }

    public ResponseDetail updateUserSetting(Long id, List<Map<String, String>> mapRequestList) {
        List<UserSetting> userSettingList = userSettingRepository.findByUserId(id);
        if(userSettingList.isEmpty()) {
            throw new NotFoundException(String.valueOf(id));
        }

        mapRequestList.forEach(datas -> {
            datas.forEach((key, value) -> {
                userSettingList.forEach(a -> {
                    if(key.equalsIgnoreCase(a.getKey()) && !value.equalsIgnoreCase(a.getValue())) {
                        a.setValue(value);
                       userSettingRepository.updateUserSetting(a.getValue(), key);
                    }
                });
            });
        });

        User user = userRepository.findByUserId(true, id);
        UserResponse userResponse = new UserResponse();
        userResponse = mappinguserResponse(user, userResponse);
        return ResponseDetail.builder()
                .user_data(userResponse)
                .user_settings(mappingUserSettingList(userSettingRepository.findByUserId(id)))
                .build();
    }

    public void deleteData(Long id) {
        User user =  userRepository.findByUserId(true, id);
        if(user == null) {
            throw new NotFoundException(String.valueOf(id));
        }
        userRepository.updateDeleteUser(false, new Date(), id);
    }

    public ResponseDetail refreshUser(Long id) {
        User user =  userRepository.findByUserId(false, id);
        if(user == null) {
            throw new NotFoundException(String.valueOf(id));
        }
        userRepository.updateDeleteUser(true, null, id);
        UserResponse userResponse = new UserResponse();
        userResponse = mappinguserResponse(user, userResponse);
        return ResponseDetail.builder()
                .user_data(userResponse)
                .user_settings(mappingUserSettingList(userSettingRepository.findByUserId(id)))
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

    private UserResponse mappinguserResponse(User data, UserResponse userResponse) {
        userResponse.setId(data.getId());
        userResponse.setSsn(data.getSsn());
        userResponse.setFirst_name(data.getFirstName());
        userResponse.setMiddle_name(data.getMiddleName());
        userResponse.setLast_name(data.getLastName());
        userResponse.setBirth_date(FORMAT_REQUEST_DB.format(data.getBirthDate()));
        userResponse.setCreated_time(ISO_FORMAT_8601.format(data.getCreatedTime()));
        userResponse.setUpdated_time(ISO_FORMAT_8601.format(data.getUpdatedTime()));
        userResponse.setCreated_by(data.getCreatedBy());
        userResponse.setUpdated_by(data.getUpdatedBy());
        userResponse.setIs_active(data.getIsActive());
        return userResponse;
    }
}
