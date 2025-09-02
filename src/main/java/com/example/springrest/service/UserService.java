package com.example.springrest.service;

import com.example.springrest.domain.User;
import com.example.springrest.dto.UserDto;
import com.example.springrest.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor // 의존성 주입
@Transactional(readOnly = true) // 읽기 조회 해서 성능 최적화를 하기 위함
public class UserService {
    private final UserRepository userRepository;

    // CRUD
    // Create : insert, add, [post]
    // Read   : select, [get]
    // Update : update, [patch, put]
    // Delete : [delete], remove

    @Transactional // 쓰기 조회 락을 건다!
    public UserDto.Response createUser(UserDto.CreateRequest dto) {
        User savedUser = userRepository.save(dto.toEntity()); // dto를 entity로 바꾸고 save로 레포에 저장한 걸
        // PK -> auto increment, audit(생성, 수정일..)
        return UserDto.Response.fromEntity(savedUser); // 다시 fromEntity로 response로 받음
    }

    // READ (ONE, ALL)
    // ONE -> id, pk
    public UserDto.Response findUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("사용자 찾을 수 없음 : " + id));
        return UserDto.Response.fromEntity(user);
    }
    // ALL -> 테이블 자체를 조회 (상황에 따라 정렬이 들어갈 수도  있다 -> pk 기준 asc)
    public List<UserDto.Response> findAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserDto.Response::fromEntity)
                .toList();
                // .collect(Collectors.toList());
    }
    // DTO랑 Entity랑 구분 안 해서 파파존스에서 DB를 걍 통째로 frontend로 보내버렸다고 함

    // UPDATE
    @Transactional
    public UserDto.Response updateUser(Long id, UserDto.UpdateRequest dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("사용자 찾을 수 없음 : " + id));
        // Dirty Checking
        user.update(
                dto.username(), dto.email()
        ); // 아예 해당 내용들을 모두 교체 // put 형태
        // DB에 반영
        return UserDto.Response.fromEntity(user);
    }

    // DELETE
    @Transactional
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("사용자 찾을 수 없음 : " + id);
        }
        userRepository.deleteById(id);
    }
}
