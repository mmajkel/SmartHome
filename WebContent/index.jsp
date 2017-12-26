<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Light switch</title>
</head>
<body>
	<a href="witaj?status=1&client=1">WL</a></br>
	<a href="witaj?status=0&client=1">WYL</a>
	
	
	Aktualny czas: 
	<div>
	<%=java.util.Calendar.getInstance().getTime()%>
	</div>
</body>
</html>