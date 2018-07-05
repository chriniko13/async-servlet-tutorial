package com.chriniko.example.controller.pipeline;

import javax.servlet.AsyncContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "BProcessorServlet", urlPatterns = "/pipeline/b")
public class BProcessorServlet extends HttpServlet implements ProcessorServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

        final AsyncContext asyncContext = produce(request);

        processAsync(
                () -> {

                    sleep(1);

                    increaseStagesCounter(this.getServletContext());

                    try {
                        asyncContext.getResponse().getWriter().write("BProcessorServlet, threadName: " + Thread.currentThread().getName() + "\n");
                    } catch (IOException e) {
                        e.printStackTrace(System.err);
                    }

                    asyncContext.dispatch("/pipeline/c");
                },
                this.getServletContext()
        );


    }


}
