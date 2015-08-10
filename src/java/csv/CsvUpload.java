package csv;

import java.io.*;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

@WebServlet(name = "CsvUpload", urlPatterns = {"/CsvUpload"})
@MultipartConfig
public class CsvUpload extends HttpServlet {

    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        //TODO: move all code to Csv2Database.readCsv and make it working
        //Csv2Database.readCsv(new FileInputStream io);
        
        response.setContentType("text/html");
        
        PrintWriter out = response.getWriter();
        
        Part filePart = null;
        InputStream fileStream = null;
        BufferedReader reader = null;

        // container for records
        ArrayList<String> lines = new ArrayList<String>();
        
        try
        {
            // get the file from request and create a file reader
            filePart = request.getPart("file");
            fileStream = filePart.getInputStream();
            reader = new BufferedReader(new InputStreamReader(fileStream));

            // parse each line and store to an array
            String line;
            while((line = reader.readLine()) != null)
            {
                lines.add(line);
            }
        }
        catch(Exception e)
        {
            out.println("<p>" + e.getMessage() + "</p>");
        }
        
        // done with file stream, clean up
        if(reader != null)      reader.close();
        if(fileStream != null)  fileStream.close();

        // print output
        printHeader(out, "CSV Reader", "");

        // print body
        int count = lines.size();
        for(int i = 0; i < count; ++i)
        {
            out.println("<div><span style='color:red;'>" + (i+1) + ":</span> " +
                        lines.get(i) + "</div>");
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
