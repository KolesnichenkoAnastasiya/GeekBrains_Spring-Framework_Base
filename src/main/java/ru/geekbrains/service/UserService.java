package ru.geekbrains.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.geekbrains.model.Role;
import ru.geekbrains.model.User;
import ru.geekbrains.model.dto.UserDto;
import ru.geekbrains.model.mapper.UserDtoMapper;
import ru.geekbrains.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService  {

    private final UserDtoMapper mapper;
    private final UserRepository userRepository;

    private final PasswordEncoder encoder;

    public Page<UserDto> findAllByFilter(String usernameFilter, String emailFilter, int page, int size, String sortField) {
        usernameFilter = usernameFilter == null || usernameFilter.isBlank() ? null : "%" + usernameFilter.trim() + "%";
        emailFilter = emailFilter == null || emailFilter.isBlank() ? null : "%" + emailFilter.trim() + "%";
        return userRepository.usersByFilter(usernameFilter, emailFilter, PageRequest.of(page, size, Sort.by(sortField)))
                .map(mapper::map);
    }

    public Optional<UserDto> findUserById(Long id) {
        return userRepository.findById(id).map(mapper::map);
    }

    @Transactional
    public void update (UserDto dto) {
        if(!userRepository.findById(dto.getId()).isPresent()) {
            userRepository.save(mapper.map(dto, encoder));
            } else {
            userRepository
                    .findById(dto.getId())
                    .ifPresent(user ->  {
                        user.setUsername(dto.getUsername());
                        user.setEmail(dto.getEmail());
                        user.setPassword(dto.getPassword());
                        user.setRoles(dto.getRoles());
                        userRepository.save(user);
                    });
        }
    }

    public void save(UserDto dto) {
        userRepository.save(mapper.map(dto, encoder));
    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    public org.springframework.security.core.userdetails.User findUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(user -> new org.springframework.security.core.userdetails.User(
                        user.getUsername(),
                        user.getPassword(),
                        user.getRoles().stream()
                                        .map(role-> new SimpleGrantedAuthority(role.getName()))
                                                .collect(Collectors.toList())
                )).orElseThrow(() -> new UsernameNotFoundException(username));
    }
}

