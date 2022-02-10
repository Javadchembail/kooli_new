package com.kooli.app.kooli.securities;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kooli.app.kooli.models.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;


@Getter


public class UserPrincipal implements UserDetails {

	private static final long serialVersionUID = 4902399377008590826L;

	private Long id;

	private String username;

	private String type;

	@JsonIgnore
	private String password;

	private String name;

	private String companyname;

	private String email;

	private String gender;

	private LocalDate dob;

	private String address;

	private String city;

	private String state;

	private String country;

	private String pin;

	private String image;

	private String status;
	
	private String category;
	
	private String subCategory;

	@JsonIgnore
	private boolean enabled;

	@JsonIgnore
	private boolean locked;

	private Collection<? extends GrantedAuthority> authorities;

	public UserPrincipal(User user, Collection<? extends GrantedAuthority> authorities) {
		this.id = user.getId();
		this.username = user.getUsername();
		this.password = user.getPassword();
		this.image = user.getImage();
		this.name = user.getFirstname() + " " + user.getLastname();
		this.companyname = user.getCompanyname();
		this.email = user.getEmail();
		this.type = user.getType();
		this.category= user.getCategory().getName();
		this.subCategory= user.getSubCategory().getName();
		this.enabled = user.isEnabled();
		this.authorities = authorities;
	}

	public static UserPrincipal create(User user) {
		List<GrantedAuthority> authorities = user.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getName().name())).collect(Collectors.toList());

		return new UserPrincipal(user, authorities);
	}

	public Long getId() {
		return id;  
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return !locked; 
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		UserPrincipal that = (UserPrincipal) o;
		return Objects.equals(id, that.id);
	}

	@Override
	public int hashCode() {

		return Objects.hash(id);
	}
}