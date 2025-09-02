package com.example.springrest.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity // JPA
@Getter
@NoArgsConstructor // @Builder -> 기본 빈 생성자
@Table(name = "users") // 예약어 회피
public class User {
    @Id // JPA
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // bigint
    @Column(nullable = false, unique = true) // NOT NULL, UNIQUE
    private String username;
    @Column(nullable = false)
    private String email;
    // username(ID) -> 동일한 ID 재가입은 안 됨 -> 탈퇴하고 새로운 ID로 가입하면서 Soft Delete라고 해서 그 전에 회원 정보 (DB 정보)를 가지고 있는 경우가 있음 이런 경우 그동안 email을 못 써 -> 그런 이슈를 방지
    @Builder
    public User(String username, String email) {
        this.username = username;
        this.email = email;
        // 빌더로 하나씩 지정해서 build()
    }
    // 데이터 수정을 위해
    public void update(String username, String email) {
        this.username = username;
        this.email = email;
        // 변경사항을 인지시키기 위한.. -> JPA에서
        // 데이터를 변경하는 방법이
        // 1) save -> upsert가 있으면 update, 없으면 insert
        // 2) save는 신규 데이터 생성에만, 기존데이터는 update 메서드 사용
    }

}
