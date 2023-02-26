package com.technical.testmandiri.repository;

import com.technical.testmandiri.entity.UserSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface UserSettingRepository extends JpaRepository<UserSetting, Long> {

    @Query(value="SELECT a.* FROM user_setting_table a " +
            "JOIN user_table b " +
            "ON a.user_id = b.id " +
            "WHERE b.id = ?1 AND b.is_active = true", nativeQuery = true)
    List<UserSetting> findByUserId(Long id);

    @Query(value = "UPDATE UserSetting SET value = ?1 WHERE key = ?2")
    @Modifying
    @Transactional
    int updateUserSetting(String value, String key);
}
