/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.searchquery;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.lucene.index.IndexWriter;
import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


@WebServlet(name = "CrawlerServlet",   urlPatterns = {"/CrawlerServlet"})
public class CrawlerServlet extends HttpServlet {
    
    
       private int crawlDepth;
	private BufferedWriter bfWriter;
	HashSet<String> indexedPages;

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
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
         
              
        String seedUrl = request.getParameter("site");
         String indexPath = request.getParameter("crawlIndex");
        try (PrintWriter out = response.getWriter()) {
            
            
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
                           "<link href=\"static/index.css\" rel=\"stylesheet\" type=\"text/css\">" );
  
                out.println("</head>");
                out.println("<body style=\"margin-bottom:80%\">");
        
        
                out.println("<div class=\"index\">");
            
            
                   
           this.crawlDepth = Integer.parseInt(request.getParameter("depth"));
              
		
		IndexFiles indexer = new IndexFiles();
		IndexWriter writer = indexer.getIndexer(indexPath);
		
		// writer will be null when an index is already present in the index path
		if (writer == null) {
			out.println("Using Already Available Index...");
                        out.println("<a  href=\"webSearch.jsp\">Search Here </a>");
			return;
		}
		
            // create the log file
            bfWriter = new BufferedWriter(new FileWriter(request.getParameter("crawlIndex") + "/pages.txt"));
		
		// initialize a HashSet to store pages that get indexed, so that
		// we can check if a page is already indexed before indexing.
		// HashSet is used because it allows for fast searching O(1).
		this.indexedPages = new HashSet<String>();
		
		// start the crawl procedure
		this.crawl(UrlNormalizer.normalize(seedUrl), 0, writer ,out);
		 out.println("<a  href=\"webSearch.jsp\">Search Here </a>");

		// close index writer and bufferedwriter after crawling is done 
		try {
			writer.close();
			bfWriter.close();
		} catch (IOException e) {
			out.println("Exception while closing index writer or buffered writer. " + e);
		}	 
            
         
           
        }
    }
    
    
    private void crawl(String url, int depth, IndexWriter writer , PrintWriter out) {
		
		// if url is null after normalization
		if (url == null) {
			return;
		}
				
		// parse the document using jsoup
		org.jsoup.nodes.Document doc = null;	
		try {
			Connection con = Jsoup.connect(url)
					.userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")
					.ignoreHttpErrors(true)
					.timeout(20000);
			Connection.Response response = con.execute();
			if (response.statusCode() == 200) {
	            doc = con.get();
			}
		} catch (HttpStatusException e) {
			out.println("URL could not be parsed. " + e);
		}
		catch (Exception e) {
			out.println("Jsoup exception while connecting to url: " + url + ". " + e);
		}

		if (doc != null) {
			
			// index the current doc
			out.println("Adding " + url);
			IndexFiles.indexDoc(writer, doc);
			
			// add the url to the indexedPages HashSet
			this.indexedPages.add(url);
			
			// write the current page to the log file
			String line = url + "\t" + depth;
			try {
				bfWriter.write(line);
				bfWriter.newLine();
				bfWriter.flush();
			} catch (IOException e) {
				out.println("Error while writing to pages.txt. " + e);
			}	
			
			
			// check if crawl depth has been reached
			if (depth < this.crawlDepth) {
				
				// extract links from the url and recurse
				Elements links = doc.select("a[href]");
				
				for (Element link : links) {
					String normalizedUrl = UrlNormalizer.normalize(link.absUrl("href").toString());					
					// recurse on the url if page is not already indexed
					if (normalizedUrl != null && !this.indexedPages.contains(normalizedUrl)) {
						crawl(normalizedUrl, depth+1, writer , out);
					}
				}
			
			}
		}
                
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
            throws ServletException, IOException {
        processRequest(request, response);
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
            throws ServletException, IOException {
        processRequest(request, response);
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
