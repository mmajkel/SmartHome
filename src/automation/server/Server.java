package automation.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

import automation.lightSwitch.LightSwitch;

/*
 * 
 * to jest serwlet dla włączników świateł
 * w nim obsługiwany jest formularz dla włączania ->wysyłania komunikatu ze 
 * stanem na jaki użytkownik chce zmienić stan włącznika i numerem klienta,
 * którego status chce zmienić
 * 
 * 
*/
@SuppressWarnings("serial")
public class Server extends HttpServlet {
	
	static Integer statusBiezacy=0;
	boolean ustawionoPozaEsp = false;

	static List<LightSwitch> lightSwitchesList = new ArrayList<>();
	static LightSwitch l1 = new LightSwitch("0");
	static LightSwitch l2 = new LightSwitch("d1c2d3a4");
	
	static {
	lightSwitchesList.add(l1);
	lightSwitchesList.add(l2);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException, ParseException {
		
		int licznik=0;
		
		res.setContentType("text/html; charset=utf-8");
		PrintWriter pw = res.getWriter();
		
		Integer status = Integer.parseInt(req.getParameter("status"));
		String client = req.getParameter("client");
		//System.out.println("klient: "+client);
		
	//	System.out.println("status " + status);
		
		
		//tu trzeba stwierdzić, czy zapytanie było z esp, czy z przeglądarki i jakiego urządzenia dotyczy
		//jakiegoś trima na stringu, żeby stwierdzić, czy przekazuję mac, czy P+mac
		
	
		
		for (LightSwitch light : lightSwitchesList) {
	//		System.out.println("id: " + light.getId());
	//		System.out.println("status z LightSwitch: " + light.getStatusBiezacy());
				if(light.getId().equals(client)) {
					
					//System.out.println("id trafionego: " + light.getId());
					//set status biezacy - ustawiam status jaki ma być na esp

					light.setStatusBiezacy(statusBiezacy);
					light.setStatus(status, client);
					statusBiezacy = light.getStatusBiezacy();
				}
				if(client.equals("1")) {  /// w equals "p"+light.getId()
					
					light.setStatusBiezacy(statusBiezacy);
					light.setStatus(status, client);
					statusBiezacy = light.getStatusBiezacy();
				}
				
		
				//mapowanie obiektowo relacyjne
				//for (LightSwitch light : lightSwitchesList) {
//			
//				//if (!light.getStatus.equlas(light.getStatusBiezacy)){
				
				//update do bazy 
				
				//select id from lightSwitch where id=light.getId
				
		}
		
		System.out.println("status biezacy z serwera: " + statusBiezacy);
		
		pw.println();	
		pw.println(statusBiezacy);
		
		}

}
