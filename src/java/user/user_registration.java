package user;

import at.favre.lib.crypto.bcrypt.BCrypt;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class user_registration extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out=response.getWriter();
        
        String name=request.getParameter("username");
        String pass=request.getParameter("password");
        String email=request.getParameter("email");
        int age=Integer.parseInt(request.getParameter("age"));
        String gender=request.getParameter("gender");
       
        if(!gender.equals( "male") && !gender.equals("female")){
            System.out.println("enter only male or feamle to gender");
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,"Invalid gender provided");
        }
 
        String url="jdbc:mysql://localhost:3306/javamysql?autoReconnect=true&useSSL=false";
        String username="root";
        String password="kavindu123/*-";
       
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn=DriverManager.getConnection(url, username, password);
            System.out.println("connected to database");
                       
            String hashedPassword = BCrypt.withDefaults().hashToString(12, pass.toCharArray());
            String sql="INSERT INTO user(username,password,age,email,gender) VALUES(?,?,?,?,?)";
            
            PreparedStatement ps=conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, hashedPassword);
            ps.setInt(3, age);
            ps.setString(4,email);
            ps.setString(5, gender);
            ps.executeUpdate();
            
            RequestDispatcher rd=request.getRequestDispatcher("newjsp.jsp");
            rd.forward(request, response);
            
        }catch(Exception e){
            System.out.println("Error ::"+e.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,"Unexpected error occoured");
        }  
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
