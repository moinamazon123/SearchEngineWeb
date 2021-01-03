package com.searchengine;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
 
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.LongPoint;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexFormatTooOldException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
 

public class CreateIndex extends javax.servlet.http.HttpServlet {

   

    /**
     *
     * Get a handle on the index.
     *
     */
    private void startup() {
        
    }

   


    /**
     *
     * Close handle to index gracefully.
     *
     */
    private void shutdown() {
       
    }

    

    /**
     *
     * doGet just forwards to doPost.
     *
     */
    public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        doPost(req, resp);
    }

    /**
     *
     * doPost handles the request.
     *
     */
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {

        // Work out search time
        String docsPath = req.getParameter("dataDirPath");
        String indexPath = req.getParameter("indexDirPath");
      
        String destination = "/result.jsp";
       
RequestDispatcher requestDispatcher = req.getRequestDispatcher(destination);
  HttpSession session = req.getSession();
session.setAttribute("indexPath", indexPath);
session.setAttribute("docsPath", docsPath);
        PrintWriter out = resp.getWriter();
       //out.println(docsPath+indexPath);  
        //Output folder
      //  String indexPath = "/Users/srcIndex";

        //Input Path Variable
        final Path docDir = Paths.get(docsPath);
 
        try
        {
            //org.apache.lucene.store.Directory instance
            Directory dir = FSDirectory.open( Paths.get(indexPath) );
             
            //analyzer with the default stop words
            Analyzer analyzer = new StandardAnalyzer();
             
            //IndexWriter Configuration
            IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
            iwc.setOpenMode(OpenMode.CREATE_OR_APPEND);
             
            //IndexWriter writes new index files to the directory
            IndexWriter writer = new IndexWriter(dir, iwc);
          
            List<Document> documents = new ArrayList<>();

// Create field based document

       /** if(req.getParameter("id")!=null && 
                req.getParameter("fieldName1")!=null &&  req.getParameter("fieldValue1")!=null &&
                req.getParameter("fieldName2")!=null &&  req.getParameter("fieldValue2")!=null &&
                req.getParameter("fieldName3")!=null &&  req.getParameter("fieldValue3")!=null &&
                req.getParameter("fieldName4")!=null &&  req.getParameter("fieldValue4")!=null 
                  ) {
            Document document = createContent(Integer.parseInt(req.getParameter("id"))
                    ,req.getParameter("fieldName1"),req.getParameter("fieldValue1"),
                     req.getParameter("fieldName2"),req.getParameter("fieldValue2"),
                     req.getParameter("fieldName3"),req.getParameter("fieldValue3"),
                     req.getParameter("fieldName4"),req.getParameter("fieldValue4"));
		documents.add(document);
		
        }	**/
             
                writer.addDocuments(documents);
            //Its recursive method to iterate all files and directories
            indexDocs(writer, docDir);
            
          
  //out.println("<font color=green>Index Completed Successfully</font>");
            writer.close();
        } 
        
        catch(IndexFormatTooOldException ex) {
           
          session.setAttribute("error", ex.getMessage());
        }
        catch (IOException e) 
        {
           
          session.setAttribute("error", e.getMessage());
        }
          requestDispatcher.forward(req, resp);
    }
     static Document createContent(Integer id , String fn1, String fv1, 
                                               String fn2, String fv2,
                                               String fn3, String fv3,
                                               String fn4, String fv4)
	{
		Document document = new Document();
		document.add(new StringField("id", id.toString() , Field.Store.YES));
		document.add(new TextField('"'+fn1+'"', fv1 , Field.Store.YES));
		document.add(new TextField('"'+fn2+'"', fv2 , Field.Store.YES));
		document.add(new TextField('"'+fn3+'"', fv3 , Field.Store.YES));
		document.add(new TextField('"'+fn4+'"', fv4 , Field.Store.YES));
		
		return document;
	}
    static void indexDocs(final IndexWriter writer, Path path) throws IOException 
    {
        //Directory?
        if (Files.isDirectory(path)) 
        {
            //Iterate directory
            Files.walkFileTree(path, new SimpleFileVisitor<Path>() 
            {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException 
                {
                    try
                    {
                        //Index this file
                        indexDoc(writer, file, attrs.lastModifiedTime().toMillis());
                    } 
                    catch (IOException ioe) 
                    {
                        ioe.printStackTrace();
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        } 
        else
        {
            //Index this file
            indexDoc(writer, path, Files.getLastModifiedTime(path).toMillis());
        }
    }
 
    static void indexDoc(IndexWriter writer, Path file, long lastModified) throws IOException 
    {
        try (InputStream stream = Files.newInputStream(file)) 
        {
            //Create lucene Document
            Document doc = new Document();
             
            doc.add(new StringField("path", file.toString(), Field.Store.YES));
            doc.add(new LongPoint("modified", lastModified));
            doc.add(new TextField("contents", new String(Files.readAllBytes(file)), Store.YES));
             
            //Updates a document by first deleting the document(s) 
            //containing <code>term</code> and then adding the new
            //document.  The delete and then add are atomic as seen
            //by a reader on the same index
            writer.updateDocument(new Term("path", file.toString()), doc);
        }
   
    }
}
