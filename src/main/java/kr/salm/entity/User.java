package kr.salm.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import kr.salm.validator.ValidPassword;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "아이디는 필수 입력 항목입니다.")
    @Size(min = 4, max = 20, message = "아이디는 4자 이상 20자 이하로 입력하세요.")
    private String username;

    @ValidPassword
    private String password;

    @NotBlank(message = "이메일은 필수 입력 항목입니다.")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    @NotBlank(message = "이름은 필수 입력 항목입니다.")
    private String name;

    private String birthDate; // yyyy-MM-dd 형식 문자열로 저장

    @NotNull(message = "성별을 선택하세요.")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @NotBlank(message = "휴대폰 번호는 필수 입력 항목입니다.")
    private String phone;

    @Column(nullable = false)
    private String role = "USER"; // 기본 권한

    @Column(name = "created_at", updatable = false)
    private java.time.LocalDateTime createdAt = java.time.LocalDateTime.now();

    public enum Gender {
        M, F
    }
}

