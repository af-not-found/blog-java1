package net.afnf.blog.common;

import javax.servlet.ServletContext;

import net.afnf.blog.bean.AppConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.jolbox.bonecp.BoneCPDataSource;

public class MyApplicationListener implements ApplicationListener<ContextRefreshedEvent> {

    private static Logger logger = LoggerFactory.getLogger(MyApplicationListener.class);

    private static boolean initialized = false;

    @Autowired
    private ServletContext servletContext;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (initialized == false) {
            initialized = true;
            logger.info("onApplicationEvent called");

            String webappDir = servletContext.getRealPath("/");
            AppConfig.setWebappDir(webappDir);

            ApplicationContext applicationContext = contextRefreshedEvent.getApplicationContext();
            Object obj = applicationContext.getBean("dataSource");
            if (obj instanceof BoneCPDataSource) {
                @SuppressWarnings("resource")
                BoneCPDataSource ds = (BoneCPDataSource) obj;
                logger.info("jdbcUrl : " + ds.getJdbcUrl());
            }
        }
    }
}
