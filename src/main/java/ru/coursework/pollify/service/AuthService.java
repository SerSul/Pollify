package ru.coursework.pollify.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.coursework.pollify.entity.security.User;
import ru.coursework.pollify.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Ищем пользователя по имени
        var user = findUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found.");
        }

        // Возвращаем UserDetails с ролями
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthorities(user));
    }

    public User findUserByUsername(String username) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        return userOpt.orElse(null);
    }

    public List<SimpleGrantedAuthority> getAuthorities(User user) {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        for (var role : user.getRoles()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRole().getKey()));
        }

        return authorities;
    }
}

