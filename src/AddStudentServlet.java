import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/add-student")
public class AddStudentServlet extends HttpServlet {

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");

        try {

            if (name == null || name.trim().isEmpty()
                    || email == null || email.trim().isEmpty()
                    || phone == null || phone.trim().isEmpty()) {

                response.setStatus(400);

                out.print(
                    "{\"success\":false,\"message\":\"Please fill all details.\"}"
                );

                return;
            }

            String sql =
                "INSERT INTO students (name, email, phone) " +
                "VALUES (?, ?, ?)";

            try (
                Connection con = DatabaseConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
            ) {

                ps.setString(1, name.trim());
                ps.setString(2, email.trim());
                ps.setString(3, phone.trim());

                int rows = ps.executeUpdate();

                if (rows > 0) {

                    out.print(
                        "{\"success\":true,\"message\":\"Student added successfully!\"}"
                    );

                } else {

                    response.setStatus(500);

                    out.print(
                        "{\"success\":false,\"message\":\"Student could not be added.\"}"
                    );
                }
            }

        } catch (Exception e) {

            response.setStatus(500);

            out.print(
                "{\"success\":false,\"message\":\"" +
                e.getMessage().replace("\"", "'") +
                "\"}"
            );

            e.printStackTrace();
        }
    }
}