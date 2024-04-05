import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class authentication implements Filter {
    
    private static final boolean debug = true;
    private FilterConfig filterConfig = null;
    
    public authentication() {
    }    
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
         HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Check if user is logged in (you need to implement this logic)
        boolean isLoggedIn = checkIfUserIsLoggedIn(httpRequest);

        if (isLoggedIn) {
            // User is logged in, continue with the request
            chain.doFilter(request, response);
        } else {
            // User is not logged in, redirect to login page or show an error message
            httpResponse.sendRedirect("login.jsp");
        }
        
    }


    public FilterConfig getFilterConfig() {
        return (this.filterConfig);
    }


    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }


    @Override
    public void destroy() {        
    }

    @Override
    public void init(FilterConfig filterConfig) {        
        this.filterConfig = filterConfig;
        if (filterConfig != null) {
            if (debug) {                
                log("authentication:Initializing filter");
            }
        }
    }


    @Override
    public String toString() {
        if (filterConfig == null) {
            return ("authentication()");
        }
        StringBuilder sb = new StringBuilder("authentication(");
        sb.append(filterConfig);
        sb.append(")");
        return (sb.toString());
    }
    
    
    public static String getStackTrace(Throwable t) {
        String stackTrace = null;
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            pw.close();
            sw.close();
            stackTrace = sw.getBuffer().toString();
        } catch (IOException ex) {
        }
        return stackTrace;
    }
    
    public void log(String msg) {
        filterConfig.getServletContext().log(msg);        
    }

    private boolean checkIfUserIsLoggedIn(HttpServletRequest request) {
               return request.getSession().getAttribute("user") != null;

    }
    
}
