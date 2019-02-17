package automation.devices.lightSwitch;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

import automation.main.UserSession;

/*
 * 
 * to jest serwlet dla włączników świateł
 * w nim obsługiwany jest formularz dla włączania ->wysyłania komunikatu ze 
 * stanem na jaki użytkownik chce zmienić stan włącznika i numerem klienta,
 * którego status chce zmienić
 * 
 * 
*/
@WebServlet("/myhome/lightswitches")
public class LightSwitchHandler extends HttpServlet {
	
	static public List<LightSwitch> lightSwitchesList = new ArrayList<>();
	//static LightSwitch l1 = new LightSwitch("2c3ae8359ef");
	final private String browserCaller = "p";
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) 
			throws IOException, ServletException, ParseException {
		
		res.setContentType("text/html; charset=utf-8");
		PrintWriter pw = res.getWriter();
		
		Integer status = Integer.parseInt(req.getParameter("status"));
		String client = req.getParameter("client");
		String callerType = req.getParameter("callerType");
		
//		System.out.println("!!---serwer---!!! "+client);
		
		for (LightSwitch light : lightSwitchesList) {
				if(client.equals(light.getId()) && !callerType.equals(browserCaller)) {
//					System.out.println("1 if z obiektu" +client);
					light.setStatus(status, client, false);
					pw.println(light.getActualStatus());
				}
				if(callerType.equals(browserCaller) && client.equals(light.getId())) {
//					System.out.println("2 if z obiektu" +client);
					light.setStatus(status, client, true);
				}
				if(light.isStatusChanged()) {
//					System.out.println("3 if z obiektu" +client);
					light.statusChangedAprooved(false);
					System.out.println(light);
				}
		}
		
//		System.out.println("!!---serwer-koniec---!!! "+client);
	}
		
}