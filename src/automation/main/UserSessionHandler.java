package automation.main;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import automation.admin.Users;

public class UserSessionHandler{
	
	private List<UserSession> userSession = new ArrayList();
	private List<Users> users = new ArrayList();
	private String username = null;

	public UserSessionHandler() {
		System.out.println("domyślny konstruktor");
	}
	public UserSessionHandler(String sessionId) {

		SessionFactory s1 = new Configuration().configure().buildSessionFactory();
		Session session = s1.openSession();
		Criteria criteria = session.createCriteria(UserSession.class);
		criteria.add(Restrictions.eq("sessionId",sessionId));
		userSession = criteria.list();
		try {
			username = userSession.get(0).getUsername();
		}catch (Exception e) {
			System.out.println("nie ma takiej sesji");
		}
	}
	
	public String getUsername(){
		if (username != null) {
			return username;
		}else {
			return "false";
		}
	}
	
 	
}
