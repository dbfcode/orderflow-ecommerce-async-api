package com.orderflow.ecommerce.services;

import com.orderflow.ecommerce.dtos.UserRequestDTO;
import com.orderflow.ecommerce.entities.User;
import com.orderflow.ecommerce.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserRequestDTO create(UserRequestDTO dto) {
        User user = new User();
        user.setName(dto.name());
        user.setEmail(dto.email());
        user.setPassword(dto.password());

        user = userRepository.save(user);
        // Retornamos com o ID que o banco gerou
        return new UserRequestDTO(user.getId(), user.getName(), user.getEmail(), null);
    }

    public List<UserRequestDTO> findAll() {
        return userRepository.findAll().stream().map(user ->
                new UserRequestDTO(user.getId(), user.getName(), user.getEmail(), null)
        ).collect(Collectors.toList());
    }

    public UserRequestDTO findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        return new UserRequestDTO(user.getId(), user.getName(), user.getEmail(), null);
    }

    public UserRequestDTO update(Long id, UserRequestDTO dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        user.setName(dto.name());
        user.setEmail(dto.email());
        // Se quiser atualizar a senha também, descomente a linha abaixo:
        // user.setPassword(dto.password());

        userRepository.save(user);
        return new UserRequestDTO(user.getId(), user.getName(), user.getEmail(), null);
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}