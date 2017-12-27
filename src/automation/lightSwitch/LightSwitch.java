package automation.lightSwitch;

public class LightSwitch {
	
	private String id;
	private Integer statusBiezacy = 0;
	boolean ustawionoPozaEsp = false;
//	private String client;
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
		
		//System.out.println("set status :::::::::: status: "+status);
		//System.out.println("set status :::::::::: statusbiezacy: "+statusBiezacy);
		
		//System.out.println("przekazany klient: "+client);
		
		if (!status.equals(statusBiezacy) && client.equals("p"+id)) {
			ustawionoPozaEsp = true;
			setStatusBiezacy(status);
			
			System.out.println("weszło w ifa jak klient jest przegladarka");
			System.out.println("przekazany status: "+status);
		}
		if (!status.equals(statusBiezacy) && client.equals(id)) {		
			if (ustawionoPozaEsp) {
				ustawionoPozaEsp = false;
				System.out.println("weszło w ustawiono poza ESP!!!");
			}
			else {
				setStatusBiezacy(status);
				System.out.println("zmiana z fizycznego switcha");
			}
			
		}
		
		//System.out.println("status biezacy z klasy LightSwitch " + statusBiezacy);
	}
	
	public Integer getStatusBiezacy() {
		
		return statusBiezacy;
	}

}
