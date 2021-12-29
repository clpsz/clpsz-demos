import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.StatisticsHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import sun.misc.Signal;
import sun.misc.SignalHandler;

/**
 * @author clpsz
 * how to test:
 * curl -X POST -d '{"name": "Peter Zuo"}' localhost:8080/test/SaveName
 * curl -X GET localhost:8080/test/GetName
 */
public class MainNormal {

    private static final String SIGNAL_NAME = "INT";
    public static Server server;
    public static String name = "Alan Turing";

    public static void main(String[] args) throws Exception {

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler
                .NO_SESSIONS);
        context.setContextPath("/test");
        context.addServlet(new ServletHolder(new HttpGetName()), "/GetName/*");
        context.addServlet(new ServletHolder(new HttpSaveName()), "/SaveName/*");

        server = new Server(8080);
        StatisticsHandler stats = new StatisticsHandler();
        server.setHandler(stats);
        stats.setHandler(context);


        server.setStopTimeout(30 * 1000);

        server.start();

        MySignalHandler mySignalHandler = new MySignalHandler();
        // kill -15
        Signal.handle(new Signal(MainNormal.SIGNAL_NAME), mySignalHandler);
        System.out.println("started");
    }

    private static @SuppressWarnings("restriction")
    class MySignalHandler implements SignalHandler {

        @Override
        public void handle(Signal signal) {

            // 信号量名称
            String name = signal.getName();
            // 信号量数值
            int number = signal.getNumber();

            // 当前进程名
            String currentThreadName = Thread.currentThread().getName();

            System.out.println("[Thread:" + currentThreadName + "] received signal: " + name + " == kill -" + number);
            if (name.equals(MainNormal.SIGNAL_NAME)) {
                try {
                    MainNormal.server.stop();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
