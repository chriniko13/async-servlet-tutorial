package com.chriniko.example.controller;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HelloServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String alive = this.getInitParameter("alive");

        if ("true".equalsIgnoreCase(alive)) {
            ServletOutputStream out = resp.getOutputStream();
            out.write("Hello World!".getBytes());
            out.flush();
            out.close();
        } else {
            resp.getWriter().write("Oooops, i am unhealthy!");
        }
    }

}
