package com.dhflour.gombooksvr.beans;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

// Lombok 어노테이션 - 필수 필드(final 필드)에 대한 생성자 자동 생성
@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {

    private final UserInfo userInfo;
    private final UserRoleEnum ue;

    // ue.getAllCodes()를 호출하여 모든 권한 코드를 리스트로 가져옴
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> returnCol = new ArrayList<SimpleGrantedAuthority>();
        for(String code : ue.getAllCodes()){    //모든 권한 코드 조회
            returnCol.add( new SimpleGrantedAuthority(code));
        }

        return returnCol;
    }

    @Override
    public String getPassword() {
        return userInfo.getPassword();
    }

    @Override
    public String getUsername() {
        return userInfo.getUserId().toString();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
