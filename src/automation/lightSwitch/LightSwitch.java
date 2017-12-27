package automation.lightSwitch;

public class LightSwitch {
	
	private String id;
	private Integer statusBiezacy = 0;
	boolean ustawionoPozaEsp = false;
	public LightSwitch(String id){
		this.id = id;
	}
	public String getId() {
		return id;
	}
	public void setStatusBiezacy(Integer statusBiezacy) {
		this.statusBiezacy = statusBiezacy;
	}
	public void setStatus(Integer status, String client) {

		if (!status.equals(statusBiezacy) && client.equals("p"+id)) {
			ustawionoPozaEsp = true;
			setStatusBiezacy(status);
		}
		if (!status.equals(statusBiezacy) && client.equals(id)) {		
			if (ustawionoPozaEsp) {
				ustawionoPozaEsp = false;
			}
			else {
				setStatusBiezacy(status);
			}
		}
	}
	public Integer getStatusBiezacy() {
		return statusBiezacy;
	}
}
