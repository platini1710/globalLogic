package com.global.tareas.model;


import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;



@Entity
@Table(name = "Usuario")
@EntityListeners(AuditingEntityListener.class)

public class Usuario {

    @Id
    private String id;



    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "fcreated", nullable = false)
    private String created;
    @Column(name = "lastlogin", nullable = false)
    private String lastLogin;
    @Column(name = "token", nullable = false ,length = 300)
    private String token;
    @Column(name = "isActive",columnDefinition = "boolean  default true", nullable = false )
    private boolean  isActive;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinTable(name = "user_phone", 
      joinColumns = 
        { @JoinColumn(name = "user_id", referencedColumnName = "id") },
      inverseJoinColumns = 
        { @JoinColumn(name = "phone_id", referencedColumnName = "id") })
    private Phone phone;
    
    
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCreated() {
		return created;
	}
	public void setCreated(String created) {
		this.created = created;
	}
	public String getLastLogin() {
		return lastLogin;
	}
	public void setLastLogin(String lastLogin) {
		this.lastLogin = lastLogin;
	}
	public Phone getPhone() {
		return phone;
	}
	public void setPhone(Phone phone) {
		this.phone = phone;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

    
    
}