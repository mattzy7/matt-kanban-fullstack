package com.matt.service;

import com.github.benmanes.caffeine.cache.Cache;
import com.matt.bo.UserBO;
import com.matt.cache.ThreadLocalCache;
import com.matt.configuration.JwtConfiguration;
import com.matt.dto.request.LoginRequestDTO;
import com.matt.dto.request.LogoutRequestDTO;
import com.matt.dto.response.UserLoginResponseDTO;
import com.matt.enums.UserSearchTypeEnum;
import com.matt.exceptions.EnumNotImplementedException;
import com.matt.exceptions.InvalidParameterException;
import com.matt.exceptions.UserNotFoundException;
import com.matt.utils.Constants;
import com.matt.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@Service
public class AuthService {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtConfiguration jwtConfiguration;
    @Autowired
    private Cache<String, String> tokenCache;


    private void validateLoginRequestParams(LoginRequestDTO request) throws InvalidParameterException {
        if (!StringUtils.hasLength(request.getPassword())) {
            throw new InvalidParameterException("User credential(s) are missing");
        }

        if (request.getLoginType() == UserSearchTypeEnum.EMAIL && !StringUtils.hasLength(request.getEmail())) {
            throw new InvalidParameterException("User credential(s) are missing");
        }

        if (request.getLoginType() == UserSearchTypeEnum.USERNAME && !StringUtils.hasLength(request.getUsername())) {
            throw new InvalidParameterException("User credential(s) are missing");
        }
    }


    public UserLoginResponseDTO login(LoginRequestDTO request) throws
            InvalidParameterException,
            UserNotFoundException,
            NoSuchAlgorithmException,
            InvalidKeySpecException, EnumNotImplementedException {
        //
        validateLoginRequestParams(request);

        String key = switch (request.getLoginType()) {
            case EMAIL -> request.getEmail();
            case USERNAME -> request.getUsername();
            default -> throw new EnumNotImplementedException(request.getLoginType().toString());
        };

        UserBO userBO = userService.getUserByKeyPassword(request.getLoginType(), key, request.getPassword());
        if (userBO == null) {
            throw new UserNotFoundException("User cannot be found with provided query params.");
        }

        String jwtToken = JwtUtil.generateToken(
                String.valueOf(userBO.getId()),
                jwtConfiguration.getExpirationInSec(),
                jwtConfiguration.getSecret());

        // cache the jwt token
        tokenCache.put(Constants.CACHE_KEY_TOKEN_PREFIX + jwtToken, String.valueOf(userBO.getId()));

        return new UserLoginResponseDTO(userBO.getId(), userBO.getEmail(), userBO.getUsername(), jwtToken);
    }

    public void logout(LogoutRequestDTO request) {
        tokenCache.invalidate(Constants.CACHE_KEY_TOKEN_PREFIX + request.getJwtToken());
        ThreadLocalCache.clear();
    }
}
