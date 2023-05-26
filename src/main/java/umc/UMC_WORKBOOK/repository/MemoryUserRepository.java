package umc.UMC_WORKBOOK.repository;

import umc.UMC_WORKBOOK.domain.User;

import java.util.*;

public class MemoryUserRepository implements UserRepository{
    private static Map<String, User> store = new HashMap<>();

    @Override
    public User save(User user) {
        if(findById(user.getId()).equals(Optional.empty()))
            store.put(user.getId(), user);
        else
            System.out.println("아디지 저장 실패, 아이디 중복");
        return user;
    }

    @Override
    public boolean remove(String id) {
        if(findById(id).equals(Optional.empty()))
            return false;

        store.remove(id);
        return true;
    }

    @Override
    public boolean update(String id, User updateUser) {
        if(findById(id).equals(Optional.empty()))
            return false;

        store.get(id).setName(updateUser.getName());
        store.get(id).setPassword(updateUser.getPassword());
        store.get(id).setBirthDate(updateUser.getBirthDate());
        return true;
    }

    @Override
    public Optional<User> findById(String id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(store.values());
    }
}
