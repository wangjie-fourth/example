import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class WebServer {
    public static void main(String[] args) throws IOException, InterruptedException {
        ServerSocket ss = new ServerSocket();
        ss.bind(new InetSocketAddress("localhost", 8080));

        while (true) {
            Socket socket = ss.accept();

            new MyServlet();
//            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//            String s = reader.readLine();
//            System.out.println("s = " + s);
//            Thread.sleep(10000);
//            socket.getOutputStream().write("HTTP/1.1 200 OK\r\nContent-Type: text/plain;\r\n\r\nHello".getBytes());
            socket.getOutputStream().flush();
        }
    }
    // 1、单纯靠上面这些技术，我们要获取请求参数会很麻烦
    // 2、上面代码只能读一次，读完就结束？如何让他永远在线
    // 3、上面代码是单线程的，如果这个线程正在做很耗时的任务，比如从数据库读数据；又有其他请求过来该怎么办呢？没法处理
}
