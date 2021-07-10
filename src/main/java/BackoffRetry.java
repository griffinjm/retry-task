import java.util.concurrent.Callable;

public class BackoffRetry<V> {

    public boolean runWithRetry(int maxRetries, long backoffMillis, float backoffFactor, Runnable runnable) {
        if (backoffFactor < 1F) {
            // maintain at least the same as initial delay
            backoffFactor = 1F;
        }
        for (int i = 0; i < maxRetries; i++) {
            try {
                Thread.sleep((long) (backoffMillis * i * backoffFactor));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return false;
            }

            try {
                runnable.run();
                return true;
            } catch (Exception e) {
                // leave the loop retry
            }
        }
        return false;
    }

    public RetryResult<V> runWithRetry(int maxRetries, long backoffMillis, float backoffFactor, Callable<V> callable) {
        if (backoffFactor < 1F) {
            // maintain at least the same as initial delay
            backoffFactor = 1F;
        }
        for (int i = 0; i < maxRetries; i++) {
            try {
                Thread.sleep((long) (backoffMillis * i * backoffFactor));
            } catch (InterruptedException e) {
                // re-interrupt the thread and short-circuit execution
                Thread.currentThread().interrupt();
                return new RetryResult<V>(false);
            }

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
