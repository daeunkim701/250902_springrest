package com.example.springrest.dto;

import com.example.springrest.domain.User;

public class UserDto {
    // Nested Class, Record
    public record CreateRequest(String username, String email) {
        // Entity는 ID가 있어 DB에서 ID 알아서 Generate해줌 -> DTO
        public User toEntity() { // DTO -> Entity 변환
            return User.builder()
                    .username(username)
                    .email(email)
                    .build();
        }
        // Controller, Service 변환 로직 -> Record에 내장
    }

    // 주소(PathVariable -> PK(ID))
    public record UpdateRequest(String username, String email) {}

    // 사용자 응답 (Response)
    public record Response(Long id, String username, String email)
        // create의 경우엔 -> createRequest로 받고 받은 상태에서 객체가 돼서, Entity로 본인 객체로 전환
        {
            // User Entity를 Response로 변환 (Response 객체라는 건 없음 -> static)
            public static Response fromEntity(User user) {
                return new Response(user.getId(), user.getUsername(), user.getEmail());
            }
    }
}
