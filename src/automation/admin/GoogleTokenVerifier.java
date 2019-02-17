package automation.admin;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.hibernate.service.ServiceRegistry;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

import automation.main.UserSession;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;

@WebServlet("/validateGoogleUser")
public class GoogleTokenVerifier extends HttpServlet{

private List<Users> users = new ArrayList();
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, ParseException {
		
		response.setContentType("text/html; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		
		PrintWriter pw = response.getWriter();
	
		String idTokenString = request.getParameter("tokenId");
	
		System.out.println(idTokenString);
		
		Collection<String> clientId = new ArrayList<>();

		clientId.add("337627846521-h6frn6s4o4jsh1kggl66n9r5m2fla546.apps.googleusercontent.com");
	 		
	
		GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(),
										new com.google.api.client.json.jackson2.JacksonFactory())
			    .setAudience(clientId)
			    .build();

			
		
			GoogleIdToken idToken = null;
			try {
				idToken = verifier.verify(idTokenString);
			} catch (GeneralSecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (idToken != null) {
			  Payload payload = idToken.getPayload();

			  // Print user identifier
			  String userId = payload.getUserId();

			  System.out.println("User ID: " + userId);

			  // Get profile information from payload
			  String email = payload.getEmail();
			  boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
			  String pictureUrl = (String) payload.get("picture");
			  String username = (String) payload.get("name");
			  String locale = (String) payload.get("locale");
			  String familyName = (String) payload.get("family_name");
			  String givenName = (String) payload.get("given_name");

			  System.out.println(" user email: "+email+
					  			 "\n user name: " + username+
					  			 "\n user locale: "+ locale+
					  			 "\n family name: "+familyName+
					  			 "\n given name: "+givenName+
					  			 "\n picture url: "+pictureUrl);
			  //sprawdzenie, czy użytkownik już wcześniej się logował
			  
				SessionFactory s1 = new Configuration().configure().buildSessionFactory();
				Session session1 = s1.openSession();
				Criteria criteria1 = session1.createCriteria(Users.class);
				Criterion equalEmail = Restrictions.eq("userEmail", email);
				criteria1.add(equalEmail);
				users = criteria1.list();
				s1.close();
				
				if (users.size()<1) {
					System.out.println("użytkownik nie logował się wcześniej z googla");
					//wpis do tabeli z użytkownikami
					Users user = new Users(username
							  ," "
							  ,'g' //g-google
							  ,email
							  ,'c' //c-confirmed
							  ," "
							  );

						SessionFactory sf = new Configuration().configure().buildSessionFactory();
						Session session = sf.openSession();

						org.hibernate.Transaction tx = session.beginTransaction();
						session.save(user);

						tx.commit();

				}
			  	//wpis do tabeli sesji z pobranym cookiesem
			  	
			//	System.out.println("Użytkownik "+users.get(0).getUsername()+" zalogowany poprawnie");
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
				Session session2 = factory.openSession();
				org.hibernate.Transaction tx = session2.beginTransaction();
				session2.createQuery("DELETE FROM UserSession sess "  
								   + "WHERE sess.username = :username ")
				.setParameter("username", username)
				.executeUpdate();

				session2.save(userSession);
				        
				tx.commit();

				session2.close();
			  
			  
				pw.print("ok");
			} else {
			  System.out.println("Invalid ID token.");
			}
	
			
	}
	
	
	
	
}

