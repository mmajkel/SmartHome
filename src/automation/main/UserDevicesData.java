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
@Table(name="usr_devices")
public class UserDevicesData implements Serializable {
	
	@Id @Column(name="usr_username")
	private String username;
	@Id @Column(name="usr_device_id")
	private String deviceId;
	@Column(name="usr_device_group")
	private String deviceGroup;
	@Column(name="usr_device_name")
	private String deviceName;
	
	public UserDevicesData() {
		
	}	
	
	public String getUsername(){
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getDeviceGroup() {
		return deviceGroup;
	}
	public void setDeviceGroup(String deviceGroup) {
		this.deviceGroup = deviceGroup;
	}
	public String getDeviceName() {
		if (deviceName == null) {
			return " ";
		}else {
			return deviceName;
		}
	}
	public void setDeviceName(String deviceGroup) {
		this.deviceName = deviceName;
	}	
	
}
