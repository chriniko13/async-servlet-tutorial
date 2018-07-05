package com.chriniko.example.controller.pipeline;

import com.chriniko.example.listener.AsyncWorkerListener;

import javax.servlet.AsyncContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadPoolExecutor;

public interface ProcessorServlet {

    default void processAsync(Runnable workToDo, ServletContext servletContext) {

        final ThreadPoolExecutor workersPool = (ThreadPoolExecutor) servletContext.getAttribute("workersPool");
        workersPool.submit(workToDo);
    }

    default AsyncContext produce(HttpServletRequest req) {
        req.setAttribute("org.apache.catalina.ASYNC_SUPPORTED", true);

        final AsyncContext asyncContext = req.startAsync();
        asyncContext.setTimeout(6000);
        asyncContext.addListener(new AsyncWorkerListener());

        return asyncContext;
    }

    default void sleep(long seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException ignored) {
        }
    }

    default void increaseStagesCounter(ServletContext servletContext) {
        final Semaphore semaphore = (Semaphore) servletContext.getAttribute("binarySemaphore");
        try {
            semaphore.acquire();
            Integer totalStagesInPipeline = (Integer) servletContext.getAttribute("totalStagesInPipeline");
            if (totalStagesInPipeline == null) {
                servletContext.setAttribute("totalStagesInPipeline", 1);
                return;
            }
            servletContext.setAttribute("totalStagesInPipeline", totalStagesInPipeline + 1);
        } catch (InterruptedException e) {
            e.printStackTrace(System.err);
        } finally {
            if (semaphore != null) {
                semaphore.release();
            }
        }
    }

}
