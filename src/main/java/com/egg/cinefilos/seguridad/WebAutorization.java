package com.egg.cinefilos.seguridad;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpMethod;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Bean;
import org.springframework.security.web.WebAttributes;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;


@Configuration
@EnableWebSecurity
public class WebAutorization extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //Required authorization to access different endpoints
        http.authorizeRequests()
                .antMatchers("/pelicula/todas").hasAuthority("ADMIN");
                /*
                .antMatchers("/web/index.html", "/web/styles/**", "/web/scripts/**", "/web/assets/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/clients").permitAll()
                .antMatchers(HttpMethod.GET, "/api/clients/confirm").permitAll()
                .antMatchers("/h2-console/**", "/rest/**", "/api/manager/**", "/api/accounts", "/api/clients").hasAuthority("ADMIN")
                .antMatchers("/api/clients/current").authenticated()
                .antMatchers(HttpMethod.GET, "/api/**").hasAuthority("CLIENT")
                .antMatchers("/api/**", "/web/**").authenticated()
                .anyRequest().denyAll();
                 */

        //Login main configuration and response handler in case of failure
        http.formLogin()
                .usernameParameter("username")
                .passwordParameter("contrasenia")
                .loginPage("/api/login");


        //Clearing cookies after logOut
        http.logout().
                logoutUrl("/api/logout")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .clearAuthentication(true);

        http.csrf().
                disable();

        http.cors();

        http.headers().
                frameOptions().disable();

        //Redirecting to index.html in case of access attempt without the required authorization
        /*
        http.exceptionHandling().
                authenticationEntryPoint((req, res, exc) -> {
                    if (req.getRequestURI().contains("/web")) {
                        res.sendRedirect("/web/index.html");
                    }
                });

         */

        http.formLogin().
                successHandler((req, res, auth) -> clearAuthenticationAttributes(req));

        http.logout().
                logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler());
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

    private void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
            session.setMaxInactiveInterval(600);
        }
    }


}