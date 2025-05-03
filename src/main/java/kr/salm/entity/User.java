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
    @Size(min = 4, max = 50, message = "아이디는 4자 이상 50자 이하로 입력하세요.")
    private String username;

    @Column(nullable = true)
    @ValidPassword
    private String password;

    @Column(nullable = true)
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    @Column(nullable = true)
    private String name;

    @Column(nullable = true)
    private String birthDate; // yyyy-MM-dd

    @Column(nullable = true)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(nullable = true)
    private String phone;

    @Column(nullable = false)
    private String role = "USER";

    @Column(name = "created_at", updatable = false)
    private java.time.LocalDateTime createdAt = java.time.LocalDateTime.now();

    public enum Gender {
        M, F
    }
}
