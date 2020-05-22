package com.linh.common.base;

import javax.persistence.EntityManager;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

// leave this code for reference as hibernate filter is not working on FindById
// this will cause security flaw in Restful API
//@Aspect
//@Component
public class EnableFilterAspect {
    @AfterReturning(
            pointcut="bean(entityManagerFactory) && execution(* createEntityManager(..))",
            returning="retVal")
    public void getSessionAfter(JoinPoint joinPoint, Object retVal) {
        if (retVal != null && EntityManager.class.isInstance(retVal)) {
            Session session = ((EntityManager) retVal).unwrap(Session.class);
            session.enableFilter("entityStateFilter");
        }
    }
}