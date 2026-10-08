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

@WebServlet("/students")
public class StudentsServlet extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");

        PrintWriter out = response.getWriter();

        String sql = "SELECT * FROM students";

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
                    "\"student_id\":" +
                    rs.getInt("student_id") +
                    ","
                );

                out.print(
                    "\"name\":\"" +
                    rs.getString("name") +
                    "\","
                );

                out.print(
                    "\"email\":\"" +
                    rs.getString("email") +
                    "\","
                );

                out.print(
                    "\"phone\":\"" +
                    rs.getString("phone") +
                    "\""
                );

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