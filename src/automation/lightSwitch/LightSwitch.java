package automation.lightSwitch;

public class LightSwitch {
	
	private String id;
	private Integer statusBiezacy = 0;
	boolean ustawionoPozaEsp = false, dostepny = true;
	public LightSwitch(String id){
		this.id = id;
	}
	public String getId() {
		return id;
	}
	private void setStatusBiezacy(Integer statusBiezacy) {
		this.statusBiezacy = statusBiezacy;
	}
	public void setDostepny(boolean dostepny) {
		this.dostepny = dostepny;
	}
	public void setStatus(Integer status, String client) {

		if (dostepny) {
		
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
	}
	public Integer getStatusBiezacy() {
		return statusBiezacy;
	}
}
