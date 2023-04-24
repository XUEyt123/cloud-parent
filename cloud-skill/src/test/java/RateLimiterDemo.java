import com.google.common.util.concurrent.RateLimiter;

public class RateLimiterDemo {
    public static void main(String[] args) {
//        testNoRateLimiter();
        testWithRateLimiter();
    }

    public static void testNoRateLimiter() {
        Long start = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            System.out.println("call execute.." + i);
        }
        Long end = System.currentTimeMillis();
        System.out.println(end - start);
    }

    // 限流
    public static void testWithRateLimiter() {
        Long start = System.currentTimeMillis();
        // 每秒生成几个令牌
        RateLimiter limiter = RateLimiter.create(1);
        for (int i = 0; i < 20; i++) {
            // 请求RateLimiter, 超过permits会被阻塞
            // acquire 尝试获取令牌 如果没有获取到令牌这个方法会阻塞 返回值是阻塞的时间
            limiter.acquire();
            // tryAcquire 尝试获取令牌 这个方法不会阻塞 获取到令牌返回true 获取不到放回false
//            boolean acquire = limiter.tryAcquire();
            System.out.println("call execute.." + i);
        }
        Long end = System.currentTimeMillis();

        System.out.println(end - start);

    }

}
