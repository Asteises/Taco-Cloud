package ru.asteises.tacocloud.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.asteises.tacocloud.model.User;
import ru.asteises.tacocloud.repository.UserRepository;

@Configuration
public class SecurityConfig {

    //TODO Не понимаю смысл этого бина. Сам класс PasswordEncoder используется и в других классах.
    // А тут получается мы его настраиваем?
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Создаем список и добавляем в него объекты User,
     * каждый из которых имеет свои свойства для хранения имени пользователя, пароля и списка привилегий;
     * На основе этого списка создаем InMemoryUserDetailsManager;
     */
//    @Bean
//    public UserDetailsService userDetailsServices(PasswordEncoder encoder) {
//        List<UserDetails> userDetails = new ArrayList<>();
//        userDetails.add(new User(
//                "buzz", encoder.encode("password"),
//                Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"))
//        ));
//        userDetails.add(new User(
//                "woody", encoder.encode("password"),
//                Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"))
//        ));
//        return new InMemoryUserDetailsManager(userDetails);
//    }

    /**
     * Для создания бин-компонента возвращаем лямбду,
     * которая принимает username и использует его для вызова метода findByUsername() в репозитории;
     */
    @Bean
    public UserDetailService userDetailsServices(UserRepository userRepo) {
        return username -> {
            User user = userRepo.findByUsername(username);
            if (user != null) return user;
            throw new UsernameNotFoundException("User '" + username + "' not found");
        };
    }

    /**
     * Тут мы задали два правила безопасности:
     * - запросы "/design", "/orders" отправленные пользователями должны иметь подтвержденную роль "USER";
     * - все остальные запросы обрабатываются в любом случае;
     *
     * Метод and() означает, что мы закончили настройку авторизации и готовы применить дополнительные настройки.
     * and() используется всякий раз когда начинается новый раздел конфигурации;
     */
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeRequests()
                .antMatchers("/design", "/orders")
                .hasRole("USER")
                .antMatchers("/", "/**")
                .permitAll()
                .and()
                .build();
    }

    /**
     * Переписали предыдущий метод с методом access(), у которого больше возможностей;
     */
    @Bean
    public SecurityFilterChain filterChainAccess(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeRequests()
                .antMatchers("/design", "/orders")
                .access("hasRole('USER')")
                .antMatchers("/", "/**")
                .access("permitAll()")
                .and()
                .build();
    }

    /**
     * Переписали предыдущий метод с методом access(), у которого больше возможностей;
     */
    @Bean
    public SecurityFilterChain filterChainTuesday(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeRequests()
                .antMatchers("/design", "/orders")
                .access("hasRole('USER') && " +
                        "T(java.util.Calendar).getInstance().get("+
                        "T(java.util.Calendar).DAY_OF_WEEK) == " +
                        "T(java.util.Calendar).TUESDAY")
                .antMatchers("/", "/**")
                .access("permitAll")
                .and()
                .build();
    }

    /**
     * Создание страницы входа;
     * formLogin() - начинает настройку формы входа;
     * loginPage() - определяет путь к странице,
     * если пользователь не пройдет аутентификацию, он будет направлен на эту страницу;
     */
    @Bean
    public SecurityFilterChain filterChainHomePage(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeRequests()
                .antMatchers("/design", "/orders")
                .access("hasRole('USER')")
                .antMatchers("/", "/**")
                .access("permitAll()")
                .and()
                .formLogin()
                .loginPage("/login")
                .and()
                .build();
    }
    }
