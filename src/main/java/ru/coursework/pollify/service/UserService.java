package ru.coursework.pollify.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.coursework.pollify.repository.UserRepository;
import ru.coursework.pollify.service.interfaces.IUserService;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;


    private final PasswordEncoder passwordEncoder;

}
