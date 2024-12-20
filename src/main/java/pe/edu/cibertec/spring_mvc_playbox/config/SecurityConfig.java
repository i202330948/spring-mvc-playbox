package pe.edu.cibertec.spring_mvc_playbox.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                // definir rutas protegidas y quien puede acceder a ellas
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/maintenance/login").permitAll() // establecer rutas publicas
                        .requestMatchers("/maintenance/register").permitAll() // establecer rutas publicas
                        .requestMatchers("/maintenance/register-confirm").permitAll() // establecer rutas publicas

                        .requestMatchers("/maintenance-game/**").permitAll() // establecer rutas publicas

                        .requestMatchers("/maintenance-order/**").permitAll() // establecer rutas publicas

                        .requestMatchers("/maintenance/start").hasAnyRole("ADMIN", "USER") // acceso para ADMIN y USER
                        .requestMatchers("/maintenance/detail/{id}").hasAnyRole("ADMIN", "USER") // acceso para ADMIN y USER
                        .requestMatchers("/maintenance/edit/{id}").hasAnyRole("ADMIN") // acceso para ADMIN
                        .requestMatchers("/maintenance/edit-confirm").hasAnyRole("ADMIN") // acceso para ADMIN
                        .requestMatchers("/maintenance/delete/{id}").hasAnyRole("ADMIN") // acceso para ADMIN
                        .requestMatchers("/maintenance/create").hasAnyRole("ADMIN") // acceso para ADMIN
                        .requestMatchers("/maintenance/create-confirm").hasAnyRole("ADMIN") // acceso para ADMIN
                        .requestMatchers("/maintenance/buy/{id}").hasAnyRole("USER") // acceso para USER
                        .anyRequest().authenticated() // el resto debe autenticarse
                )

                // redireccionar a una pagina de error en caso no tenga permisos
                .exceptionHandling(ex -> ex
                        .accessDeniedHandler((req, rsp, e) -> {
                            rsp.sendRedirect("/maintenance/restricted");
                        })
                )

                // configurar el formulario de inicio de sesion
                .formLogin(form -> form
                        .loginPage("/maintenance/login")
                        .defaultSuccessUrl("/maintenance/start", false)
                        .permitAll()
                )

                // configurar el formulario de cerrar sesion
                .logout(logout -> logout
                        .logoutUrl("/maintenance/logout")
                        .logoutSuccessUrl("/maintenance/login?logout")
                        .permitAll()
                )

                // Desactivar CSRF temporalmente para rutas específicas (como Postman)
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/maintenance-order/**", "/maintenance-game/**")
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
