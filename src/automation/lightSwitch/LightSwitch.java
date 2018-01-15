package automation.lightSwitch;

public class LightSwitch {
	
	private String id;
	private Integer actualStatus = 0;
	boolean changeOutside = false;
	public LightSwitch(String id){
		this.id = id;
	}
	public String getId() {
		return id;
	}
	private void setActualStatus(Integer actualStatus) {
		this.actualStatus = actualStatus;
	}

	public void setStatus(Integer status, String client) {

			if (!status.equals(actualStatus) && client.equals("p"+id)) {
				changeOutside = true;
				setActualStatus(status);
			}
			if (!status.equals(actualStatus) && client.equals(id)) {		
				if (changeOutside) {
					changeOutside = false;
				}
				else {
					setActualStatus(status);
				}
			}
		}

	public Integer getActualStatus() {
		return actualStatus;
	}
}
