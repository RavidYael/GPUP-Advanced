package UserManagement.UsersManagementServer.servlets;

import utils.ServletUtils;
import utils.SessionUtils;

import java.io.IOException;

import UserManagement.userManager;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


//TODO TO BE HONEST I HAVE NO IDEA IF THAT NECESSARY
@WebServlet(name = "LogoutServlet", urlPatterns = {"/logout"})
public class LogoutServlet extends HttpServlet {


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String usernameFromSession = SessionUtils.getUsername(request);
        userManager userManager = ServletUtils.getUserManager(getServletContext());

        if (usernameFromSession != null) {
            System.out.println("Clearing session for " + usernameFromSession);
            userManager.removeUser(usernameFromSession);
            SessionUtils.clearSession(request);

            // used mainly for the web version. irrelevant in the desktop client version
            response.sendRedirect(request.getContextPath() + "/index.html");
        }
    }

}