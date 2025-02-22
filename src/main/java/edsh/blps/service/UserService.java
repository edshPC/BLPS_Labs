package edsh.blps.service;

import edsh.blps.Repository.User_Repository;
import edsh.blps.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final User_Repository user_repository;
    public void save(User user) {
        user_repository.save(user);
    }
}

