package ru.netology.servlet.handler;

import ru.netology.controller.PostController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static ru.netology.servlet.handler.Method.*;

public class Handler {
    public void serviceHandler(HttpServletRequest req, HttpServletResponse resp, PostController controller) {
        try {
            final var path = req.getRequestURI();
            final var method = req.getMethod();
            // primitive routing
            if (method.equals(GET.getMethod()) && path.equals("/api/posts")) {
                controller.all(resp);
                return;
            }
            if (method.equals(GET.getMethod()) && path.matches("/api/posts/\\d+")) {
                // easy way
                final var id = Long.parseLong(path.substring(path.lastIndexOf("/") + 1));
                System.out.println(id);
                controller.getById(id, resp);
                return;
            }
            if (method.equals(POST.getMethod()) && path.equals("/api/posts")) {
                controller.save(req.getReader(), resp);
                return;
            }
            if (method.equals(DELETE.getMethod()) && path.matches("/api/posts/\\d+")) {
                // easy way
                final var id = Long.parseLong(path.substring(path.lastIndexOf("/") + 1));
                controller.removeById(id, resp);
                return;
            }
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}

