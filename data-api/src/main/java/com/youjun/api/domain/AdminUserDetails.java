package com.youjun.api.domain;

import com.youjun.api.modules.ums.model.UmsAdmin;
import com.youjun.api.modules.ums.model.UmsResource;
import com.youjun.common.api.ResultCode;
import com.youjun.common.exception.Asserts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * SpringSecurity需要的用户详情
 *
 * @author kirk
 * @date 2021/5/01
 */
@Slf4j
public class AdminUserDetails implements UserDetails {
    private UmsAdmin umsAdmin;
    private List<UmsResource> resourceList;
    public AdminUserDetails(UmsAdmin umsAdmin, List<UmsResource> resourceList) {
        this.umsAdmin = umsAdmin;
        this.resourceList = resourceList;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //返回当前用户的角色
        return resourceList.stream()
                .map(role ->new SimpleGrantedAuthority(role.getId()+":"+role.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return umsAdmin.getPassword();
    }

    public String getUserId() {
        return umsAdmin.getId().toString();
    }

    @Override
    public String getUsername() {
        return umsAdmin.getUsername();
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
        return umsAdmin.getStatus().equals(1);
    }

    /**
     * 获取当前登录用户
     * @return
     */
    public static AdminUserDetails getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(null==principal){
            log.error("Access current user failed");
            //暂未登录或token已经过期  throw new RuntimeException();
            Asserts.fail(ResultCode.UNAUTHORIZED);
        }
        return (AdminUserDetails)principal;
       /* UmsAdmin umsAdmin = new UmsAdmin();
        umsAdmin.setId("1");
        umsAdmin.setUsername("test");
        return new AdminUserDetails(umsAdmin, Collections.EMPTY_LIST);*/
    }
}
