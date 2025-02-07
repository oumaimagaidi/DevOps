package tn.esprit.foyer.configuration;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;


@Component
@Aspect
@Slf4j
public class LoggingAspect {


    @Before("execution( * tn.esprit.foyer.services.*.*(..))")
    public void logMethodEntry(JoinPoint joinPoint) {
        String name = joinPoint.getSignature().getName();
        log.info("In method : " + name + " : ");
    }

    @AfterReturning("execution( * tn.esprit.foyer.services.EtudiantServiceImpl.retrieveEtudiant(..))")
    public void logMethodExit1(JoinPoint joinPoint) {
        String name = joinPoint.getSignature().getName();
        log.info("Out of method without errors : " + name );
    }

    @AfterThrowing("execution(* tn.esprit.foyer.services.*.*(..))")
    public void logMethodExit2(JoinPoint joinPoint) {
        String name = joinPoint.getSignature().getName();
        log.error("Out of method with erros : " + name );
    }

    @After("execution(* tn.esprit.foyer.services.*.*(..))")
    public void logMethodExit(JoinPoint joinPoint) {
        String name = joinPoint.getSignature().getName();
        log.info("Out of method : " + name );
    }

}

