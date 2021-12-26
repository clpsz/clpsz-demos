import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.StatisticsHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;


/**
 * @author clpsz
 */
public class JettyServer extends Server {
    public static volatile String name = "";

    public JettyServer(int port) {
        super(port);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler
                .NO_SESSIONS);
        context.setContextPath("/test");
        context.addServlet(new ServletHolder(new HttpGet_GetName()), "/GetName/*");
        context.addServlet(new ServletHolder(new HttpPost_SaveName()), "/SaveName/*");


        StatisticsHandler stats = new StatisticsHandler();
        this.setHandler(stats);
        this.setStopTimeout(30);


        this.setHandler(context);
        this.setStopAtShutdown(true);
    }
}