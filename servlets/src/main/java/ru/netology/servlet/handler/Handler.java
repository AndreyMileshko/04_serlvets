package ru.netology.servlet.handler;

import ru.netology.controller.PostController;
import ru.netology.exception.NotFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static ru.netology.servlet.handler.Method.*;

public class Handler {
    public static void serviceHandler(HttpServletRequest req,
                                      HttpServletResponse resp,
                                      PostController controller) {
        try {
            final var path = req.getRequestURI();
            final var method = req.getMethod();
            if (method.equals(GET.getMethod()) && path.equals("/api/posts")) {
                controller.all(resp);
                return;
            }
            if (method.equals(GET.getMethod()) && path.matches("/api/posts/\\d+")) {
                final var id = parseID(path);
                controller.getById(id, resp);
                return;
            }
            if (method.equals(POST.getMethod()) && path.equals("/api/posts")) {
                controller.save(req.getReader(), resp);
                return;
            }
            if (method.equals(DELETE.getMethod()) && path.matches("/api/posts/\\d+")) {
                final var id = parseID(path);
                controller.removeById(id, resp);
                return;
            }
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);

        } catch (NotFoundException e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private static long parseID(String path) {
        return Long.parseLong(path.substring(path.lastIndexOf("/") + 1));
    }
}

