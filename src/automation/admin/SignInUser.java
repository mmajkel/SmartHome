package automation.admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
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

@WebServlet("/signInUser")
public class SignInUser extends HttpServlet{
	
	private List<Users> users = new ArrayList();
	private boolean isPasswordCorrect = false;
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, ParseException {
	
		response.setContentType("text/html; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		
		PrintWriter pw = response.getWriter();
	
		String username = request.getParameter("login");
		String email = request.getParameter("email");
		String enteredPassword = request.getParameter("password");
		

		System.out.println("enteredPassword="+enteredPassword+
						   " username="+username);
		
		
		InjectingChecker ic = new InjectingChecker();
		
		if (	ic.isSqlInjection(username)
	//        ||	ic.isSqlInjection(enteredPassword)
	        ||  ic.isSqlInjection(email) 
	                                           ) {
			pw.print("hakier");
		}else {

			//Sprawdzenie, czy istnieje już taki email
			SessionFactory s2 = new Configuration().configure().buildSessionFactory();
			Session session2 = s2.openSession();
			Criteria criteria2 = session2.createCriteria(Users.class);
			Criterion rest2 = Restrictions.eq("username", username.toLowerCase().trim());
			criteria2.add(rest2);

			users = criteria2.list();
			s2.close();
			
			if (users.size() > 0) {
				System.out.println("jest już user o tym loginie");
				// docelowo błąd logowania i email do użytkownika, że ktoś się próbował zalogować
				pw.print("Użytkownik już istnieje"); 
			}else{
			String hashedPassword = " ";
				CodePassword cp = new CodePassword();
				try {
					hashedPassword = cp.getSaltedHash(enteredPassword);
					System.out.println("hashedPass="+hashedPassword);
				} catch (Exception e) {
					pw.println("wystąpił błąd - spróbuj ponownie pózniej");
				}
			String randomTempConfirmationLink = RandomLinkGenerator.generateRandomString(50);
				
				Users user = new Users(username
									  ,hashedPassword
									  ,'n' //n-
									  ,email
									  ,'p' //pending
									  ,randomTempConfirmationLink
									  );

				SessionFactory sf = new Configuration().configure().buildSessionFactory();
			    Session session = sf.openSession();

			    org.hibernate.Transaction tx = session.beginTransaction();
			    session.save(user);

			    String link = "http://localhost:8080/Automation/confirmation/"+randomTempConfirmationLink;
			    String message = "wiadomosc wygenerowana automatycznie \n "
			    		+ "<a href="+link+">"+link+"</a>";
			    System.out.println(message);
			    
			    EmailSender javaEmail = new EmailSender();
				javaEmail.setMailServerProperties();
				try {
					javaEmail.createEmailMessage("92bialy92@gmail.com", "rejestracja", message);
				} catch (MessagingException e) {
					e.printStackTrace();
				}
				try {
					javaEmail.sendEmail();
				} catch (MessagingException e) {
					e.printStackTrace();
				}
				tx.commit();
				
			}
			
		}
	}
}
