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
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Restrictions;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.sql.ordering.antlr.Factory;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;
import com.sun.org.apache.xpath.internal.operations.And;

import automation.admin.CodePassword;
import automation.main.UserSession;

@WebServlet("/confirmation/*")
public class SignInConfirmationHandler extends HttpServlet{

	PrintWriter pw;
	final String confirmation = "confirmation/";
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException, ParseException {
		response.setContentType("text/html; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		
		pw = response.getWriter();

		int lastIndex = request.getRequestURI().indexOf("confirmation")+confirmation.length();
		String mainPath = request.getRequestURI().substring(lastIndex);
		
		System.out.println("mainpath: "+mainPath);
		
		
		//update na userze, który miał taki confirmation link
		// status na c
		
		SessionFactory sf = new Configuration().configure().buildSessionFactory();
		Session session = sf.openSession();
		org.hibernate.Transaction tx = session.beginTransaction();

		String hqlUpdate = "update Users u "
					+ "set u.userConfirmationStatus = :userConfirmationStatus "
					+ "where u.userTempConfirmationLink = :userTempConfirmationLink";
		// or String hqlUpdate = "update Customer set name = :newName where name = :oldName";
		int updatedEntities = session.createQuery( hqlUpdate )
		        .setString( "userConfirmationStatus", "c" ) //c - confirmed
		        .setString( "userTempConfirmationLink", mainPath )
		        .executeUpdate();
		
		tx.commit();
		session.close();
		
		
		
/*		SessionFactory sf = new Configuration().configure().buildSessionFactory();
	    Session session = sf.openSession();

	    Users updatedUser = new Users();
	    updatedUser.setUserConfirmationStatus('c');

	    //session.update(updatedUser);
	    
	    String hql = "UPDATE Users set userConfirmationStatus = :status "  + 
	             "WHERE userTempConfirmationLink = :mainPath";
	    Query query = session.createQuery(hql);
	    query.setParameter("status", 'c');
	    query.setParameter("mainPath", mainPath);
	    int result = query.executeUpdate();
	    System.out.println("Rows affected: " + result);
	    */
	    
		
	}

}
