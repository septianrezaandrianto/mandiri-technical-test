package com.technical.testmandiri.repository;

import com.technical.testmandiri.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT * FROM user_table WHERE is_active = true", nativeQuery = true)
    Page<User> findAllData(Pageable pageable);

    @Query(value = "SELECT * FROM user_table WHERE is_active = ?1 AND id = ?2", nativeQuery = true)
    User findByUserId(Boolean isActive, Long id);

    @Query(value = "SELECT * FROM user_table WHERE is_active = true AND ssn = ?1", nativeQuery = true)
    User findBySsn(String ssn);

    @Transactional
    @Modifying
    @Query(value="UPDATE User SET firstName = ?1, middleName = ?2, lastName = ?3, " +
            "birthDate = ?4, updatedTime =?5 WHERE id = ?6")
    int updateUser(String firstName, String middleName, String lastName, Date birthDate,
                    Date updatedTime, Long id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE User SET isActive = ?1 , deletedTime = ?2 WHERE id = ?3")
    int updateDeleteUser(Boolean isActive, Date deleteDate, Long id);
}
