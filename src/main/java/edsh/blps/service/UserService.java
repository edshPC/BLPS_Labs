package edsh.blps.service;

import edsh.blps.Repository.UserRepository;
import edsh.blps.entity.User;
import edsh.blps.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User findByUsername(String username) {
        return userRepository.findById(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    public void save(User user) {
        userRepository.save(user);
    }

    public User login(UserDTO request) {
        User user = findByUsername(request.getUsername());
        if(passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return user;
        }
        throw new IllegalArgumentException("Wrong password");
    }

    public User register(UserDTO request) {
        if (userRepository.existsById(request.getUsername())) {
            throw new IllegalArgumentException("User already exists");
        }
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .telephone(request.getTelephone())
                .build();
        save(user);
        return user;
    }

}
