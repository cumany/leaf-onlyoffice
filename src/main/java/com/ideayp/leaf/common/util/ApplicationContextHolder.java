package com.ideayp.leaf.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * <p>Description: 容器管理  </p>
 * <p>email: ypasdf@163.com</p>
 * <p>Copyright: Copyright (c) 2017</p>
 *
 * @author yangpeng
 * @version 1.0
 * @date 2018/1/4
 * @since 1.8
 */
@Component
public class ApplicationContextHolder implements ApplicationContextAware {
    private static Logger logger = LoggerFactory.getLogger(ApplicationContextHolder.class);
    private static ApplicationContext APPLICATION_CONTEXT;
    private static DefaultListableBeanFactory defaultListableBeanFactory;

    /**
     * 设置spring上下文
     *
     * @param applicationContext spring上下文
     * @throws BeansException 异常
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        logger.debug("ApplicationContext registed-->{}", applicationContext);
        APPLICATION_CONTEXT = applicationContext;
        defaultListableBeanFactory = (DefaultListableBeanFactory) applicationContext.getAutowireCapableBeanFactory();

    }


    /**
     * 动态注入bean
     *
     * @param beanName        名称
     * @param singletonObject 结果
     */
    public static void registerSingleton(String beanName, Object singletonObject) {
        defaultListableBeanFactory.registerSingleton(beanName, singletonObject);

    }

    /**
     * 移除bean
     * @param beanName name
     */
    public static void destroySingleton(String beanName) {
        defaultListableBeanFactory.destroySingleton(beanName);
    }

    /**
     * 获取容器
     *
     * @return 当前对象
     */
    public static ApplicationContext getApplicationContext() {
        return APPLICATION_CONTEXT;
    }

    /**
     * 获取容器对象
     *
     * @param type 类型
     * @param <T> 类型
     * @return 结果
     */
    public static <T> T getBean(Class<T> type) {
        try {
            return APPLICATION_CONTEXT.getBean(type);
        } catch (Exception e) {
            logger.error("getBean fail ", type, e);
        }
        return null;
    }
}