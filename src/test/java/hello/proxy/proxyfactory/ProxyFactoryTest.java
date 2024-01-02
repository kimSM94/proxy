package hello.proxy.proxyfactory;

import hello.proxy.common.advice.TimeAdvice;
import hello.proxy.common.service.ConcreteService;
import hello.proxy.common.service.ServiceImpl;
import hello.proxy.common.service.ServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.test.util.AopTestUtils;

@Slf4j
public class ProxyFactoryTest {
    @Test
    @DisplayName("인터페이스가 있으면 JDK 동적 프록시 사용")
    void interfacePorxy() {
        ServiceInterface target = new ServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.addAdvice(new TimeAdvice());
        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();

        log.info("targetClass={}", target.getClass());
        log.info("proxyClass={}", proxy.getClass());

        proxy.save();

        Assertions.assertThat(AopUtils.isAopProxy(proxy)).isTrue(); // 프록시 팩토리 쓸때만 사용 가능
        Assertions.assertThat(AopUtils.isJdkDynamicProxy(proxy)).isTrue(); // 프록시 팩토리 쓸때만 사용 가능
        Assertions.assertThat(AopUtils.isCglibProxy(proxy)).isTrue(); // 프록시 팩토리 쓸때만 사용 가능

    }

    @Test
    @DisplayName("구체 클래스만 있으면 CGLIB 사용")
    void concreteProxy() {
        ConcreteService target = new ConcreteService();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.addAdvice(new TimeAdvice());
        ConcreteService proxy = (ConcreteService) proxyFactory.getProxy();

        log.info("targetClass={}", target.getClass());
        log.info("proxyClass={}", proxy.getClass());

        proxy.call();

        Assertions.assertThat(AopUtils.isAopProxy(proxy)).isTrue(); // 프록시 팩토리 쓸때만 사용 가능
        Assertions.assertThat(AopUtils.isJdkDynamicProxy(proxy)).isFalse(); // 프록시 팩토리 쓸때만 사용 가능
        Assertions.assertThat(AopUtils.isCglibProxy(proxy)).isTrue(); // 프록시 팩토리 쓸때만 사용 가능
    }

    @Test
    @DisplayName("ProxyTargetClass 옵션을 사용하면, 인터페이스가 있어도 CGLIB를 사용하고, 클래스 기반 프록시 사용")
    void proxyTargetClass() {
        ServiceInterface target = new ServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.setProxyTargetClass(true);
        proxyFactory.addAdvice(new TimeAdvice());
        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();

        log.info("targetClass={}", target.getClass());
        log.info("proxyClass={}", proxy.getClass());

        proxy.save();

        Assertions.assertThat(AopUtils.isAopProxy(proxy)).isTrue(); // 프록시 팩토리 쓸때만 사용 가능
        Assertions.assertThat(AopUtils.isJdkDynamicProxy(proxy)).isFalse(); // 프록시 팩토리 쓸때만 사용 가능
        Assertions.assertThat(AopUtils.isCglibProxy(proxy)).isTrue(); // 프록시 팩토리 쓸때만 사용 가능

    }
}
