package automation.main;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name="adm_usr_sess")
public class UserSession implements Serializable {
	
	@Id @Column(name="adm_username")
	private String username;
	@Id @Column(name="adm_session_id")
	private String sessionId;
	@Column(name="adm_session_max_time")
	private String sessionMaxTime;
	
	public UserSession() {
		System.out.println("konstruktor domyślny - UserSession");
	}
	
	public UserSession(String username, String sessionId) {
		this.username = username;
		this.sessionId = sessionId;
		sessionMaxTime = "9999-12-31";
	}
	
	public UserSession(String username, String sessionId, String sessionMaxTime) {
		this.username = username;
		this.sessionId = sessionId;
		this.sessionMaxTime = sessionMaxTime;		
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getSessionMaxTime() {
		return sessionMaxTime;
	}
	public void setSessionMaxTime(String sessionMaxTime) {
		this.sessionMaxTime = sessionMaxTime;
	}	
}
