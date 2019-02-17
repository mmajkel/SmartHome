package automation.admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transaction;

import org.eclipse.jdt.internal.compiler.ast.AND_AND_Expression;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Restrictions;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.sql.ordering.antlr.Factory;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;
import com.sun.org.apache.xpath.internal.operations.And;

import automation.admin.CodePassword;
import automation.main.UserSession;

@WebServlet("/validateUser")
public class ValidateUser extends HttpServlet{
	
	private List<Users> users = new ArrayList();
	private boolean isPasswordCorrect = false;
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, ParseException {
	
		response.setContentType("text/html; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		
		PrintWriter pw = response.getWriter();
	
		String username = request.getParameter("login");
		String enteredPassword = request.getParameter("password");
		
		InjectingChecker ic = new InjectingChecker();
		
		if (	ic.isSqlInjection(username)){
	  //      ||	ic.isSqlInjection(enteredPassword)) {
			pw.print("hakier");
		}else {
			
/*			//pobranie soli dla znalezionego loginu
				
			SessionFactory s1 = new Configuration().configure().buildSessionFactory();
	        Session session1 = s1.openSession();
	        org.hibernate.Transaction tx1 = session1.beginTransaction();
	        session1.createQuery("SELECT password FROM Users users "  
	        		+ "WHERE users.username = :username ")
	        .setParameter("username", username)
	        .executeUpdate();

	        tx1.commit();

	        session1.close();
			
			//
			*/
			SessionFactory s2 = new Configuration().configure().buildSessionFactory();
			Session session2 = s2.openSession();
			Criteria criteria2 = session2.createCriteria(Users.class);
			Criterion equalUsername = Restrictions.eq("username", username.toLowerCase().trim());
//			Criterion normalOrCompanylUserType = Restrictions.in("userType", new char[] {'n','c'});
			Criterion notPendingStatus = Restrictions.ne("userConfirmationStatus", 'p');
			LogicalExpression andExp = Restrictions.and(equalUsername/*,normalOrCompanylUserType*/,notPendingStatus);
			criteria2.add(andExp);
	//lista zawiera jednego usera i zahashowane hasło
			users = criteria2.list();
			
	
			s2.close();
			if (users.size() < 1) {
				System.out.println("długość znalezionej tablicy = "+users.size());
				System.out.println("Błędne dane logowania. Nazwa użytkownika: "+username+" hasło: "+enteredPassword);
				pw.print("Nie masz konta? załóż już dzisiaj");
			}else{
			
				CodePassword cp = new CodePassword();
				try {
					isPasswordCorrect = cp.check(enteredPassword,users.get(0).getPassword());
					System.out.println("password correct " + isPasswordCorrect);
				} catch (Exception e) {
					pw.println("wystąpił błąd - spróbuj ponownie pózniej");
				}
			}
			
			if (isPasswordCorrect) {
				
				System.out.println("Użytkownik "+users.get(0).getUsername()+" zalogowany poprawnie");
				//pobranie session id
				request.getSession();
				String sessionId = request.getSession().getId();
				System.out.println("session id= "+sessionId);
				//insert do tabeli z nazwą użytkownika i session id
				//   tabela zawiera session id do wznowienia wczytania kontekstu użytkownika jeżeli się nie wylogował
					UserSession userSession = new UserSession(username,sessionId);
					Configuration config = new Configuration();
					        config.configure();
					        config.addAnnotatedClass(UserSession.class);
					        ServiceRegistry sr = new StandardServiceRegistryBuilder().applySettings(config.getProperties()).build();
					        SessionFactory factory = config.buildSessionFactory(sr);

					        Session session3 = factory.openSession();
					        org.hibernate.Transaction tx = session3.beginTransaction();
					        session3.createQuery("DELETE FROM UserSession sess "  
					        		+ "WHERE sess.username = :username ")
					        .setParameter("username", username)
					        .executeUpdate();

					        session3.save(userSession);
					        
					        tx.commit();

					        session3.close();
					        
					   pw.print("ok");
			}else {
				pw.println("błędne hasło");
			}
		}
	}
}
