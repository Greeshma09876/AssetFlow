
package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

	// Password encryption
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// Application security configuration
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http.authorizeHttpRequests(auth -> auth

				// Public pages and static resources
				.requestMatchers("/login", "/css/**", "/js/**", "/access-denied", "/images/**", "/webjars/**", "/error")
				.permitAll()

				// User management: Admin only
				.requestMatchers("/users/**", "/roles/**").hasRole("ADMIN")

				// EMPLOYEES - Admin only for modifications
				.requestMatchers(HttpMethod.POST, "/employees/**").hasRole("ADMIN")

				.requestMatchers("/employees/add", "/employees/edit/**").hasRole("ADMIN")

				// ASSETS - Admin only for modifications
				.requestMatchers(HttpMethod.POST, "/assets/**").hasRole("ADMIN")

				.requestMatchers("/assets/add", "/assets/edit/**").hasRole("ADMIN")

				// CATEGORIES - Admin only for modifications
				.requestMatchers(HttpMethod.POST, "/categories/**").hasRole("ADMIN")

				.requestMatchers("/categories/add", "/categories/edit/**").hasRole("ADMIN")

				// ASSIGNMENTS - Role-Based Authorization

				// Delete: Admin only
				.requestMatchers(HttpMethod.POST, "/assignments/delete/**").hasRole("ADMIN")

				// View and forms: Admin and Staff
				.requestMatchers(HttpMethod.GET, "/assignments", "/assignments/add", "/assignments/edit/**")
				.hasAnyRole("ADMIN", "STAFF")

				// Save and update: Admin and Staff
				.requestMatchers(HttpMethod.POST, "/assignments/save", "/assignments/update/**")
				.hasAnyRole("ADMIN", "STAFF")

				// Other assignment URLs: Admin only
				.requestMatchers("/assignments/**").hasRole("ADMIN")

				// RETURNS - Role-Based Authorization

				// Delete: Admin only
				.requestMatchers(HttpMethod.POST, "/returns/delete/**").hasRole("ADMIN")

				// View and forms: Admin and Staff
				.requestMatchers(HttpMethod.GET, "/returns", "/returns/add", "/returns/edit/**")
				.hasAnyRole("ADMIN", "STAFF")

				// Save and update: Admin and Staff
				.requestMatchers(HttpMethod.POST, "/returns/save", "/returns/update/**").hasAnyRole("ADMIN", "STAFF")

				// Other return URLs: Admin only
				.requestMatchers("/returns/**").hasRole("ADMIN")

				// MAINTENANCE - Role-Based Authorization

				// Delete: Admin only
				.requestMatchers(HttpMethod.POST, "/maintenance/delete/**").hasRole("ADMIN")

				// View and forms: Admin and Staff
				.requestMatchers(HttpMethod.GET, "/maintenance", "/maintenance/add", "/maintenance/edit/**")
				.hasAnyRole("ADMIN", "STAFF")

				// Save and update: Admin and Staff
				.requestMatchers(HttpMethod.POST, "/maintenance/save", "/maintenance/update/**")
				.hasAnyRole("ADMIN", "STAFF")

				// Other maintenance URLs: Admin only
				.requestMatchers("/maintenance/**").hasRole("ADMIN")

				// STATUS HISTORY - Role-Based Authorization

				// Admin only: Add and edit forms
				.requestMatchers(HttpMethod.GET, "/status-history/add", "/status-history/edit/**").hasRole("ADMIN")

				// Admin only: Save and update
				.requestMatchers(HttpMethod.POST, "/status-history/save", "/status-history/update/**").hasRole("ADMIN")

				// Admin and Staff: View history
				.requestMatchers(HttpMethod.GET, "/status-history").hasAnyRole("ADMIN", "STAFF")

				// Other status history URLs: Admin only
				.requestMatchers("/status-history/**").hasRole("ADMIN")

				// Dashboard and read-only pages
				.requestMatchers(HttpMethod.GET, "/dashboard", "/employees", "/assets", "/categories")
				.hasAnyRole("ADMIN", "STAFF")

				// Everything else requires authentication
				.anyRequest().authenticated())

				// Login configuration
				.formLogin(form -> form.loginPage("/login").loginProcessingUrl("/login").usernameParameter("username")
						.passwordParameter("password").defaultSuccessUrl("/dashboard", true)
						.failureUrl("/login?error=true").permitAll())

				// Logout configuration
				.logout(logout -> logout.logoutUrl("/logout").logoutSuccessUrl("/login?logout=true")
						.invalidateHttpSession(true).deleteCookies("JSESSIONID").permitAll())

				// Access denied page
				.exceptionHandling(
						exception -> exception.accessDeniedHandler((request, response, accessDeniedException) -> {
							response.sendRedirect(request.getContextPath() + "/access-denied");
						}));

		return http.build();
	}
}
