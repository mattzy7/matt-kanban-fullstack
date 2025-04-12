package com.matt.service;

import com.matt.bo.UserBO;
import com.matt.converter.UserConverter;
import com.matt.dto.request.UserCreateRequestDTO;
import com.matt.entity.UserEntity;
import com.matt.enums.UserSearchTypeEnum;
import com.matt.exceptions.EnumNotImplementedException;
import com.matt.exceptions.UserDuplicateException;
import com.matt.repository.UserRepository;
import com.matt.utils.PasswordUtil;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.LocalDateTime;

import static com.matt.utils.Constants.SYSTEM_USER_ID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private void checkUserExist(String username, String email) throws UserDuplicateException {
        if (userRepository.findByUsername(username) != null) {
            throw new UserDuplicateException("User name exists");
        }

        if (userRepository.findByEmail(email) != null) {
            throw new UserDuplicateException("User email exists");
        }
    }

    public UserBO createUser(UserCreateRequestDTO request)
            throws NoSuchAlgorithmException, InvalidKeySpecException, UserDuplicateException {
        checkUserExist(request.getUsername(), request.getEmail());

        String salt = PasswordUtil.generateSalt();

        UserEntity newUser = new UserEntity();
        newUser.setUsername(request.getUsername());
        newUser.setPassword(PasswordUtil.hashPassword(request.getPassword(), salt));
        newUser.setEmail(request.getEmail());
        newUser.setSalt(salt);
        newUser.setCreatedOn(LocalDateTime.now());
        newUser.setCreatorId(SYSTEM_USER_ID);
        UserEntity userEntity = userRepository.save(newUser);

        return UserConverter.convertEntityToBO(userEntity);

    }

    public UserBO getUserByKeyPassword(UserSearchTypeEnum type, String key, String password)
            throws NoSuchAlgorithmException, InvalidKeySpecException, EnumNotImplementedException {
        if(Strings.isEmpty(key) ||  Strings.isEmpty(password)) {
            return null;
        }

        UserEntity userEntity = switch (type) {
            case USERNAME -> userRepository.findByUsername(key);
            case EMAIL -> userRepository.findByEmail(key);
            default -> throw new EnumNotImplementedException(type.toString());
        };

        if(userEntity == null) {
            return null;
        }

        boolean isMatch = PasswordUtil.isPasswordMatch(password, userEntity.getPassword(), userEntity.getSalt());
        if(!isMatch) {
            return null;
        }

        return UserConverter.convertEntityToBO(userEntity);
    }
}

