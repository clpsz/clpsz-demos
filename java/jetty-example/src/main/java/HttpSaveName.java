import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @author clpsz
 */
public class HttpSaveName extends HttpServlet {
    public HttpSaveName() {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        try {
            System.out.println(request.getProtocol());

            String requestData = request.getReader().lines().collect(Collectors.joining());
            JsonObject jsonObject = new JsonParser().parse(requestData).getAsJsonObject();
            String name = jsonObject.get("name").getAsString();

            Main.name = name;

            response.setStatus(HttpServletResponse.SC_OK);

        } catch (Exception ex) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

        } finally {
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().println("");
            response.getWriter().close();
        }
    }
}
