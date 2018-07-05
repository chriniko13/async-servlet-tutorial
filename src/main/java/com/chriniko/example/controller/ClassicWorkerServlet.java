package com.chriniko.example.controller;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class ClassicWorkerServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {


        final String startMessage = "ClassicWorkerServlet Start::Name="
                + Thread.currentThread().getName() + "::ID="
                + Thread.currentThread().getId();
        System.out.println(startMessage);

        long startTime = System.currentTimeMillis();
        heavyWork();
        long endTime = System.currentTimeMillis();


        final String endMessage = "ClassicWorkerServlet Finish::Name="
                + Thread.currentThread().getName() + "::ID="
                + Thread.currentThread().getId() + "::Time Taken="
                + (endTime - startTime) + " ms.";
        System.out.println(endMessage);

        resp.getWriter().write(startMessage + "\n" + endMessage);
    }

    private void heavyWork() {

        int rand = ThreadLocalRandom.current().nextInt(5) + 1;

        try {
            TimeUnit.SECONDS.sleep(rand);
        } catch (InterruptedException ignored) {
        }

    }
}
