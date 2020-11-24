package Toster.servlets;

import Toster.entity.User;
import Toster.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
//Реализовать сервлет для логина пользователя, обрабатывающего запрос GET /user/login. При
// успешном логине данные о пользователе должны сохраняться в сессии.

//@WebServlet(urlPatterns = {"/user/login"})
//@Component
public class LogServlet extends HttpServlet {
    @Autowired
    UserService user;
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String login = req.getParameter("login");
        String name = req.getParameter("name");
        String pass = req.getParameter("password");
        try {
            if (login != null && user.authorization(new User(name, login, pass))) {
                session.setAttribute("login", login);
                session.setAttribute("name", name);
                session.setAttribute("password", pass);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
