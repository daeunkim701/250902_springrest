package com.example.springrest.controller;

import com.example.springrest.dto.UserDto;
import com.example.springrest.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
// @Controller
@RequiredArgsConstructor
@RequestMapping("/api/users") // 공통경로이자 prefix
// localhost:8080/api/users/... => 이렇게 만들겠다는 뜻
public class UserApiController {
    private final UserService userService;
    // @Get... @Post...
    // public String index() { return "index"; }
    // public String index(Model model) { return "index"; }
    // public String index(RedirectAttributes...) { return "redirect..."; }
    // Session? Cookie? -> Response
    // @ResponseBody // ViewResolver로 가는 게 아니라 Return 데이터 자체를 응답값으로 제공해라
    // -> 이건 @RestController에는 필요 없음 -> 혼용할 때는 필요할 수도 있음
    @GetMapping // ("/")가 생략되어있음 // GET은 그냥 localhost:8080/api/users로 가면 됨
    // public List<UserDto.Response> getAllUsers() {
    public ResponseEntity<List<UserDto.Response>> getAllUsers() {
        // 이렇게 ResponseEntity로 감싸서 전달하는 게 요즘의 추세래
        // DB 원래 데이터 -> Entity(JPA Repo) -> Service, Controller (DTO) -> Restful API (JSON) / Thymeleaf (Model, Object)
        // return userService.findAllUsers();
        return ResponseEntity.ok(userService.findAllUsers());
    }

    // 사용자 생성
    // POST localhost:8080/api/users
    @PostMapping // 별도로 연결 안 한다는 건 걍 ("/") 이거 라는 거
    public ResponseEntity<UserDto.Response> createUser(@RequestBody UserDto.CreateRequest dto) {
        // JSON Body에서 했을 땐 Form 하나하나 input 했다면 Json에서는 {키/프로퍼티이름:값}으로 함
        UserDto.Response response = userService.createUser(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 특정 사용자 조회 : 숫자로 된 개별 id/pk
    @GetMapping("/{id}")
    public ResponseEntity<UserDto.Response> getUserById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findUserById(id));
    }
}
