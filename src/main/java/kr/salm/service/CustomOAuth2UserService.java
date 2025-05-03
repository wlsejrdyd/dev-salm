package kr.salm.service;

import kr.salm.entity.User;
import kr.salm.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.*;
import org.springframework.security.oauth2.core.*;
import org.springframework.security.oauth2.core.user.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId(); // google, naver, kakao
        String userNameAttribute = userRequest.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        Map<String, Object> attributes = oAuth2User.getAttributes();
        Map<String, Object> userData;

        String username = null;
        String email = null;
        String name = null;

        switch (registrationId) {
            case "google":
                username = (String) attributes.get("sub");
                email = (String) attributes.get("email");
                name = (String) attributes.get("name");
                break;

            case "kakao":
                userData = (Map<String, Object>) attributes.get("kakao_account");
                Map<String, Object> profile = (Map<String, Object>) userData.get("profile");
                username = String.valueOf(attributes.get("id"));
                email = (String) userData.get("email");
                name = (String) profile.get("nickname");
                break;

            case "naver":
                userData = (Map<String, Object>) attributes.get("response");
                username = (String) userData.get("id");
                email = (String) userData.get("email");
                name = (String) userData.get("name");
                break;

            default:
                throw new OAuth2AuthenticationException(new OAuth2Error("invalid_provider"), "지원하지 않는 OAuth 서비스입니다.");
        }

        // 기존 사용자 있는지 확인
        Optional<User> existingUser = userRepository.findByUsername(username);
        if (existingUser.isEmpty()) {
            User user = new User();
            user.setUsername(username);
            user.setPassword("oauth"); // 더미 비번
            user.setEmail(email != null ? email : "");
            user.setName(name != null ? name : registrationId + " 사용자");
            user.setPhone("010-0000-0000");
            user.setGender(User.Gender.M); // 기본값
            user.setRole("USER");
            userRepository.save(user);
        }

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
                attributes,
                userNameAttribute
        );
    }
}
