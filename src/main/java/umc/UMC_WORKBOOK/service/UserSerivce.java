package umc.UMC_WORKBOOK.service;

import umc.UMC_WORKBOOK.domain.User;
import umc.UMC_WORKBOOK.repository.MemoryUserRepository;
import umc.UMC_WORKBOOK.repository.UserRepository;

import java.util.List;
import java.util.Optional;

public class UserSerivce {
    private final UserRepository userRepository;

    public UserSerivce(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String join(User user){
        vaildateDuplicateUser(user);
        userRepository.save(user);
        return user.getId();
    }

    public List<User> findUsers(){
        return  userRepository.findAll();
    }

    public Optional<User> findOne(String id){
        return userRepository.findById(id);
    }

    private void vaildateDuplicateUser(User user) {
        userRepository.findById(user.getId()).ifPresent(m->{
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        });
    }
}
