package automation.admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transaction;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.sql.ordering.antlr.Factory;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;
import automation.admin.CodePassword;
import automation.main.UserSession;

@WebServlet("/myhome/logoff")
public class LogoffUser extends HttpServlet{
		
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, ParseException {
	
		response.setContentType("text/html; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		
		PrintWriter pw = response.getWriter();
	
		String username = request.getParameter("login");
				
			Configuration config = new Configuration();
	        config.configure();
	        config.addAnnotatedClass(UserSession.class);
			ServiceRegistry sr = new StandardServiceRegistryBuilder().applySettings(config.getProperties()).build();
			SessionFactory factory = config.buildSessionFactory(sr);

	        Session session1 = factory.openSession();
	        org.hibernate.Transaction tx = session1.beginTransaction();
	        session1.createQuery("DELETE FROM UserSession sess "  
	        		+ "WHERE sess.username = :username ")
	        .setParameter("username", username)
	        .executeUpdate();

	        tx.commit();

	        session1.close();
					        
		   pw.print("ok");
	}
}
