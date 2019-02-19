import java.util.concurrent.*;

class ThreadPoolSimple {
    public static void main(String[] args) throws Exception{
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(3);
        //simulate some work
        for (int i=0; i < 10; i++) {
            executor.submit(() -> {
                Thread.sleep(1000);
                System.out.println(Math.random());
                return null;
            });
        }
    }
}