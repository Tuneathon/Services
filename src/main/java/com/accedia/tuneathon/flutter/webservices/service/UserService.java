package com.accedia.tuneathon.flutter.webservices.service;

import com.accedia.tuneathon.flutter.webservices.dto.UserDTO;
import com.accedia.tuneathon.flutter.webservices.entity.User;
import com.accedia.tuneathon.flutter.webservices.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public long createUser(UserDTO userDTO) {
        User user = new User();
        user.setName(userDTO.getName());
        this.userRepository.save(user);

        return user.getId();
    }
}
