package com.global.tareas.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "Phone")
@EntityListeners(AuditingEntityListener.class)

public class Phone {
    @Id
    @Column(name = "id")
    private String id;
    
    
    @OneToOne(mappedBy = "phone")
    private Usuario user;
    
    @Column(name = "number", nullable = false)
	private long number;
    @Column(name = "citycode", nullable = true)
	private int cityCode;
    @Column(name = "countrycode", nullable = true)    
	private String countryCode;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Usuario getUser() {
		return user;
	}
	public void setUser(Usuario user) {
		this.user = user;
	}
	public long getNumber() {
		return number;
	}
	public void setNumber(long number) {
		this.number = number;
	}
	public int getCityCode() {
		return cityCode;
	}
	public void setCityCode(int cityCode) {
		this.cityCode = cityCode;
	}
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
    
    
    

	
	
}
