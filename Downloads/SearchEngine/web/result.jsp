<%-- 
    Document   : result
    Created on : 4 Jun, 2020, 4:51:56 PM
   
--%>


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
		
		<link href="jqueryFileTree.css" rel="stylesheet" type="text/css" media="screen" />
	
                <script type="text/javascript">
			
			$(document).ready( function() {
				
				$('#fileTreeDemo_1').fileTree({ root: '<%=  (String)session.getAttribute("indexPath")%>', script: 'connectors/jqueryFileTree_1_1.jsp' }, function(file) { 
					
				});
                                
                              $('#fileTreeDemo_2').fileTree({ root: '<%=  (String)session.getAttribute("docsPath")%>', script: 'connectors/jqueryFileTree_1_1.jsp' }, function(file) { 
					
				});
				
			
				
			});
		</script>
		

	</head>
	<% if( (String)session.getAttribute("error")!=null){ %>
        <Font color="red"> <%=(String)session.getAttribute("error")%>  <a href="admin.jsp">Re-Index</a> </font>
        Solution : Go to your index directory and clean it.
          <% } else {%>  
    <font color=green>Index Completed Successfully</font> 
    <p><a href="admin.jsp">Re-Index</a></p>
    <p><a href="search.jsp"> Start Search</font><a></p>
             
    
	<body>
       
			
                        <table><tr><td> <div class="example">
                               Check the index under (<%=  (String)session.getAttribute("indexPath")%>) <div id="fileTreeDemo_1" class="demo">
                       
			
                                </div></div>
                            </td>
                        <td>
                     
                        <div class="example"> 
                            Documents Indexed <div id="fileTreeDemo_2" class="demo">
                       
			
		</div>
                        </div>
                        </td>
                            </tr>
                        </table>
       </div>
      <% } %>                         
        </body>
</html>
