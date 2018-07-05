package com.chriniko.example.service;

import javax.servlet.AsyncContext;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class AsyncRequestProcessor implements Runnable {

    private AsyncContext asyncContext;

    private AsyncRequestProcessor() {
    }

    public AsyncRequestProcessor(AsyncContext asyncCtx) {
        this.asyncContext = asyncCtx;
    }

    @Override
    public void run() {

        final String startMessage = "Async Supported? " + asyncContext.getRequest().isAsyncSupported()
                + " | AsyncWorkerServlet Start::Name="
                + Thread.currentThread().getName() + "::ID="
                + Thread.currentThread().getId();
        System.out.println(startMessage);


        long startTime = System.currentTimeMillis();
        heavyWork();
        long endTime = System.currentTimeMillis();


        final String endMessage = "AsyncWorkerServlet Finish::Name="
                + Thread.currentThread().getName() + "::ID="
                + Thread.currentThread().getId() + "::Time Taken="
                + (endTime - startTime) + " ms.";
        System.out.println(endMessage);


        try {
            asyncContext.getResponse().getWriter().write(startMessage + "\n" + endMessage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //complete the processing
        asyncContext.complete();
    }

    private void heavyWork() {

        int rand = ThreadLocalRandom.current().nextInt(5) + 1; // Max rand number == 5

        try {
            TimeUnit.SECONDS.sleep(rand);
        } catch (InterruptedException ignored) {
        }

    }
}
