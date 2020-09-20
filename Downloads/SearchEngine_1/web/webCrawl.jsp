<%@ page import = "java.io.*,java.util.*,java.sql.*"%>
<%@ page import = "javax.servlet.http.*,javax.servlet.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix = "c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix = "sql"%>
 

<html lang="en">
<head>
  <meta charset="utf-8">
  <title>Search Engine - Web Crawl</title>
  <meta name="description" value="The search engine that doesn't track you. Learn More.">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
   <link rel="stylesheet" type="text/css" href="static/colors.css">
  <link rel="stylesheet" type="text/css" href="static/modal.css">
  <link rel="stylesheet" type="text/css" href="static/index.css">
  <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
  <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
  <script src="static/jQuery.js"></script>
  
 
  
  
</head>

   <body>
       <script>
         $(document).ready(function(){
             
                $("#crawlSite").show();
             
            $("#crawl").click(function(){
                
               $("#crawlSite").toggle();
                
            }) ;
             
      var myOptions = {
      1 : 1,
      2 : 2,
      3 : 3
    
    
};

$.each(myOptions, function(val, text) {
    $("#depth").append(
        $('<option></option>').val(val).html(text)
    );
});
             
             
         } );  
     
           
           
       </script>
   <div style="background-color: #e3e3e3">
             </b> Remove contents under  <b><%=pageContext.getServletContext().getInitParameter("web-crawl-dir")%></b>
             <br>
             <br>
             <form class="w3-container" action="CrawlerServlet" method="POST">
                 <input type="hidden" name="crawlIndex" value="<%=pageContext.getServletContext().getInitParameter("web-crawl-dir")%>">
             <div id="crawlSite">
                 <table id="customer">
                     <tr><td> Enter Web Site to Crawl <input class="w3-input" type="text" name="site" id="site"/></td></tr>
                     <tr><td> Enter Crawl Depth <select name="depth" id="depth"> (Note : More Depth may take more time to index.choose 1)
         
         </select>    </td></tr>
                 </table>
                 <input type="submit" class="btn btn-primary" onclick="javascript:document.getElementById('pBar').style.display='block'"  value="Crawl">
                 
                   <div id="pBar" style="display:none">
          Indexing..<img src="images/spinner.gif">
      </div>
                 
             </div>
             </form>
                    
       
          
             <br>
             <br>
              <div id="searchWeb">
              
                  <a href="webSearch.jsp"> Go To Search Page
                 
             </div>
 
   </body>
   
   
   

</html>