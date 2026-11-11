package cn.wbnull.springbootdemo.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * UserService
 *
 * @author dukunbiao(null)  2024-03-12
 * https://github.com/dkbnull/SpringBootDemo
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
//        List<GrantedAuthority> roles = AuthorityUtils.commaSeparatedStringToAuthorityList("admin");
//
//        return new User("admin", new BCryptPasswordEncoder().encode("123456"), roles);

        List<GrantedAuthority> roles;
        if ("admin1".equals(s)) {
            roles = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_level1");
        } else if ("admin2".equals(s)) {
            roles = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_level2");
        } else if ("admin3".equals(s)) {
            roles = AuthorityUtils.commaSeparatedStringToAuthorityList("level3");
        } else if ("admin0".equals(s)) {
            roles = AuthorityUtils.createAuthorityList("ROLE_level1", "ROLE_level2", "level3");
        } else {
            throw new UsernameNotFoundException("用户名或密码错误");
        }

        return new User(s, new BCryptPasswordEncoder().encode("123456"), roles);
    }
}
