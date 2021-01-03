<%-- 
    Document   : document
    Created on : 5 Jun, 2020, 11:59:57 PM
    
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create Document</title>
         <link rel="stylesheet" type="text/css" href="static/colors.css">
  <link rel="stylesheet" type="text/css" href="static/modal.css">
  <link rel="stylesheet" type="text/css" href="static/index.css">
  
    </head>
    <body>
        <form method="POST" action="CreateDocument">
        <input type="text" name="indexDir"   value="  <%=(String)session.getAttribute("indexPath")%>">
        <table border="0">
            <tr><td>Id</td><td>Id Value:<input type="text" name="id"></td></tr>
            <tr><td>Enter Field 1 :<input type="text" name="fieldName1"></td><td>Enter Field value 1:<input type="text" name="fieldValue1"></td></tr>
         <tr><td>Enter Field 2 :<input type="text" name="fieldName2"></td><td>Enter Field value 2:<input type="text" name="fieldValue2"></td></tr>
          <tr><td>Enter Field 3 :<input type="text" name="fieldName3"></td><td>Enter Field value 3:<input type="text" name="fieldValue3"></td></tr>
           <tr><td>Enter Field 4 :<input type="text" name="fieldName4"></td><td>Enter Field value 4:<input type="text" name="fieldValue4"></td></tr>
        </table>
        <input type="submit" value="Add to Index" >
        </form>
        
    </body>
</html>
