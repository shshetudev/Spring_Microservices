package com.shshetudev.api.users.ui.controllers;

import com.shshetudev.api.users.data.UserEntity;
import com.shshetudev.api.users.service.UsersService;
import com.shshetudev.api.users.shared.UserDto;
import com.shshetudev.api.users.ui.model.CreateUserRequestModel;
import com.shshetudev.api.users.ui.model.CreateUserResponseModel;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Controller class for user signup, signin, fetch, delete
 *
 * @author Md. Shahariar Hossen
 * @since 30th March, 2022
 */
@RestController
@RequestMapping("/users")
public class UsersController {
    // We want to read properties from environment
    private final Environment env;

    private UsersService usersService;

    public UsersController(Environment env, UsersService usersService) {
        this.env = env;
        this.usersService = usersService;
    }

    @GetMapping("/status/check")
    public String status() {
        return "Working on port " + env.getProperty("local.server.port");
    }

    @PostMapping(
            consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<CreateUserResponseModel> create(@Valid @RequestBody CreateUserRequestModel userDetails) {
        return ResponseEntity.status(HttpStatus.CREATED).body(toUserResponse(usersService.create(toUserDto(userDetails))));
    }

    private UserDto toUserDto(CreateUserRequestModel userDetails) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT); // Matching strategy is strict
        return mapper.map(userDetails, UserDto.class);
    }

    private CreateUserResponseModel toUserResponse(UserDto userDetails) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT); // Matching strategy is strict
        return mapper.map(userDetails, CreateUserResponseModel.class);
    }
}
