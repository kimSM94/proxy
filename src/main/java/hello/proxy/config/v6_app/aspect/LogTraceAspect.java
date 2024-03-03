package hello.proxy.config.v6_app.aspect;

import hello.proxy.trace.TraceStatus;
import hello.proxy.trace.logtrace.LogTrace;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import java.lang.reflect.Method;

@Slf4j
@Aspect
public class LogTraceAspect {

    private final LogTrace logTrace;

    public LogTraceAspect(LogTrace logTrace) {
        this.logTrace = logTrace;
    }
/*
* Around의 값에 포인트컷 표현식을 넣는다. 표현식을 AspectJ 표현식을 사용한다.
* Around의 메서드는 어드바이스("Advice")가 된다.
* */
    @Around("execution(* hello.proxy.app..*(..))")
    public Object execute(ProceedingJoinPoint joinPoint) throws  Throwable {
        TraceStatus status = null;
    /*
    * ProceedingJoinPoint joinPoint
    * MethodInvocation invocation과 유사한 기능. 내부에 실제 호출 대상,
    *  전달 인자, 그리고 어떤 객체와 어떤 메서드가 호출되었는지 정보가 담겨있따.
    *
    * */
        try {
            String message = joinPoint.getSignature().toShortString();

            status = logTrace.begin(message);

            // 로직 호출
            // joinPoint.proceed(); : 실제 호출 대상('target')을 호출
            Object result = joinPoint.proceed();

            logTrace.end(status);
            return result;
        } catch (Exception e) {
            logTrace.exception(status, e);
            throw e;
        }
    }
}
