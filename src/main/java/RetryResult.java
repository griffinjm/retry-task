import java.util.Objects;

public class RetryResult<V> {

    private boolean successful;
    private V returned;

    public RetryResult(boolean successful) {
        this(successful, null);
    }

    public RetryResult(boolean successful, V returned) {
        this.successful = successful;
        this.returned = returned;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public V getReturned() {
        return returned;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RetryResult<?> that = (RetryResult<?>) o;
        return successful == that.successful && Objects.equals(returned, that.returned);
    }

    @Override
    public int hashCode() {
        return Objects.hash(successful, returned);
    }

    @Override
    public String toString() {
        return "RetryResult{" +
                "successful=" + successful +
                ", returned=" + returned +
                '}';
    }
}
