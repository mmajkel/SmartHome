package automation.devices.lightSwitch;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;


@WebServlet("/myhome/lightswitchesStatus")
public class LightSwitchStatus extends HttpServlet {

	private List<LightSwitch> lightList = LightSwitchHandler.lightSwitchesList;
	private Integer correctStatus = 2;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) 
			throws IOException, ServletException, ParseException {

		res.setContentType("text/html; charset=utf-8");
		PrintWriter pw = res.getWriter();
		
		String deviceId = req.getParameter("deviceId");
		
		for(int i=0; i<LightSwitchHandler.lightSwitchesList.size(); i++) {
			String correctId = lightList.get(i).getId();
			if(correctId.equals(deviceId)) {
				correctStatus = lightList.get(i).getActualStatus();
			}
		}
		pw.print(correctStatus);
	}
}
