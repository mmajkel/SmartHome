package automation.devices.lightSwitch;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

public class LightSwitch {
	
	private String id;
	private Integer actualStatus = 0;
	private boolean changeOutside = false;
	private boolean statusChanged = false;
	//private Integer tempI=0;
	
	public LightSwitch() {
		
	}
	
	public LightSwitch(String id){
		this.id = id;
	}
	public String getId() {
		return id;
	}
	private void setActualStatus(Integer actualStatus) {
		this.actualStatus = actualStatus;
	}
	public void setStatus(Integer status, String client, boolean modeBrowser) {

			if (!status.equals(actualStatus) && modeBrowser) {
				changeOutside = true;
				setActualStatus(status);
				statusChangedAprooved(true);
			}
			if   (!status.equals(actualStatus) 
			    && client.equals(id)
			    && !modeBrowser) {		
				
				if (changeOutside) {
					changeOutside = false;
				}
				else {
					setActualStatus(status);
					statusChangedAprooved(true);
				}
			}
/*			System.out.println("-------"+new Date());
			System.out.println("obiekt "+getId());
			System.out.println("ustawiono status="+getActualStatus());
			System.out.println("-------------------------------------");*/
		}

	public Integer getActualStatus() {
	/*		System.out.println(new Date());
		System.out.println(tempI+" pytanie o status dla "+getId()+" status = "+actualStatus);
*/		return actualStatus;
	}
	public boolean isStatusChanged() {
		
		return statusChanged;
	}
	public void statusChangedAprooved(boolean statusChanged) {
		this.statusChanged = statusChanged;
	}

	@Override
	public String toString() {
		return "LightSwitch [id=" + id + ", actualStatus=" + actualStatus + ", changeOutside=" + changeOutside
				+ ", data=" + new Date() + ", statusChanged=" + statusChanged + "]";
	}
	
}