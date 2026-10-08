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

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();

        String username =
                request.getParameter("username");

        String password =
                request.getParameter("password");

        String role =
                request.getParameter("role");

        try {

            // Check empty fields
            if (username == null ||
                password == null ||
                role == null ||
                username.trim().isEmpty() ||
                password.trim().isEmpty() ||
                role.trim().isEmpty()) {

                response.setStatus(400);

                out.print(
                    "{\"success\":false," +
                    "\"message\":\"Please enter all login details.\"}"
                );

                return;
            }

            /*
             * Check username, password AND role
             * from users table.
             */
            String sql =
                "SELECT user_id, username, role " +
                "FROM users " +
                "WHERE username = ? " +
                "AND password = ? " +
                "AND role = ?";

            try (
                Connection con =
                    DatabaseConnection.getConnection();

                PreparedStatement ps =
                    con.prepareStatement(sql)
            ) {

                ps.setString(1, username.trim());
                ps.setString(2, password);
                ps.setString(3, role.trim().toLowerCase());

                try (
                    ResultSet rs =
                        ps.executeQuery()
                ) {

                    if (rs.next()) {

                        String dbUsername =
                            rs.getString("username");

                        String dbRole =
                            rs.getString("role");

                        out.print(
                            "{\"success\":true," +
                            "\"username\":\"" +
                            dbUsername +
                            "\"," +
                            "\"role\":\"" +
                            dbRole +
                            "\"," +
                            "\"message\":\"Login successful!\"}"
                        );

                    } else {

                        response.setStatus(401);

                        out.print(
                            "{\"success\":false," +
                            "\"message\":\"Invalid username, password or role.\"}"
                        );
                    }
                }
            }

        } catch (Exception e) {

            response.setStatus(500);

            out.print(
                "{\"success\":false," +
                "\"message\":\"" +
                e.getMessage()
                    .replace("\"", "'") +
                "\"}"
            );

            e.printStackTrace();
        }
    }
}