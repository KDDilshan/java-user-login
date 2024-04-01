<%-- 
    Document   : login
    Created on : Mar 31, 2024, 8:55:52 PM
    Author     : HP
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h3 align="center">User Login</h3>
        <form action="user_login" method="post">
            <table border="0" align="center">
                <tbody>
                    <tr>
                        <td>Username:</td>
                        <td><input type="text" name="username" placeholder="Enter username"/></td>
                    </tr>
                    <tr>
                        <td>Password:</td>
                        <td><input type="password" name="password" placeholder="Enter password"></td>
                    </tr>
                    <tr>
                        <td><input type="submit" value="SUBMIT"></td>
                        <td align="center"><input type="reset" value="RESET"></td>
                    </tr>
                </tbody>
            </table>
        </form>
    </body>
</html>
