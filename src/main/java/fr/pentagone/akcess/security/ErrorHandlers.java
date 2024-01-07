package fr.pentagone.akcess.security;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public final class ErrorHandlers {
    private ErrorHandlers(){}

    public static void respondError(HttpServletResponse response, int status, String message) throws IOException {
        response.setStatus(status);
        var writer = response.getWriter();
        writer.write(message);
        writer.flush();
    }
}
