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

	static public List<LightSwitch> lightSwitchesList = new ArrayList<>();
	static LightSwitch l1 = new LightSwitch("2c3ae8359ef");
	static LightSwitch l2 = new LightSwitch("6019449b4c6");
	
	static {
	lightSwitchesList.add(l1);
	lightSwitchesList.add(l2);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException, ParseException {
		
		res.setContentType("text/html; charset=utf-8");
		PrintWriter pw = res.getWriter();
		
		Integer status = Integer.parseInt(req.getParameter("status"));
		String client = req.getParameter("client");
		
		for (LightSwitch light : lightSwitchesList) {
				if(client.equals(light.getId())) {
					
					light.setStatus(status, client);
					pw.println(light.getStatusBiezacy());
					
				}
				if(client.equals("p"+light.getId())) {
					
					light.setStatus(status, client);
					
				}
		
				//mapowanie obiektowo relacyjne
				//for (LightSwitch light : lightSwitchesList) {
//			
//				//if (!light.getStatus.equlas(light.getStatusBiezacy)){
				
				//update do bazy 
				
				//select id from lightSwitch where id=light.getId
		
				
				
				
		}
				
	}
		
		

}
