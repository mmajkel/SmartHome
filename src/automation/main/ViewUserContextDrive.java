package automation.main;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

@WebServlet("/mydrive/getFiles")
public class ViewUserContextDrive extends HttpServlet{
	
private List<String> foldersList = new ArrayList<>();
private List<String> filesList = new ArrayList<>();
	
@Override
protected void doPost(HttpServletRequest req, HttpServletResponse res) 
		throws IOException, ServletException, ParseException {
	
	res.setContentType("text/html; charset=utf-8");
	PrintWriter pw = res.getWriter();
	
	String path = req.getParameter("path");
		
	//System.out.println("request="+path);
	
	File folder = new File(path);	
	String[] directories = folder.list(new FilenameFilter() {
		
		  @Override
		  public boolean accept(File current, String name) {
		    return new File(current, name).isDirectory();
		  }
		});
	
	//System.out.println("directories: ");
	
	for (String directory : directories) {
		foldersList.add(directory);
		//System.out.println(directory);
	//	pw.println(directory);
		}

	File file = new File(path);
	File[] listOfFiles = folder.listFiles();

	//System.out.println("files: ");

	for (File file1 : listOfFiles) {
		filesList.add(file1.getName());
		//System.out.println(file1.getName());
		pw.println(file1.getName());
		}
	}
	/*public ViewUserContextDrive(String directory){

		File folder = new File("D:/");	
		String[] directories = folder.list(new FilenameFilter() {
			
			  @Override
			  public boolean accept(File current, String name) {
			    return new File(current, name).isDirectory();
			  }
			});
		for(int i=0;i<directories.length;i++) {
			foldersList.add(directories[i]);
			System.out.println(directories[i]);
		}

		
		File file = new File("D:/");
		File[] listOfFiles = folder.listFiles();

		for (File file1 : listOfFiles) {
		    if (file1.isFile()) {
		    	filesList.add(file1.getName());
		        System.out.println(file1.getName());
		    }
		}
		
	}
	
	public List<String> getFolders(){
		return foldersList;
	}*/



}
