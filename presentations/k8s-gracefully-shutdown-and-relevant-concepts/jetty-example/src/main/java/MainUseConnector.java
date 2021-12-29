import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.StatisticsHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.thread.QueuedThreadPool;
import sun.misc.Signal;
import sun.misc.SignalHandler;

import java.util.concurrent.TimeUnit;

/**
 * @author clpsz
 * how to test:
 * curl -X POST -d '{"name": "Peter Zuo"}' localhost:8080/test/SaveName
 * curl -X GET localhost:8080/test/GetName
 */
public class MainUseConnector {

    private static final String SIGNAL_NAME = "INT";
    public static Server server;
    public static ServerConnector connector;
    public static String name = "Alan Turing";

    public static void main(String[] args) throws Exception {

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler
                .NO_SESSIONS);
        context.setContextPath("/test");
        context.addServlet(new ServletHolder(new HttpGetName()), "/GetName/*");
        context.addServlet(new ServletHolder(new HttpSaveName()), "/SaveName/*");

        // threadPool
        QueuedThreadPool threadPool = new QueuedThreadPool();
        threadPool.setName("Galaxy-connector");
        threadPool.setDaemon(true);
        threadPool.setMaxThreads(200);
        threadPool.setMinThreads(200);

        server = new Server(threadPool);
        connector = new ServerConnector(server, 4, 4);

        connector.setPort(8080);
        connector.setSoLingerTime(-1);
        connector.setReuseAddress(true);
        connector.setAcceptQueueSize(8);

        server.addConnector(connector);

        StatisticsHandler stats = new StatisticsHandler();
        stats.setHandler(context);

        server.setHandler(stats);
        server.setStopTimeout(30 * 1000);

        server.start();


        MySignalHandler mySignalHandler = new MySignalHandler();
        // kill -15
        Signal.handle(new Signal(MainUseConnector.SIGNAL_NAME), mySignalHandler);


        TimeUnit.SECONDS.sleep(1000);
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
            if (name.equals(MainUseConnector.SIGNAL_NAME)) {
                try {
//                    MainUseConnector.server.stop();
                    MainUseConnector.connector.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
