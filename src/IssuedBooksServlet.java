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

@WebServlet("/issued")
public class IssuedBooksServlet extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();

        String sql =
            "SELECT issue_id, student_id, book_id, " +
            "issue_date, return_date " +
            "FROM issued_books " +
            "ORDER BY issue_id DESC";

        try (
            Connection con =
                DatabaseConnection.getConnection();

            PreparedStatement ps =
                con.prepareStatement(sql);

            ResultSet rs =
                ps.executeQuery()
        ) {

            out.print("[");

            boolean first = true;

            while (rs.next()) {

                if (!first) {
                    out.print(",");
                }

                out.print("{");

                out.print(
                    "\"issue_id\":" +
                    rs.getInt("issue_id") +
                    ","
                );

                out.print(
                    "\"student_id\":" +
                    rs.getInt("student_id") +
                    ","
                );

                out.print(
                    "\"book_id\":" +
                    rs.getInt("book_id") +
                    ","
                );

                out.print(
                    "\"issue_date\":\"" +
                    rs.getDate("issue_date") +
                    "\","
                );

                if (rs.getDate("return_date") == null) {

                    out.print("\"return_date\":null");

                } else {

                    out.print(
                        "\"return_date\":\"" +
                        rs.getDate("return_date") +
                        "\""
                    );
                }

                out.print("}");

                first = false;
            }

            out.print("]");

        } catch (Exception e) {

            response.setStatus(500);

            out.print(
                "{\"error\":\"" +
                e.getMessage() +
                "\"}"
            );

            e.printStackTrace();
        }
    }
}