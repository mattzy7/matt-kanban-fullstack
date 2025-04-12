package com.matt.converter;

import com.matt.bo.UserBO;
import com.matt.entity.UserEntity;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


public class UserConverter {

    public static UserBO convertEntityToBO(UserEntity entity) {
        if (entity == null) {
            return null;
        }

        UserBO bo = new UserBO();
        bo.setId(entity.getId());
        bo.setCreatorId(entity.getCreatorId());
        bo.setUsername(entity.getUsername());
        bo.setPassword(entity.getPassword());
        bo.setEmail(entity.getEmail());
        bo.setSalt(entity.getSalt());

        return bo;
    }

    public static List<UserBO> convertEntityListToBOList(List<UserEntity> entityList) {
        if (entityList == null || entityList.isEmpty()) {
            return Collections.emptyList();
        }

        return entityList.stream()
                .map(UserConverter::convertEntityToBO)
                .collect(Collectors.toList());
    }
}
