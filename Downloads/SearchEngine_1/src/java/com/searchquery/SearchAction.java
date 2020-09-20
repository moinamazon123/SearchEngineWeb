/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.searchquery;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.File;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Formatter;
import org.apache.lucene.search.highlight.Fragmenter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.search.highlight.SimpleSpanFragmenter;
import org.apache.lucene.search.highlight.TokenSources;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;


@WebServlet(name = "SearchAction", urlPatterns = {"/SearchAction"})
public class SearchAction extends HttpServlet {
    
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
        
        // Embed search.jsp
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
            out.println("<a style=\"margin-left: 80%\" href=\"admin_1.jsp\">Admin</a>");
            out.println(" &nbsp; &nbsp; &nbsp;<a style=\"margin-left: 80%\" href=\"webCrawl.jsp\">Web Crawl</a>");
            
      out.println("<section class=\"index__header\">");
      
       out.println("<div class=\"index__logo\"></div>");
       out.println("<ul class=\"index__nav\">");
         out.println("<li id=\"docLi\"><a id=\"docKeyWord\" href=\"#\">Document</a></li>");
          out.println(" <li id=\"relLi\"><a id=\"relKeyWord\" href=\"#\" \">Relevant Keywords</a></li>");
          out.println(" <li id=\"scLi\"><a id=\"scoreKeyWord\" href=\"#\" \">Sort by Score</a></li>");
        
       out.println("</ul>");
       out.println("<div class=\"index__search\">");
         out.println("<form class=\"index__form\" action=\"SearchAction\" methode=\"POST\">");
          out.println(" <div>");
             out.println("<input name=\"q\" type=\"search\" maxlength=\"512\" placeholder=\"Search Keyword\"  class=\"index__query\" autofocus=\"autofocus\" maxlength=\"512\" aria-label=\"Search\"  autocomplete=\"off\">");
               
          out.println(" </div>");
           out.println("<button class=\"index__button\" value=\"Search\" aria-label=\"Search\" type=\"submit\">");
             out.println("<div class=\"index__ico\"></div>");
           out.println("</button>");
            
            
         out.println("</form>");
       out.println("</div>");
     out.println("</section>");
    
        
         //Get directory reference
        Directory dir = FSDirectory.open(Paths.get((String)ses.getAttribute("indexPath")));
         
        //Index reader - an interface for accessing a point-in-time view of a lucene index
        IndexReader reader = DirectoryReader.open(dir);
         
        //Create lucene searcher. It search over a single IndexReader.
        IndexSearcher searcher = new IndexSearcher(reader);
         
        //analyzer with the default stop words
        Analyzer analyzer = new StandardAnalyzer();
         
        //Query parser to be used for creating TermQuery
        QueryParser qp = new QueryParser("contents", analyzer);
         
        //Create the query
       // Query query = qp.parse("cottage private discovery concluded");
        searchKW =request.getParameter("q").trim();
        Query query = qp.parse(searchKW);
out.println("Entered Key word :"+searchKW+"<br>");

	

        //Search the lucene documents
        TopDocs hits = searcher.search(query, 100);
         
        /** Highlighter Code Start ****/
         
        //Uses HTML &lt;B&gt;&lt;/B&gt; tag to highlight the searched terms
        Formatter formatter = new SimpleHTMLFormatter();
         
        //It scores text fragments by the number of unique query terms found
        //Basically the matching score in layman terms
        QueryScorer scorer = new QueryScorer(query);
         
        //used to markup highlighted terms found in the best sections of a text
        Highlighter highlighter = new Highlighter(formatter, scorer);
         
        //It breaks text up into same-size texts but does not split up spans
        Fragmenter fragmenter = new SimpleSpanFragmenter(scorer, 10);
         
        //breaks text up into same-size fragments with no concerns over spotting sentence boundaries.
        //Fragmenter fragmenter = new SimpleFragmenter(10);
         
        //set fragmenter to highlighter
        highlighter.setTextFragmenter(fragmenter);
         
        String searchHtml = null;
         String title = null;
         
          //Let's print out the path of files which have searched term
        for (ScoreDoc sd : hits.scoreDocs)
        {
            Document d = searcher.doc(sd.doc);
            out.println("<div id=\"scoreResult\">Path : "+ d.get("path") + ", Score : <b>" + sd.score+"</b></div><br>");
        }
         
        //Iterate over found results
        for (int i = 0; i < hits.scoreDocs.length; i++) 
        {
            int docid = hits.scoreDocs[i].doc;
            Document doc = searcher.doc(docid);
             title = doc.get("path");
            File f = new File(title);
             
      //Printing - to which document result belongs
         
             
            //Get stored text from found document
            String text = doc.get("contents");

            //Create token stream
            TokenStream stream = TokenSources.getAnyTokenStream(reader, docid, "contents", analyzer);

            //Get highlighted text fragments
            String[] frags = highlighter.getBestFragments(stream, text, 10);
           // System.out.println(Arrays.toString(frags));
            int occurance =0;
          
            boolean flag =false;
               out.println("<div style=\"display:none\" id=\"relWord\">");
               out.println("<div id=\"path\">Path " + " : " + title+"</div>" );
            for (String frag : frags)
            {
               // System.out.println("=======================");
        
                String kw = frag.replaceAll("<B>","").replaceAll("</B>","");
                  
              out.println("<mark>"+frag+"</mark><br>");
                if(kw.contains(searchKW)) {
                    ++occurance;
                    flag=true;

                }

            }
             out.println("</div>") ;
                   
            searchHtml = flag?"<div id=\"searchResult\" style=\"word-wrap:normal\"><div id=\"path\">Path " + " : " + title+"</div><mark><b>"+searchKW+"</b></mark> Occurred "+occurance+" times in doc."+"</div><br/>":"";
           
            out.println(searchHtml);
            
   
        }
        if(searchHtml == null){
            out.println("<font color=red>No Search Result Found</font>"); 
        }
                out.println("<footer class=\"index__footer\">");
       out.println("<div class=\"index__bottom\">");
       
         out.println("<p class=\"index__copyright\">&copy; 2020 Search Engine</p>");
       out.println("</div>");
     out.println("</footer>");
   out.println("</div>");
 
 out.println("</body>");  
        dir.close();
    
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
             Logger.getLogger(SearchAction.class.getName()).log(Level.SEVERE, null, ex);
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

    private static class WebSearch {

        public WebSearch() {
        }
    }

}
