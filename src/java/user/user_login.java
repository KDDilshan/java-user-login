package user;

import at.favre.lib.crypto.bcrypt.BCrypt;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class user_login extends HttpServlet {

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
        
        
        if(pass == null && name == null){
            out.println("<h1> the name and password feilds are empty </h1>");
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,"enter username and password");
        }
        
        String url="jdbc:mysql://localhost:3306/javamysql?autoReconnect=true&useSSL=false";
        String username="root";
        String password="kavindu123/*-";
        
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn=DriverManager.getConnection(url, username, password);
            System.out.println("Databse is connected");
            
            
            String sql="Select password from user where username=?";
            PreparedStatement ps=conn.prepareStatement(sql);
            ps.setString(1, name);
            ResultSet rs= ps.executeQuery();
            
            if(rs.next()==true){
                String passwords=rs.getString("password");
                boolean cheakPass=BCrypt.verifyer().verify(pass.toCharArray(), passwords).verified;
                if(cheakPass){
                     Cookie usercookie=new Cookie("username",name);
                     usercookie.setMaxAge(3000);
                     
                     usercookie.setSecure(true);
                     usercookie.setHttpOnly(true);
                     response.addCookie(usercookie);
                     out.println("<h1> you logged in. </h1>");
                }else{
                     out.println("<h1> Incorect Password. </h1>");
                }
            }else {
                out.println("<h1>No user found with the given username.</h1>");
            }
            
        }catch(Exception e){
            System.out.println("Error is :: "+e.getMessage());
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,"Invaild creadentials");
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
