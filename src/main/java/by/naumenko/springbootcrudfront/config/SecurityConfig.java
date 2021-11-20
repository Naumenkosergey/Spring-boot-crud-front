package by.naumenko.springbootcrudfront.config;

import by.naumenko.springbootcrudfront.servise.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;
    private final LoginSuccessHandler loginSuccessHandler;
    private final UserService userService;


    //@formatter:off
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
                authorizeRequests()
                    .antMatchers("/signin").anonymous()
                    .antMatchers("/main").hasAnyRole("ADMIN", "USER")
                    .antMatchers("/home/**").permitAll()
                    .anyRequest().authenticated();
        http.
                formLogin()
                    .loginPage("/signin")
                    .successHandler(loginSuccessHandler)
                    .usernameParameter("email")
                    .passwordParameter("password");

        http.   logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .clearAuthentication(true)
                    .deleteCookies("JSESSIONID")
                    .logoutSuccessUrl("/signin?logout");
    }
    //@formatter:on


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
    }
}
