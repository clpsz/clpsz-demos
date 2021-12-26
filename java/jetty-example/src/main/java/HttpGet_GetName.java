import com.google.gson.Gson;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@SuppressWarnings("serial")
public class HttpGet_GetName extends HttpServlet {
    public HttpGet_GetName() {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String output = "";
        Map<String, String> ret = new HashMap<>();

        try {
            ret.put("name", JettyServer.name);
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
