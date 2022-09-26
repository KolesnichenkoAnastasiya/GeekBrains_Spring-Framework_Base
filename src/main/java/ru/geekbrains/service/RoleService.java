package ru.geekbrains.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.geekbrains.model.Role;
import ru.geekbrains.repository.RoleRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public List<Role> findAll(){
        return roleRepository.findAll();
    }
}
