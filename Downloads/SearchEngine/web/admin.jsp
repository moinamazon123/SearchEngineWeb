<%-- 
    Document   : admin
    Created on : 4 Jun,2020 1:58:23 AM
   
--%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import = "java.io.*,java.util.*, javax.servlet.*,javax.swing.*,javax.swing.filechooser.FileSystemView" %>
<%@ page import="java.io.File,java.io.FilenameFilter,java.util.Arrays" %>
<html xmlns="http://www.w3.org/1999/xhtml">

	<head>
		<title>File Browser</title>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
		
		<style type="text/css">
			BODY,
			HTML {
				padding: 0px;
				margin: 0px;
			}
			BODY {
				font-family: Verdana, Arial, Helvetica, sans-serif;
				font-size: 11px;
				background: #EEE;
				padding: 15px;
			}
			
			H1 {
				font-family: Georgia, serif;
				font-size: 20px;
				font-weight: normal;
			}
			
			H2 {
				font-family: Georgia, serif;
				font-size: 16px;
				font-weight: normal;
				margin: 0px 0px 10px 0px;
			}
			
			.example {
				float: left;
				margin: 15px;
			}
			
			.demo {
				width: 200px;
				height: 400px;
				border-top: solid 1px #BBB;
				border-left: solid 1px #BBB;
				border-bottom: solid 1px #FFF;
				border-right: solid 1px #FFF;
				background: #FFF;
				overflow: scroll;
				padding: 5px;
			}
			
		</style>
		
		<script src="jquery.js" type="text/javascript"></script>
		<script src="jquery.easing.js" type="text/javascript"></script>
                <script src="jqueryFileTree.js" type="text/javascript"></script>
                <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
		
		<link href="jqueryFileTree.css" rel="stylesheet" type="text/css" media="screen" />
                <link href="static/style.css" rel="stylesheet" type="text/css" media="screen" />
		
		<script type="text/javascript">
			
			$(document).ready( function() {
				
				$('#fileTreeDemo_1').fileTree({ root: '<%=pageContext.getServletContext().getInitParameter("root-dir")	%>', script: 'connectors/jqueryFileTree.jsp' }, function(file) { 
					
				});
                                
                               $('#fileTreeDemo_2').fileTree({ root: '<%=pageContext.getServletContext().getInitParameter("root-dir")	%>', script: 'connectors/jqueryFileTree_1.jsp' }, function(file) { 
					
				});
				
			$("#create").click(function(){
                            alert(("#indexDirVar").val());
                            makeDir($("#indexDirVar").val());
                        });
				
			});
		</script>

	</head>
	
	<body>

            <p>Your root directory is <%=pageContext.getServletContext().getInitParameter("root-dir")	%></p>
            
            <% if((String)session.getAttribute("indexPath")!=null && (String)session.getAttribute("docsPath")!=null){ %>
            
            You have already set Index directory as <b> <%=(String)session.getAttribute("indexPath")%> </b> and
            data directory as <b><%=(String)session.getAttribute("docsPath")%></b>
                    
                    
                    <a href="admin_1.jsp" class="button3" id="reEnter" onclick="javascript:$("#formdiv").display='block'" >Re Index</a>
                   
                    
         <% } else { %>  
            <form  method="POST" action="CreateIndexAction">
                
                
                
            
		
            <input type="hidden" id="indexDirVar" name="indexDirPath" >	
            <input type="hidden" id="dataDirVar" name="dataDirPath">
		<div class="example">
			<h2>Select Index Directory</h2>
                        <div id="fileTreeDemo_1" class="demo">
                       
			
		</div>
                                                                 
                </div>
                 
                <div class="example">
			<h2>Select Data Directory</h2>
			<div id="fileTreeDemo_2" class="demo"></div>
		</div>
                     
                    
                <h3>You have selected index directory <div style="color:green" id="indexDir"></div></h3>
                
                    <h3>You have selected data directory <div style="color:green" id="dataDir"></div>

                        <% if( (String)request.getAttribute("docFlag")!=null) { %>
                        
                         <table border="0">
            <tr><td>Id</td><td>Id Value:<input type="text" name="id"></td></tr>
            <tr><td>Enter Field 1 :<input type="text" name="fieldName1"></td><td>Enter Field value 1:<input type="text" name="fieldValue1"></td></tr>
         <tr><td>Enter Field 2 :<input type="text" name="fieldName2"></td><td>Enter Field value 2:<input type="text" name="fieldValue2"></td></tr>
          <tr><td>Enter Field 3 :<input type="text" name="fieldName3"></td><td>Enter Field value 3:<input type="text" name="fieldValue3"></td></tr>
           <tr><td>Enter Field 4 :<input type="text" name="fieldName4"></td><td>Enter Field value 4:<input type="text" name="fieldValue4"></td></tr>
        </table>
                     
                        
                        <%}%>
            
            
</h3>
		
                    <input type="submit" onclick="javascript:document.getElementById('pBar').style.display='block'" class="button3" value="index">	
                         
                         <div id="pBar" style="display:none">
          <img src="images/spinner.gif">
                </div>
                        </form>
             
                              
                      
                        
            <%}%>
	</body>
	
</html>