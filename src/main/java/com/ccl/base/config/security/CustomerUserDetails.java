package com.ccl.base.config.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import com.ccl.base.config.security.md5.SaltProperties;
import com.ccl.core.entity.Role;
import com.ccl.core.entity.User;

public class CustomerUserDetails extends User implements UserDetails {
	
	private static final long serialVersionUID = -7685395677742010725L;
	
	
	@SaltProperties
	private String salt;
	private Boolean isAccountNonLocked;
	private Boolean isEnabled;
	
	private Collection<? extends GrantedAuthority> authorities;
	
	
	public CustomerUserDetails(User user) {
		super(user);
		this.salt = user.getUsername();
		
		this.isAccountNonLocked = user.getIsAccountLocked() == 0 ? false : true;
		this.isEnabled = user.getIsEnabled() == 0 ? true : false;
		
		List<Role> roleList = user.getRoles();
		
		if (roleList == null || roleList.size() < 1) {
			authorities = AuthorityUtils.commaSeparatedStringToAuthorityList("");
		}
		StringBuilder commaBuilder = new StringBuilder();
		for (Role role : roleList) {
			commaBuilder.append(role.getKey()).append(",");
		}
		String aut = commaBuilder.length() > 0 ? commaBuilder.substring(0, commaBuilder.length() - 1) : "";
		authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(aut);
	}
	
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}
	
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	
	/*@Override
	public void setRoles(List<Role> roles) {
		if(CollectionUtils.isNotEmpty(roles)) {
			super.setRoles(roles);
			Collection<GrantedAuthority> attr = new ArrayList<>();
			for (Role role : roles) {
				attr.add(new SimpleGrantedAuthority(role.getKey()));
			}
		}
	}*/

	public String getSalt() {
		return salt;
	}


	@Override
	public boolean isAccountNonLocked() {
		return this.isAccountNonLocked;
	}

	@Override
	public boolean isEnabled() {
		return this.isEnabled;
	}


}
