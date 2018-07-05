package com.chriniko.example.controller.pipeline;

import javax.servlet.AsyncContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "AProcessorServlet", urlPatterns = "/pipeline/a")
public class AProcessorServlet extends HttpServlet implements ProcessorServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

        final AsyncContext asyncContext = produce(request);

        processAsync(
                () -> {

                    sleep(3);

                    increaseStagesCounter(this.getServletContext());

                    try {
                        asyncContext.getResponse().getWriter().write("AProcessorServlet\n");
                    } catch (IOException e) {
                        e.printStackTrace(System.err);
                    }

                    asyncContext.dispatch("/pipeline/b");
                },
                this.getServletContext()
        );

    }
}
