package com.example.captcha;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

public class SubmitServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get user input
        String userInput = request.getParameter("captcha");

        // Get CAPTCHA text from session and database
        String captchaText = (String) request.getSession().getAttribute("captchaText");
        String storedCaptchaText = null;
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/captcha", "root", "password");
             PreparedStatement statement = connection.prepareStatement("SELECT captcha_text FROM captcha ORDER BY creation_time DESC LIMIT 1")) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                storedCaptchaText = resultSet.getString(1);
            }
        } catch (SQLException e) {
            throw new ServletException("Failed to retrieve CAPTCHA text from database", e);
        }

        // Verify CAPTCHA
        if (captchaText.equals(userInput) && captchaText.equals(storedCaptchaText)) {
            // CAPTCHA verification succeeded
            response.getWriter().println("CAPTCHA verification succeeded");
        } else {
            // CAPTCHA verification failed
            response.getWriter().println("CAPTCHA verification failed");
        }
    }

}
