package com.hkmedium.jpamapping.service;

import com.hkmedium.jpamapping.entity.Address;
import com.hkmedium.jpamapping.entity.User;
import com.hkmedium.jpamapping.repository.AddressRepository;
import com.hkmedium.jpamapping.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@Transactional
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressRepository addressRepository;

    public User createUserWithAddress(User user) {
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
    }

    public User getUserByAddressId(Long addressId) {
        return userRepository.findUserByAddressId(addressId);
    }

    public User updateUser(Long userId, User updatedUser) {
        User user = getUserById(userId);
        if (StringUtils.hasText(updatedUser.getUserName())) {
            user.setUserName(updatedUser.getUserName());
        }

        Address address = user.getAddress();
        if (address == null) {
            address = new Address();
            user.setAddress(address);
        }

        Address updatedAddress = updatedUser.getAddress();
        if (updatedAddress != null) {
            user.getAddress().setStreet(updatedAddress.getStreet());
            user.getAddress().setCity(updatedAddress.getCity());
        }

        return userRepository.save(user);
    }

    public String deleteUser(Long userId) {
        User user = getUserById(userId);
        userRepository.delete(user);
        return "User deleted successfully";
    }
}
