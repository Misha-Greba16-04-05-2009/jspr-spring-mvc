package ru.netology.servlet;

import ru.netology.controller.PostController;
import ru.netology.exception.NotFoundException;
import ru.netology.repository.PostRepository;
import ru.netology.service.PostService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MainServlet extends HttpServlet {
    private static final String METHOD_GET = "GET";
    private static final String METHOD_POST = "POST";
    private static final String METHOD_DELETE = "DELETE";
    private static final String API_POSTS = "/api/posts";
    private static final String API_POSTS_BY_ID = "/api/posts/\\d+";

    private PostController controller;

    @Override
    public void init() {
        final var repository = new PostRepository();
        final var service = new PostService(repository);
        controller = new PostController(service);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) {
        try {
            final var path = req.getRequestURI();
            final var method = req.getMethod();

            if (METHOD_GET.equals(method) && API_POSTS.equals(path)) {
                controller.all(resp);
                return;
            }
            if (METHOD_GET.equals(method) && path.matches(API_POSTS_BY_ID)) {
                controller.getById(extractId(path), resp);
                return;
            }
            if (METHOD_POST.equals(method) && API_POSTS.equals(path)) {
                controller.save(req.getReader(), resp);
                return;
            }
            if (METHOD_DELETE.equals(method) && path.matches(API_POSTS_BY_ID)) {
                controller.removeById(extractId(path), resp);
                return;
            }

            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } catch (NotFoundException e) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private long extractId(String path) {
        return Long.parseLong(path.substring(path.lastIndexOf('/') + 1));
    }
}
