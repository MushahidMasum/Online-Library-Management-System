import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/issue-book")
public class IssueBookServlet extends HttpServlet {

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();

        String studentIdText = request.getParameter("studentId");
        String bookIdText = request.getParameter("bookId");

        try {

            int studentId = Integer.parseInt(studentIdText);
            int bookId = Integer.parseInt(bookIdText);

            try (
                Connection con = DatabaseConnection.getConnection();

                CallableStatement cs =
                    con.prepareCall("{CALL issue_book(?, ?)}")
            ) {

                cs.setInt(1, studentId);
                cs.setInt(2, bookId);

                boolean hasResult = cs.execute();

                if (hasResult) {

                    try (ResultSet rs = cs.getResultSet()) {

                        if (rs.next()) {

                            String message =
                                rs.getString(1);

                            out.print(
                                "{\"success\":true,\"message\":\"" +
                                message.replace("\"", "'") +
                                "\"}"
                            );

                        } else {

                            out.print(
                                "{\"success\":true,\"message\":\"Book issued successfully!\"}"
                            );
                        }
                    }

                } else {

                    out.print(
                        "{\"success\":true,\"message\":\"Book issued successfully!\"}"
                    );
                }

            }

        } catch (NumberFormatException e) {

            response.setStatus(400);

            out.print(
                "{\"success\":false,\"message\":\"Student ID and Book ID must be valid numbers.\"}"
            );

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