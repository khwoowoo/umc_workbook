package umc.UMC_WORKBOOK.repository;

import umc.UMC_WORKBOOK.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    User save(User user);
    boolean remove(String id);
    boolean update(String id, User updateUser);
    Optional<User> findById(String id);
    List<User> findAll();
}
