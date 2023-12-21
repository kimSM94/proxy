package hello.proxy.jdkdynamic;

import hello.proxy.jdkdynamic.code.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Proxy;

@Slf4j
public class JdkDynamicProxyTest {

    @Test
    void dynamicA() {
        AImpl target = new AImpl();

        TimeInvocationHandler handler = new TimeInvocationHandler(target);

        Ainterface proxy = (Ainterface) Proxy.newProxyInstance(Ainterface.class.getClassLoader(), new Class[]{Ainterface.class}, handler);

        proxy.call();
        log.info("targetClass={}",target.getClass());
        log.info("proxyClass={}",proxy.getClass());
    }

    @Test
    void dynamicB() {
        BImpl target = new BImpl();

        TimeInvocationHandler handler = new TimeInvocationHandler(target);

        Binterface proxy = (Binterface) Proxy.newProxyInstance(Binterface.class.getClassLoader(), new Class[]{Binterface.class}, handler);

        proxy.call();
        log.info("targetClass={}",target.getClass());
        log.info("proxyClass={}",proxy.getClass());
    }
}
