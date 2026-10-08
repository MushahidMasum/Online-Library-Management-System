import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/add-book")
public class AddBookServlet extends HttpServlet {

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();

        String title =
                request.getParameter("title");

        String author =
                request.getParameter("author");

        String category =
                request.getParameter("category");

        String quantityText =
                request.getParameter("quantity");

        String availableText =
                request.getParameter("available");

        try {

            int quantity =
                    Integer.parseInt(quantityText);

            int available =
                    Integer.parseInt(availableText);


            if (title == null || title.trim().isEmpty()
                    || author == null || author.trim().isEmpty()
                    || category == null || category.trim().isEmpty()) {

                response.setStatus(400);

                out.print(
                    "{\"success\":false," +
                    "\"message\":\"Please fill all required fields.\"}"
                );

                return;
            }


            if (quantity <= 0) {

                response.setStatus(400);

                out.print(
                    "{\"success\":false," +
                    "\"message\":\"Quantity must be greater than 0.\"}"
                );

                return;
            }


            if (available < 0 || available > quantity) {

                response.setStatus(400);

                out.print(
                    "{\"success\":false," +
                    "\"message\":\"Available quantity is invalid.\"}"
                );

                return;
            }


            String sql =
                    "INSERT INTO books " +
                    "(title, author, category, quantity, available) " +
                    "VALUES (?, ?, ?, ?, ?)";


            try (
                Connection con =
                        DatabaseConnection.getConnection();

                PreparedStatement ps =
                        con.prepareStatement(sql)
            ) {

                ps.setString(1, title.trim());
                ps.setString(2, author.trim());
                ps.setString(3, category.trim());
                ps.setInt(4, quantity);
                ps.setInt(5, available);

                int rows =
                        ps.executeUpdate();


                if (rows > 0) {

                    out.print(
                        "{\"success\":true," +
                        "\"message\":\"Book added successfully!\"}"
                    );

                } else {

                    response.setStatus(500);

                    out.print(
                        "{\"success\":false," +
                        "\"message\":\"Book could not be added.\"}"
                    );
                }
            }

        } catch (NumberFormatException e) {

            response.setStatus(400);

            out.print(
                "{\"success\":false," +
                "\"message\":\"Quantity must be a valid number.\"}"
            );

        } catch (Exception e) {

            response.setStatus(500);

            out.print(
                "{\"success\":false," +
                "\"message\":\"" +
                e.getMessage().replace("\"", "'") +
                "\"}"
            );

            e.printStackTrace();
        }
    }
}