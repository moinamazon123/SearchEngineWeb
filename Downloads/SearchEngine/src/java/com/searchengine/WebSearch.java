/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.searchengine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Date;
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
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;


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
     String[] fields = {"title", "contents"};
     
     // Embed HTML search page content
     
     
       out.println("<html lang=\"en\">");
 out.println("<head>");
   out.println("<meta charset=\"utf-8\">");
   out.println("<title>START</title>");
   out.println("<meta name=\"description\" value=\"The search engine that doesn't track you. Learn More.\">");
   out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
 out.println("<meta http-equiv=\"Content-Type\" content=\"text/html;\">");
out.println("<script language=\"javaScript\" type=\"text/javascript\" src=\"static/jQuery.js\"></script> "
        +
        "<script language=\"javaScript\" type=\"text/javascript\" src=\"static/search.js\"></script> "
        +
   "<link href=\"static/colors.css\" rel=\"stylesheet\" type=\"text/css\">" 
+
   "<link href=\"static/modal.css\" rel=\"stylesheet\" type=\"text/css\">" 
+
   "<link href=\"static/index.css\" rel=\"stylesheet\" type=\"text/css\">"
        
+  " <link rel=\"stylesheet\" href=\"//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css\">"
        
+  "<link rel=\"stylesheet\" href=\"/resources/demos/style.css\">" 
        
+   "<script src=\"https://code.jquery.com/jquery-1.12.4.js\"></script>" 
        
+  "<script src=\"https://code.jquery.com/ui/1.12.1/jquery-ui.js\"></script>" 
        +   "<script language=\"javaScript\" type=\"text/javascript\" src=\"static/suggest.js\"></script> "
);
  
 out.println("</head>");
 out.println("<body style=\"margin-bottom:80%\">");
        
        
        out.println("<div class=\"index\">");
            out.println("<a style=\"margin-left: 80%\" href=\"admin_1.jsp\">Admin</a>");
              out.println("<a style=\"margin-right: -80%\" href=\"search.jsp\">Document Search</a>");
      out.println("<section class=\"index__header\">");
      
       out.println("<div class=\"index__logo\"></div>");
       out.println("<ul class=\"index__nav\">");
       
          out.println(" <li id=\"relLi\"><a id=\"relKeyWord\" href=\"#\" \">Web Search</a></li>");
      
        
       out.println("</ul>");
       out.println("<div class=\"index__search\">");
         out.println("<form class=\"index__form\" action=\"WebSearch\" methode=\"POST\">");
          out.println(" <div><div class=\"ui-widget\"><label for=\"tags\">");
             out.println("<input name=\"webKeyword\" id=\"tags\" type=\"search\" maxlength=\"512\" placeholder=\"Search Keyword\"  class=\"index__query\" autofocus=\"autofocus\" maxlength=\"512\" aria-label=\"Search\"   autocomplete=\"off\">");
               
          out.println("</div></div>");
           out.println("<button class=\"index__button\" value=\"Search\" aria-label=\"Search\" type=\"submit\">");
             out.println("<div class=\"index__ico\"></div>");
           out.println("</button>");
            
            
        
       out.println("</div>");
     out.println("</section><br>");
      out.println("<div>  No. of Search Result per Page <select name=\"noResult\" id=\"noResult\">");
      out.println("</select>  </div>");
        out.println("</form><br>");
             
     
     
     
               //creating ServletContext object  
                    ServletContext context=getServletContext();  
  

               
                String indexPath = context.getInitParameter("web-crawl-dir"); 
                out.println("??????????????????"+indexPath);
		final int maxHitsDisplay = Integer.parseInt(request.getParameter("noResult"));	// Maximum number of result documents to display

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
			
		out.println("\nSearching For: " + request.getParameter("webKeyword") + "\n"+"<br><br>");
			
			// Call the method that executes the search
			executeSearch(in, searcher, q, maxHitsDisplay ,out);

			// Close the IndexReader
			reader.close();
			
		} catch(Exception e) {
			out.println("<font color=\"red\">Error while initializing index reader " + e +"</font><br><a href=\"webCrawl.jsp\">Index Here</a>");
		}
    }

    
    
    public static void executeSearch(BufferedReader in, IndexSearcher searcher, Query query,
			int maxHitsDisplay , PrintWriter out) throws IOException {
		
		Date startDate = new Date();
		
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
                
                out.println("<table id=\"customers\">");
                out.println("<tr><th>Sl No.</th><th>Title</th><th>URL</th><th>Score</th></tr>");
		
		// Iterate over the hits array
		for (int i = 0; i < end; i++) {

			Document doc = searcher.doc(hits[i].doc);

			String title = doc.get("title");
			String url = doc.get("url");
			String text = doc.get("content");
			double score = 	hits[i].score;
                        out.println("<tr>");
			if (url != null) {
				// prints the document rank and title 1. Manchester United.html
				out.println("<td>"+(i+1)+"</td>");
                                
                                out.println("<td>"+ title  +"</td>");

				// prints the path of the document
				out.println("<td><a href=\'"+url+"' target=\"_blank\">"+ url+"</a></td>");

				// prints document score
				out.println("<td>"+ score + "\n"+"</td>");
				
			} else {
				out.println((i+1) + ". " + "Document does not exist");
			}
		}
                
                out.println("</tr></table>");
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
