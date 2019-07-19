package com.hotmart.playground.user;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping(
        path = "${spring.data.rest.basePath}/v1/users",
        produces = MediaType.APPLICATION_JSON_UTF8_VALUE
)
public class UserController {

    private final ModelMapper modelMapper;

    private final UserService userService;

    public UserController(ModelMapper modelMapper, UserService userService) {
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    @GetMapping
    public Page<User> findAll(final Pageable pageable) {
        return userService.findAll(pageable);
    }

    @PostMapping
    public ResponseEntity signUp(@RequestBody @Valid @NotNull final UserCreationDto userDto) {
        User user = modelMapper.map(userDto, User.class);
        user.setId(null);
        userService.save(user);

        return ResponseEntity.accepted()
                .build();
    }
}
