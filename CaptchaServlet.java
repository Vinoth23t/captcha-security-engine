package com.example.captcha;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

public class CaptchaServlet extends HttpServlet {

    private DefaultKaptcha captchaProducer;

    @Override
    public void init() throws ServletException {
        super.init();
        Properties properties = new Properties();
        properties.setProperty("kaptcha.image.width", "200");
        properties.setProperty("kaptcha.image.height", "50");
        properties.setProperty("kaptcha.textproducer.char.string", "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789");
        Config config = new Config(properties);
        captchaProducer = new DefaultKaptcha();
        captchaProducer.setConfig(config);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Set response headers
        response.setContentType("image/png");
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);

        // Generate CAPTCHA image
        String captchaText = captchaProducer.createText();
        BufferedImage captchaImage = captchaProducer.createImage(captchaText);

        // Store CAPTCHA text in session
        request.getSession().setAttribute("captchaText", captchaText);

        // Store CAPTCHA text in database
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/captcha", "root", "password");
             PreparedStatement statement = connection.prepareStatement("INSERT INTO captcha (captcha_text) VALUES (?)")) {
            statement.setString(1, captchaText);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new ServletException("Failed to store CAPTCHA text in database", e);
        }

        // Write CAPTCHA image to response output stream
        try (OutputStream outputStream = response.getOutputStream
