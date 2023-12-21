package hello.proxy.config.v1_proxy.concrete_proxy;

import hello.proxy.app.v2.OrderRepositoryV2;
import hello.proxy.app.v2.OrderServiceV2;
import hello.proxy.trace.TraceStatus;
import hello.proxy.trace.logtrace.LogTrace;

public class OrderServiceConcreteProxy extends OrderServiceV2 {

    private final OrderServiceV2 target;
    private final LogTrace logTrace;

    /**
     * 부모 생성자
     * public OrderServiceConcreteProxy{
     *
     * }
     */
    public OrderServiceConcreteProxy(OrderServiceV2 target, LogTrace logTrace) {

        super(null); // 지금 부모의 생성자를 강제로 호출해야 하기에, 그리고 null인 이유는 위에 기능을 안 쓰고 프록시 역할만 할꺼기에

        this.target = target;
        this.logTrace = logTrace;
    }


    @Override
    public void orderItem(String itemId) {
        TraceStatus status = null;

        try {
            status = logTrace.begin("OrderService.orderItem()");
            // tartget 호출
            target.orderItem(itemId);
            logTrace.end(status);
        } catch (Exception e) {
            logTrace.exception(status, e);
            throw e;
        }
    }
}
