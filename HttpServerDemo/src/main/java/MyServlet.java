import javax.servlet.*;
import java.io.IOException;

// 将这个类的字节码放在Servlet容器即可，
// Tomcat的web.xml就是Servlet描述文件，当
public class MyServlet implements Servlet {
    public void init(ServletConfig config) throws ServletException {

    }

    public ServletConfig getServletConfig() {
        return null;
    }

    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        System.out.println("自定义的Servlet已执行....");
        res.getOutputStream().write("HTTP/1.1 200 OK\r\nContent-Type: text/plain;\r\n\r\nHello".getBytes());
        res.getOutputStream().flush();
        res.flushBuffer();

    }

    public String getServletInfo() {
        return null;
    }

    public void destroy() {

    }
}
