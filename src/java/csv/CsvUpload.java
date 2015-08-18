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
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        ArrayList<Person> added = null;
        ArrayList<Person> parsed = null;
        Part filePart = null;
        InputStream fileStream = null;
        BufferedReader reader = null;
        int parsedCount = 0;
        int addedCount = 0;
        
        Csv2Database db1 = null;
        
        ArrayList<String> lines = new ArrayList<String>();
        
        try
        {
            // get the file from request and create a file reader
            filePart = request.getPart("file");
            fileStream = filePart.getInputStream();
            reader = new BufferedReader(new InputStreamReader(fileStream));
            /*creating instance of Csv2Db allows to use readCsv method
            but also requires assignment of all necessary code to make it NOT empty*/
            
            //TODO make the thing to use added arraylist
            db1 = new Csv2Database();
            parsed = db1.readCsv(fileStream);
            added = db1.addPersons(parsed);
            
            if(reader != null)
            reader.close();
            fileStream.close();
            
            
            parsedCount = parsed.size();
            addedCount = added.size();
            request.setAttribute("parsedCount", parsedCount);
            request.setAttribute("addedCount", addedCount);
            request.setAttribute("added", added);
            
            RequestDispatcher rd = request.getRequestDispatcher("csvResult.jsp");
            rd.forward(request, response);
            
        }
        catch(Exception e)
        {
            out.println("<p>Exception: " + e.toString() + "</p>");
            out.println("<p>Stack Trace: " + e.getStackTrace() + "</p>");
            out.println("<p>Cause: " + e.getCause() + "</p>");
            out.println("<p>parsedCount: " + parsedCount + "</p>");
            out.println("<p>addedCount: " + addedCount + "</p>");
            out.println("<p>Stream: " + filePart.getInputStream() + "</p>");
        }
        
        // print output
        printHeader(out, "CSV Reader", "");

        // print body
        int count = lines.size();
        
        for(int i = 1; i < count; i++)
        {
            out.println("<div>" + (i+1) + added.get(i) + "</div>");
        }
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
