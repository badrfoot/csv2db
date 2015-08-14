package csv;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

@WebServlet(name = "CsvUpload", urlPatterns = {"/CsvUpload"})
@MultipartConfig
public class CsvUpload extends HttpServlet {

    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        //TODO: make it working
        
        response.setContentType("text/html");
        
        PrintWriter out = response.getWriter();
        ArrayList<Person> added = null;
        ArrayList<Person> parsed = null;
        Part filePart = null;
        InputStream fileStream = null;
        int parsedCount = 0;
        int addedCount = 0;
        Csv2Database db1 = null;
        boolean connected = false;
        try
        {
            // get the file from request and create a file reader
            filePart = request.getPart("file");
            fileStream = filePart.getInputStream();
            
            /*creating instance of Csv2Db allows to use readCsv method
            but also requires assignment of all necessary code to make it NOT empty*/
            
            //TODO make the thing to use added arraylist
            db1 = new Csv2Database();
            //db1.jdbc.connect("jdbc:mysql://localhost:3306/ejd", "root", "not4you");
            connected = db1.jdbc.connect("jdbc:mysql://localhost:3306/ejd", "root", "not4you");
            parsed = db1.readCsv(fileStream);
            
            fileStream.close();
            
            added = db1.addPersons(parsed);
            
            parsedCount = parsed.size();
            addedCount = added.size();
            request.setAttribute("parsedCount", parsedCount);
            request.setAttribute("addedCount", addedCount);
            request.setAttribute("added", added);
            
            RequestDispatcher rd = request.getRequestDispatcher("csvResult");
            rd.forward(request, response);
            
        }
        catch(Exception e)
        {
            out.println("<p>Exception: " + e.toString() + "</p>");
            out.println("<p>Stack Trace: " + e.getStackTrace() + "</p>");
            out.println("<p>Cause: " + e.getCause() + "</p>");
            out.println("<p>Connected: " + connected + "</p>");
            out.println("<p>parsedCount: " + parsedCount + "</p>");
            out.println("<p>addedCount: " + addedCount + "</p>");
            out.println("<p>Stream: " + filePart.getInputStream() + "</p>");
        }
        
        // print output
        printHeader(out, "CSV Reader", "");

        // print body
        int count = parsed.size();
        System.out.println(count);
        for(int i = 1; i < count; i++)
        {
            out.println("<div><span style='color:red;'>" + (i+1) + ":</span> " +
                        added.get(i) + "</div>");
        }
            out.println("<div>" + addedCount + "</div>");
            out.println("<div>" + parsedCount + "</div>");
        printFooter(out);
            
        }
    
    ///////////////////////////////////////////////////////////////////////////
    // helper methods to print HTML content
    ///////////////////////////////////////////////////////////////////////////
    private void printHeader(PrintWriter out, String title, String css)
    {
        String header = "<!DOCTYPE html>\n" +
                        "<html lang=\"en\">\n" +
                        "<head>\n" +
                        "<meta charset=\"utf-8\">\n" +
                        "<title>" +
                        title +
                        "</title>\n" +
                        css +
                        "</head>\n" +
                        "<body>\n";
        out.println(header);
    }

    private void printFooter(PrintWriter out)
    {
        out.println("\n</body>\n</html>");
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    
    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
