package kr.co.ezenac.membership;

import java.sql.Date;

/*
 * 	ID				VARCHAR2(10)			NOT NULL ,
	PASS 			VARCHAR2(10)			NOT NULL ,
	NAME 			VARCHAR2(30) 			NOT NULL 
 */

public class MemberDTO {
	private String id;
	private String pass;
	private String name;
	
	public MemberDTO() {
		super();
	}
	
	public MemberDTO(String id, String pass, String name) {
		this.id = id;
		this.pass = pass;
		this.name = name;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
