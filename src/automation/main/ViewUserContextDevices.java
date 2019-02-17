package automation.main;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

import automation.devices.lightSwitch.LightSwitch;
import automation.devices.lightSwitch.LightSwitchHandler;

public class ViewUserContextDevices{
	
	private List<UserDevicesData> usersData = new ArrayList();
	
	public ViewUserContextDevices(String username) {

		SessionFactory s = new Configuration().configure().buildSessionFactory();
		Session session = s.openSession();
		Criteria c = session.createCriteria(UserDevicesData.class);
		c.add(Restrictions.eq("username", username));
		usersData = c.list();
		s.close();
		
		for (UserDevicesData userData : usersData) {
		    LightSwitchHandler.lightSwitchesList.add(new LightSwitch(userData.getDeviceId().trim()));
			System.out.println(userData.getUsername().trim() + " " +
							   userData.getDeviceId().trim() + " " +
							   userData.getDeviceGroup().trim() + " "+
							   userData.getDeviceName());
		}
	}
	public List<UserDevicesData> getUserContext(){
		return usersData;
	}
	public int getContextSize() {
		return usersData.size();
	}
}
