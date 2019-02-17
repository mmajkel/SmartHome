package automation.admin;

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
@Table(name="adm_usr")
public class Users {

	@Id
	@Column(name="usr_username")
	private String username;
	@Column(name="usr_password")
	private String password;
	@Column(name="usr_user_type")
	private char userType;
	@Column(name="usr_email")
	private String userEmail;
	@Column(name="usr_confirmation_status")
	private char userConfirmationStatus;
	@Column(name="usr_temp_confirmation_link")
	private String userTempConfirmationLink;
	
	public Users() {
		
	}
	public Users(String username
				,String password
				,char userType
				,String userEmail
				,char userConfirmationStatus
				,String userTempConfirmationLink
				) {
		this.username = username;
		this.password = password;
		this.userType = userType;
		this.userEmail = userEmail;
		this.userConfirmationStatus = userConfirmationStatus;
		this.userTempConfirmationLink = userTempConfirmationLink;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public char getUserType() {
		return userType;
	}

	public void setUserType(char userType) {
		this.userType = userType;
	}

	public String getEmail() {
		return userEmail;
	}

	public void setEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public char getUserConfirmationStatus() {
		return userConfirmationStatus;
	}

	public void setUserConfirmationStatus(char userConfirmationStatus) {
		this.userConfirmationStatus = userConfirmationStatus;
	}	
	public String getUserConfirmationLink() {
		return userTempConfirmationLink;
	}

	public void setUserConfirmationLink(String userTempConfirmationLink) {
		this.userTempConfirmationLink = userTempConfirmationLink;
	}	
	
	
}
