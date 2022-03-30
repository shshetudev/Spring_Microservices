package com.shshetudev.api.users.service;

import com.shshetudev.api.users.data.UserEntity;
import com.shshetudev.api.users.data.UserRepository;
import com.shshetudev.api.users.shared.UserDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UsersServiceImpl implements UsersService{
    private UserRepository repository;
    private BCryptPasswordEncoder encoder;

    public UsersServiceImpl(UserRepository repository, BCryptPasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    @Override
    public UserDto create(UserDto userDetails) {
        userDetails.setUserId(UUID.randomUUID().toString());
        //todo: Optimize this
        UserEntity userEntity = toUserEntity(userDetails);
        userEntity.setEncryptedPassword(encoder.encode(userDetails.getPassword())); // optimize later
        return toUserDto(repository.save(userEntity));
    }

    //todo: Optimize these
    //***************************************
    // Utility methods:
    //***************************************
    private UserEntity toUserEntity(UserDto userDetails) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT); // Matching strategy is strict
        return mapper.map(userDetails, UserEntity.class);
    }

    private UserDto toUserDto(UserEntity userDetails) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT); // Matching strategy is strict
        return mapper.map(userDetails, UserDto.class);
    }
}
