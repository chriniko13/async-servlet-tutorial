package com.chriniko.example.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ApplicationListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        // Note: init and save pool.
        ThreadPoolExecutor workersPool = new ThreadPoolExecutor(
                25,
                150,
                40,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(300),
                new ThreadPoolExecutor.DiscardPolicy()
        );

        sce.getServletContext().setAttribute("workersPool", workersPool);


        // Note: init and save semaphore.
        Semaphore semaphore = new Semaphore(1, true);
        sce.getServletContext().setAttribute("binarySemaphore", semaphore);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

        ThreadPoolExecutor workersPool = (ThreadPoolExecutor) sce.getServletContext().getAttribute("workersPool");
        workersPool.shutdown();
    }
}
