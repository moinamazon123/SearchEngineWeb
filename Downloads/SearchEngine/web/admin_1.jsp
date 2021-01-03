<%-- 
    Document   : admin_1
    Created on : 5 Jun, 2020 11:58:23 PM
   
--%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import = "java.io.*,java.util.*, javax.servlet.*,javax.swing.*,javax.swing.filechooser.FileSystemView" %>
<%@ page import="java.io.File,java.io.FilenameFilter,java.util.Arrays" %>
<%@ page import="org.apache.lucene.store.FSDirectory,java.nio.file.Paths,
                 org.apache.lucene.analysis.standard.StandardAnalyzer,
                 org.apache.lucene.index.IndexWriter,
                 org.apache.lucene.index.IndexWriterConfig"%>               
         
        
<html xmlns="http://www.w3.org/1999/xhtml">

    <body>
    
        <% 
// Removing the Old Index

//FSDirectory dir = FSDirectory.open(Paths.get((String)session.getAttribute("indexPath")));
//
//		IndexWriterConfig indexconfig = new IndexWriterConfig(new StandardAnalyzer());
//		IndexWriter writer = new IndexWriter(dir, indexconfig);
//		 writer.deleteAll();
    

        session.removeAttribute("indexPath");
        
        session.removeAttribute("docPath");
        request.setAttribute("docFlag" , request.getParameter("docFlag") );
        %>
          <jsp:forward page="admin.jsp" />
    </body>
          
	
	
</html>