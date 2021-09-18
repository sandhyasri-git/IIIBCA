
package p1;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 *
 * @author Sandhya.Angara
 */
@MultipartConfig(maxFileSize = 16177215)
public class addproduct2 extends HttpServlet {

  public int uploadFile(String name, int quantity,int price,String catagory,String description,
                       InputStream file) throws SQLException, ClassNotFoundException 
    {
        String sql
                = "INSERT INTO  ADDPRODUCT "
                + "(name,quantity,price,category,description, "
                + "photo) values (?,?,?,?,?,?)";
        int row = 0;
        Class.forName("org.apache.derby.jdbc.ClientDriver");
        Connection connection
                = DriverManager.getConnection("jdbc:derby://localhost:1527/sample","app","app");
        System.out.println("photo "+file);
        PreparedStatement preparedStatement;
        try {
            preparedStatement
                    = connection.prepareStatement(sql);

            preparedStatement
                    .setString(1, name);
          preparedStatement.setInt(2, quantity);
           preparedStatement.setInt(3, price);
          preparedStatement
                    .setString(4, catagory);
         
           preparedStatement
                    .setString(5, description);

            if (file != null) {

                // Fetches the input stream
                // of the upload file for
                // the blob column
                preparedStatement.setBlob(6, file);
            }

            // Sends the statement to
            // the database server
            row = preparedStatement
                    .executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return row;
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, ClassNotFoundException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) 
         {
            // Getting the parametes from web page
         String name=request.getParameter("name");
       int quantity=Integer.parseInt(request.getParameter("quantity"));
           int price=Integer.parseInt(request.getParameter("price"));
            String catagory=request.getParameter("catagory");
            String description=request.getParameter("description");

                        // Input stream of the upload file
            InputStream inputStream = null;

            String message = null;

        // Obtains the upload file
            // part in this multipart request
            Part filePart
                    = request.getPart("photo");
                       if (filePart != null) {

            // Prints out some information
                // for debugging
                System.out.println(
                        filePart.getName());
               
                System.out.println(
                        filePart.getSize());
                System.out.println(
                        filePart.getContentType());

                // Obtains input stream of the upload file
                inputStream
                        = filePart.getInputStream();
            }

        // Sends the statement to the
            // database server
            int row
                    = uploadFile(name,quantity,price,catagory,description,
                               inputStream);
            if (row > 0) {
                message
                        = "File uploaded and "
                        + "saved into database"; 
            }
            System.out.println(message);
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
      try {
          processRequest(request, response);
      } catch (SQLException ex) {
          Logger.getLogger(addproduct2.class.getName()).log(Level.SEVERE, null, ex);
      } catch (ClassNotFoundException ex) {
          Logger.getLogger(addproduct2.class.getName()).log(Level.SEVERE, null, ex);
      }
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
      try {
          processRequest(request, response);
      } catch (SQLException ex) {
          Logger.getLogger(addproduct2.class.getName()).log(Level.SEVERE, null, ex);
      } catch (ClassNotFoundException ex) {
          Logger.getLogger(addproduct2.class.getName()).log(Level.SEVERE, null, ex);
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
