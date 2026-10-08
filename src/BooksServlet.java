import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/books")
public class BooksServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");

        PrintWriter out = response.getWriter();

        String sql = "SELECT * FROM books";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            out.print("[");

            boolean first = true;

            while (rs.next()) {

                if (!first) {
                    out.print(",");
                }

                out.print("{");

                out.print("\"book_id\":" + rs.getInt("book_id") + ",");
                out.print("\"title\":\"" + rs.getString("title") + "\",");
                out.print("\"author\":\"" + rs.getString("author") + "\",");
                out.print("\"category\":\"" + rs.getString("category") + "\",");
                out.print("\"quantity\":" + rs.getInt("quantity") + ",");
                out.print("\"available\":" + rs.getInt("available"));

                out.print("}");

                first = false;
            }

            out.print("]");

        } catch (Exception e) {

            response.setStatus(500);

            out.print("{\"error\":\"" + e.getMessage() + "\"}");

            e.printStackTrace();
        }
    }
}