<%@ page
	import="java.io.File,java.io.FilenameFilter,java.util.Arrays"%>
    
    <head>
     
        
    </head>
<%
/**
  * jQuery File Tree JSP Connector
  * Version 1.0
  * Copyright 2008 Joshua Gould
  * 21 April 2008
*/	
    String dir = request.getParameter("dir");
    if (dir == null) {
    	return;
    }
	
	if (dir.charAt(dir.length()-1) == '\\') {
    	dir = dir.substring(0, dir.length()-1) + "/";
	} else if (dir.charAt(dir.length()-1) != '/') {
	    dir += "/";
	}
	
	dir = java.net.URLDecoder.decode(dir, "UTF-8");	
	
    if (new File(dir).exists()) {
		String[] files = new File(dir).list(new FilenameFilter() {
		    public boolean accept(File dir, String name) {
				return name.charAt(0) != '.';
		    }
		});
		Arrays.sort(files, String.CASE_INSENSITIVE_ORDER);
		out.print("<ul class=\"jqueryFileTree\" style=\"display: none;\">");
		// All dirs
                for (String file : files) {
                    String fullPath =dir+file;
                    
		    if (new File(dir, file).isDirectory()) {
				out.print("<li class=\"directory collapsed\"><a href=\"#\" id=\"dirPath1+'"+file+"'\" onclick=\"populateValue1('"+dir+"','"+file+"');\" rel=\"" + dir + file + "/\">"
					+ file + "</a></li>");
		    }
		}
		
    }
%>
   
        <script type="text/JavaScript">  
    function populateValue1(elm , path){

$("#dataDir").text(elm+path);

$("#dataDirVar").val(elm+path);
	}
     </script>