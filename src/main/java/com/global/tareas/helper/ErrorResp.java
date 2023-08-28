package com.global.tareas.helper;

import java.sql.Timestamp;

public class ErrorResp {
private int codigo;
private String detail;
private Timestamp timestamp ;


public int getCodigo() {
	return codigo;
}
public void setCodigo(int codigo) {
	this.codigo = codigo;
}
public String getDetail() {
	return detail;
}
public void setDetail(String detail) {
	this.detail = detail;
}
public Timestamp getTimestamp() {
	return timestamp;
}
public void setTimestamp(Timestamp timestamp) {
	this.timestamp = timestamp;
}


}
