import java.util.concurrent.Callable;

public class SimpleRetry<V> {

    public boolean runWithRetry(int maxRetries, Runnable runnable) {
        for (int i = 0; i < maxRetries; i++) {
            try {
                runnable.run();
                return true;
            } catch (Exception e) {
                // leave the loop retry
            }
        }
        return false;
    }

    public RetryResult<V> runWithRetry(int maxRetries, Callable<V> callable) {
        for (int i = 0; i < maxRetries; i++) {
            try {
                return new RetryResult<V>(true, callable.call());
            } catch (InterruptedException ie) {
                // re-interrupt the thread and short-circuit execution
                Thread.currentThread().interrupt();
                return new RetryResult<V>(false);
            } catch (Exception e) {
                // leave the loop retry
            }
        }
        return new RetryResult<V>(false);
    }

}
