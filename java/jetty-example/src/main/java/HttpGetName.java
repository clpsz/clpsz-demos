import com.google.gson.Gson;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @author clpsz
 */
public class HttpGetName extends HttpServlet {
    public HttpGetName() {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String output = "";
        Map<String, String> ret = new HashMap<>();

        try {
            TimeUnit.SECONDS.sleep(10);
            ret.put("name", Main.name);
            Gson gson = new Gson();

            output = gson.toJson(ret);
            response.setStatus(HttpServletResponse.SC_OK);

        } catch (Exception ex) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

        } finally {
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().println(output);
            response.getWriter().close();
        }
    }
}
