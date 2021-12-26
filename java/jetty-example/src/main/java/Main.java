/**
 * @author clpsz
 * how to test:
 * curl -X POST -d '{"name": "Alan Turing"}' localhost:8080/test/SaveName
 * curl localhost:8080/test/GetName
 */
public class Main {
    public static void main(String[] args) throws Exception {
        final JettyServer server = new JettyServer(8080);
        server.start();

        // wait 1 second
        Thread.sleep(1000);

        if (!server.isStarted()) {
            throw new Exception("Cannot start jetty server");
        } else {
            System.out.println("jetty server started");
        }
    }
}
