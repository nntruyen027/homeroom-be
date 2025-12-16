package com.vinhthanh2.lophocdientu.service;

import com.vinhthanh2.lophocdientu.dto.sql.UserAuthPro;
import com.vinhthanh2.lophocdientu.repository.PermissionRepo;
import com.vinhthanh2.lophocdientu.repository.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepo userRepository;
    private final PermissionRepo permissionRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // Lấy user từ DB
        Optional<UserAuthPro> optionalUser = userRepository.findAuthByUsername(username);
        UserAuthPro user = optionalUser.orElseThrow(
                () -> new UsernameNotFoundException("Không tìm thấy người dùng: " + username)
        );

        // Lấy permission của user
        Set<String> permissions = permissionRepo.timPermissionTheoUserId(user.getId());

        // Chuyển permissions thành GrantedAuthority
        Set<GrantedAuthority> permissionAuthorities = permissions.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());

        // Chuyển roles thành GrantedAuthority dạng ROLE_*
        Set<GrantedAuthority> roleAuthorities = user.getRoles() == null ? Set.of() :
                Arrays.stream(user.getRoles())
                        .map(r -> r.startsWith("ROLE_") ? r : "ROLE_" + r)
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toSet());

        // Gộp role + permission và loại bỏ trùng lặp
        Set<GrantedAuthority> authorities = Stream.concat(roleAuthorities.stream(), permissionAuthorities.stream())
                .collect(Collectors.toSet());

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                authorities
        );
    }
}
