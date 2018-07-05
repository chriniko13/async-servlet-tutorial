package com.chriniko.example.controller;

import com.chriniko.example.listener.AsyncWorkerListener;
import com.chriniko.example.service.AsyncRequestProcessor;

import javax.servlet.AsyncContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.ThreadPoolExecutor;

@WebServlet(
        urlPatterns = "/async-worker",
        asyncSupported = true
)
public class AsyncWorkerServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {

        req.setAttribute("org.apache.catalina.ASYNC_SUPPORTED", true);

        final AsyncContext asyncContext = req.startAsync();
        asyncContext.setTimeout(6000);
        asyncContext.addListener(new AsyncWorkerListener());


        final ThreadPoolExecutor workersPool = (ThreadPoolExecutor) req.getServletContext().getAttribute("workersPool");
        workersPool.submit(new AsyncRequestProcessor(asyncContext));

    }

}
