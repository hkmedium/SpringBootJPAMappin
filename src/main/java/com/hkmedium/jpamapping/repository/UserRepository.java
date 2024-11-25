package com.hkmedium.jpamapping.repository;

import com.hkmedium.jpamapping.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.address.addressId = :addressId")
    User findUserByAddressId(@Param("addressId") Long addressId);
}
