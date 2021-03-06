package com.secure.Security1.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.secure.Security1.forgotpass.UserNotFoundException;
import com.secure.Security1.model.Role;
import com.secure.Security1.model.User;
import com.secure.Security1.respositories.UserRepository;

public class MyUserDetails implements UserDetails {

	private int id;
	private String username;
	
	@JsonIgnore
	private String password;
	private String email;
	private Collection<? extends GrantedAuthority> authorities;

	public MyUserDetails(Integer id, String username, String password,String email,
			Collection<? extends GrantedAuthority> authorities) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
		this.authorities = authorities;
	}
	

	public static MyUserDetails build(User user) {
		List<GrantedAuthority> authorities = user.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getName().name()))
				.collect(Collectors.toList());

		return new MyUserDetails(
				user.getId(), 
				user.getUsername(), 
				user.getPassword(), 
				user.getEmail(),
				authorities);
	}

		@Override
		public Collection<? extends GrantedAuthority> getAuthorities() {
			return authorities;
		}

		public int getId() {
			return id;
		}

		@Override
		public String getPassword() {
			return password;
		}

		@Override
		public String getUsername() {
			return username;
		}
		
		public String getEmail() {
			return email;
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

		@Override
		public boolean equals(Object o) {
			if (this == o)
				return true;
			if (o == null || getClass() != o.getClass())
				return false;
			MyUserDetails user = (MyUserDetails) o;
			return Objects.equals(id, user.id);
		}

		
	}
