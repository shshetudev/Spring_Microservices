package com.shshetudev.api.users.service;

import com.shshetudev.api.users.shared.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UsersService extends UserDetailsService {
    UserDto create(UserDto userDetails);
    UserDto getUserDetailsByEmail(String email);
}
