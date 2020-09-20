/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.searchquery;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.InputStreamReader;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


@WebServlet(name = "WebSearch", urlPatterns = {"/WebSearch"})
public class WebSearch extends HttpServlet {
    
     private static final String INDEX_DIR = null;
     String searchKW =null;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
       
        HttpSession ses = request.getSession();
        PrintWriter out = response.getWriter();
         String keyWordFromSite= request.getParameter("webKeyword")+" ";
         
String keyWord = null;
// capitalize first letter
if(keyWordFromSite.split("\\s+").length ==2) {
    
  System.out.println(keyWordFromSite.split("\\s+")[0].substring(0, 1).toUpperCase() +keyWordFromSite.split("\\s+")[0].substring(1)
          +"_"+keyWordFromSite.split("\\s+")[1].substring(0, 1).toUpperCase() +keyWordFromSite.split("\\s+")[1].substring(1));
keyWord = keyWordFromSite.split("\\s+")[0].substring(0, 1).toUpperCase() +keyWordFromSite.split("\\s+")[0].substring(1)
          +" "+keyWordFromSite.split("\\s+")[1].substring(0, 1).toUpperCase() +keyWordFromSite.split("\\s+")[1].substring(1);
  
}else if(keyWordFromSite.split("\\s+").length ==1) {
    
  
keyWord = keyWordFromSite.split("\\s+")[0].substring(0, 1).toUpperCase() +keyWordFromSite.split("\\s+")[0].substring(1)
         ;
  
}

else {
   keyWord =  keyWordFromSite;
    
}
String[] fields = {"title", "contents"};
     
     // Embed HTML search page content
     
        // Embed HTML search page content
     
 
     out.println("<html lang=\"en\">");
     out.println("<head>");

out.println("<meta charset=\"utf-8\">");
   out.println("<title>Search</title>");
 out.println("<meta name=\"description\" value=\"The search engine that doesn't track you. Learn More.\">");
out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
out.println("<meta http-equiv=\"Content-Type\" content=\"text/html;\">");
out.println("<link href=\"//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css\" rel=\"stylesheet\" id=\"bootstrap-css\"> "
        +
        "<script src=\"//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js\"></script> "
        +
   "<script src=\"//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js\"></script>" 
+
   "<script src=\"https://code.jquery.com/jquery-1.12.4.js\"></script>" 
+
   "<script src=\"https://code.jquery.com/ui/1.12.1/jquery-ui.js\"></script>"
        
+  " <script src=\"static/search.js\"></script>"
        
+  " <script src=\"static/suggest.js\"></script>" 

+   "<link rel=\"stylesheet\"  href=\"static/index.css\"> "       
        
+   "  <link rel=\"stylesheet\" href=\"static/style.css\">" 
    

);
  
 out.println("</head>");
 out.println("<body style=\"margin-bottom:80%\">");
        
        
        out.println("<div class=\"container\">");
            out.println(" <form action=\"WebSearch\" id=\"searchFrm\" method=\"POST\">");
              out.println("<div class=\"row\">");
      out.println(" <div class=\"col-12\"><h2>Web Search Here</h2></div>");
      
       out.println("  <div class=\"col-12\">");
       out.println(" <div id=\"custom-search-input\">");
       
          out.println("  <div class=\"input-group\">");
      
        
       out.println("  <input type=\"search\" name=\"webKeyword\" value='"+keyWord+"' class=\"search-query form-control\" placeholder=\"Search\" />");
       out.println(" <span class=\"input-group-btn\">");
         out.println(" <button onclick=\"javascript:document.getElementById('pBar').style.display='block'\" type=\"submit\" >");
          out.println("  <span class=\"fa fa-search\"></span>");
             out.println("  </button>\n" +
"                    </span>\n" +
"                </div>\n" +
"                <br>");
               
          out.println("<div>  No. of Search Result per Page <select name=\"noResult\" id=\"noResult\">");
 out.println("</select></div>  </div>");
 out.println("<div id=\"pBar\" style=\"display:none\">\n" +
"          <img  src=\"images/spinner.gif\">\n" +
"      </div>");
      out.println("</div></form></div>");
     
     
               //creating ServletContext object  
                    ServletContext context=getServletContext();  
  

               
                String indexPath = context.getInitParameter("web-crawl-dir"); 
            int maxHitsDisplay  = 5;
	   maxHitsDisplay = Integer.parseInt(request.getParameter("noResult"));	// Maximum number of result documents to display

		try {
			// Initialize the index reader
			
                      
                       System.out.println(indexPath);
                        
                        IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths.get(indexPath)));

			// Initialize the index searcher
			IndexSearcher searcher = new IndexSearcher(reader);

			Analyzer analyzer = new StandardAnalyzer();
			
			// MultifiedQueryParser can search multiple fields in the document objects
			// using the same parser instance. 
			MultiFieldQueryParser mfparser = new MultiFieldQueryParser(fields, analyzer);

			BufferedReader in = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));

			// Parse the query and store it in a Query Object
			Query q = mfparser.parse(request.getParameter("webKeyword"));
                        
                        request.setAttribute("searchKW", request.getParameter("webKeyword"));
			
		out.println("\nSearching For:  <b>" + request.getParameter("webKeyword") + "</b>\n"+"<br><br>");
			
			// Call the method that executes the search
			executeSearch(in, searcher, q, maxHitsDisplay ,out , keyWord ,keyWordFromSite);

			// Close the IndexReader
			reader.close();
			
		} catch(Exception e) {
			System.out.println("<font color=\"red\">Error while initializing index reader " + e +"</font><br><a href=\"webCrawl.jsp\">Index Here</a>");
		}
    }

    
    
     public static void executeSearch(BufferedReader in, IndexSearcher searcher, Query query,
			int maxHitsDisplay , PrintWriter out , String keyWord ,String keyWordFromSite) throws IOException, ParseException {
		
		Date startDate = new Date();
                 List<String> siteimageList=new ArrayList<String>();
		
		// Collect hits
		TopDocs results = searcher.search(query, maxHitsDisplay);
		ScoreDoc[] hits = results.scoreDocs;
		int numTotalHits = Math.toIntExact(results.totalHits);
		
		// Get the lesser value between maximum hits to display and 
		// the actual number of hits. If hits are more than max hits 
		// to display, iterate to maxHitsDisplay. Else iterate to
		// the number of hits
		int end = Math.min(maxHitsDisplay, numTotalHits);
		
		Date endDate = new Date();
		
		out.println("Total " + numTotalHits + " Matching Documents Found in " + ((endDate.getTime() - startDate.getTime()) / 1000.0) + " Seconds<br><br>");
		out.println("Showing Top " + end + "\n");
                 out.println("<ul class=\"index__nav\">\n" +
"        <li class=\"index__active\" id=\"all\"><a id=\"All\" href=\"#\">All</a></li>\n" +
"        \n" +
"      \n" +
"        <li id=\"relLi\"><a id=\"images\" href=\"#\">Images</a></li>\n" +
"        \n" +
"      </ul>");
                out.println("<div style=\"width:1000px; height:800px; overflow:auto;\">");
               
                out.println("<table   id=\"customers\">");
                
                out.println("<thead><tr><th>Sl No.</th><th>Results</th><th>Score</th></tr></thead>");
                
          
                if(keyWordFromSite.split("\\s+").length ==2
                        || keyWordFromSite.split("\\s+").length ==1) { // For Two Keyword search query data first fetch from wiki page
                String wikiURL ="https://en.wikipedia.org/wiki/"+keyWord;
                
		
                org.jsoup.nodes.Document document1 = Jsoup.connect(wikiURL).get();
                             String description1 = document1.select("meta[name=description]").size()>0?
                                     document1.select("meta[name=description]").get(0).attr("content"):" ";  
				
                                  String body_text1 = document1.body().text().substring(0, 1000);
                                 
                           String highLiteQuery1 = body_text1.contains(keyWord)?
                                                  body_text1.replace(keyWord, "<font color=red><b>"+keyWord+"</b></font>"):body_text1;
                            System.out.println("&&&&&&&&&"+highLiteQuery1);                     
                               Elements images1 = document1.select("img");//doc1.select("link[href~=(?i)\\.(png|jpe?g|gif)]");  //.getElementsByTag("img") ;//
         
                               for (Element image : images1) {  
               siteimageList.add(image.attr("abs:src"));
 
            }   
        
                               out.println("<tr><td>"+(1)+"</td><td><a href=\'"+wikiURL+"' target=\"_blank\">"+ wikiURL+"</a></td>");

				// prints document score
				out.println("<td>98</td>");
                                // prints document content
				out.println("\n"+"<tr><td></td><td>"+highLiteQuery1+description1+"... "+"</td>");
                }
              
                
                
                
                 for (int i = 0; i < hits.length; ++i) { 
        int docId = hits[i].doc; 
        Document d = searcher.doc(docId); 
        System.out.println((i + 1) + ". " + d.get("content")); 
      } 
                
       
		
		// Iterate over the hits array
		for (int i = 0; i < end; i++) {

			Document doc = searcher.doc(hits[i].doc);

			String title = doc.get("title");
			String url = doc.get("url");
                    
            
			
			double score = 	hits[i].score;
                          String strImageURL =  null;
                        out.println("<tbody><tr>");
			if (url != null) {
                            org.jsoup.nodes.Document document = Jsoup.connect(url).get();
                             String description = document.select("meta[name=description]").size()>0?
                                     document.select("meta[name=description]").get(0).attr("content"):" ";  
				
                                  String body_text = document.body().text().substring(0, 1000);
                                 
                           String highLiteQuery = body_text.contains(keyWord)?
                                                  body_text.replace(keyWord, "<font color=red><b>"+keyWord+"</b></font>"):body_text;
                            System.out.println("&&&&&&&&&"+highLiteQuery);                     
                               Elements images = document.select("img");//doc1.select("link[href~=(?i)\\.(png|jpe?g|gif)]");  //.getElementsByTag("img") ;//
         
                               for (Element image : images) {  
               siteimageList.add(image.attr("abs:src"));
 
                System.out.println("Image Found!");
                System.out.println("src attribute is : "+strImageURL);
            }          

// prints the document rank and title 1. Manchester United.html
				//out.println("<tr>"+(i+1)+"  ");
                                
                              
                                
                              //  out.println("<tr><td>"+ title  +"</td>");

				// prints the path of the document
				out.println("<tr><td>"+(i+2)+"</td><td><a href=\'"+url+"' target=\"_blank\">"+ url+"</a></td>");

				// prints document score
				out.println("<td>"+ score + "\n"+"</td>");
                                // prints document content
				out.println("\n"+"<tr><td></td><td>"+highLiteQuery+description+"... "+"</td>");
                                
				
			} else {
				out.println((i+1) + ". " + "Document does not exist");
			}
		}
                
                out.println("</tr></tbody><br><br></table></div>");
                
                 
                 
                   out.println("<table  style=\"margin-top:-90%\" id=\"siteImage\" style=display:none>");

        out.println("<tr><td><img src="+siteimageList.get(40)+"></td>"
             + "<td><img src="+siteimageList.get(51)+"></td>"
             + "<td><img src="+siteimageList.get(63)+"></td>"
             + "<td><img src="+siteimageList.get(74)+"></td>"
                     + "<td><img src="+siteimageList.get(49)+"></td></tr>");  
                   out.println("<tr><td><img src="+siteimageList.get(0)+"></td>"
             + "<td><img src="+siteimageList.get(6)+"></td>"
             + "<td><img src="+siteimageList.get(7)+"></td>"
             + "<td><img src="+siteimageList.get(8)+"></td>"
                  + "<td><img src="+siteimageList.get(9)+"></td></tr>");
        out.println("<tr><td><img src="+siteimageList.get(10)+"></td>"
             + "<td><img src="+siteimageList.get(71)+"></td>"
             + "<td><img src="+siteimageList.get(83)+"></td>"
             + "<td><img src="+siteimageList.get(84)+"></td>"
                    + "<td><img src="+siteimageList.get(17)+"></td></tr>");
              out.println("<tr><td><img src="+siteimageList.get(20)+"></td>"
             + "<td><img src="+siteimageList.get(21)+"></td>"
             + "<td><img src="+siteimageList.get(23)+"></td>"
             + "<td><img src="+siteimageList.get(24)+"></td>"
                     + "<td><img src="+siteimageList.get(19)+"></td></tr>");
                 out.println("<tr><td><img src="+siteimageList.get(30)+"></td>"
             + "<td><img src="+siteimageList.get(31)+"></td>"
             + "<td><img src="+siteimageList.get(33)+"></td>"
             + "<td><img src="+siteimageList.get(34)+"></td>"
                     + "<td><img src="+siteimageList.get(39)+"></td></tr>");
          
                out.println("<tr><td><img src="+siteimageList.get(40)+"></td>"
             + "<td><img src="+siteimageList.get(101)+"></td>"
             + "<td><img src="+siteimageList.get(103)+"></td>"
             + "<td><img src="+siteimageList.get(94)+"></td>"
                     + "<td><img src="+siteimageList.get(118)+"></td></tr>"); 
                  out.println("<tr><td><img src="+siteimageList.get(110)+"></td>"
             + "<td><img src="+siteimageList.get(121)+"></td>"
             + "<td><img src="+siteimageList.get(123)+"></td>"
             + "<td><img src="+siteimageList.get(114)+"></td>"
                     + "<td><img src="+siteimageList.get(119)+"></td></tr>"); 
     
 out.println("</table>");
	}
    
    
    
    
    
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
             throws IOException, ServletException {
        
            try {
                doPost(request, response);
            } catch (Exception ex) {
                Logger.getLogger(WebSearch.class.getName()).log(Level.SEVERE, null, ex);
            }
      
    }
    
    	private static TopDocs searchById(Integer id, IndexSearcher searcher) throws Exception
	{
		QueryParser qp = new QueryParser("id", new StandardAnalyzer());
		Query idQuery = qp.parse(id.toString());
		TopDocs hits = searcher.search(idQuery, 10);
		return hits;
	}


    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
             throws IOException, ServletException {
         try {
             processRequest(request, response);
         } catch (Exception ex) {
             Logger.getLogger(WebSearch.class.getName()).log(Level.SEVERE, null, ex);
         }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
