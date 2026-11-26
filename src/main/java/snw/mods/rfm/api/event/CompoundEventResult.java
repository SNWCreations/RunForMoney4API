package snw.mods.rfm.api.event;

import org.jetbrains.annotations.Nullable;

import java.util.Objects;

/**
 * The result of a listener of a compound-result event: an {@link EventResult} control value plus an
 * optional payload.
 * <p>
 * The two halves answer different questions, and a consumer may need either or both: {@link #result()}
 * says whether evaluation should stop and with which control value, while {@link #value()} is the
 * payload a listener provides, if any.
 * <p>
 * {@link #isEmpty()} answers "was a payload provided?", <b>not</b> "was a result recorded?" — every
 * dispatch returns a result, at least {@link #pass()}.
 *
 * @param <T> The type of the payload
 */
public final class CompoundEventResult<T> {
    private static final CompoundEventResult<?> PASS = new CompoundEventResult<>(EventResult.PASS, null);
    private static final CompoundEventResult<?> STOP = new CompoundEventResult<>(EventResult.STOP, null);
    private final EventResult result;
    private final @Nullable T value;

    private CompoundEventResult(EventResult result, @Nullable T value) {
        this.result = result;
        this.value = value;
    }

    /**
     * Check whether a payload was provided.
     * <p>
     * This is decided by the payload alone: {@link #pass()} and {@link #stop()} are empty because
     * they carry no payload, whatever their {@link #result()} says.
     *
     * @return True if {@link #value()} is {@code null}
     */
    public boolean isEmpty() {
        return value == null;
    }

    /**
     * Provide a payload and stop evaluating the remaining listeners, recording the affirmative
     * {@link EventResult#TRUE} control value.
     *
     * @param value The payload
     * @param <T>   The type of the payload
     * @return The result carrying the payload
     */
    public static <T> CompoundEventResult<T> interruptsTrue(T value) {
        return new CompoundEventResult<>(EventResult.TRUE, value);
    }

    /**
     * Provide a payload and stop evaluating the remaining listeners, recording the negative
     * {@link EventResult#FALSE} control value.
     * <p>
     * Whether a consumer treats this as a rejection or as an ordinary payload is up to that
     * consumer; see the event declaring it.
     *
     * @param value The payload
     * @param <T>   The type of the payload
     * @return The result carrying the payload
     */
    public static <T> CompoundEventResult<T> interruptsFalse(T value) {
        return new CompoundEventResult<>(EventResult.FALSE, value);
    }

    /**
     * Provide nothing and let the remaining listeners evaluate.
     *
     * @param <T> The type of the payload
     * @return The shared pass result
     */
    @SuppressWarnings("unchecked")
    public static <T> CompoundEventResult<T> pass() {
        return (CompoundEventResult<T>) PASS;
    }

    /**
     * Provide nothing and stop evaluating the remaining listeners, recording
     * {@link EventResult#STOP}.
     * <p>
     * What this means depends on the event; check its documentation. On a permission-style event it
     * usually means "I have decided, ignore the rest"; on a provision-style event it can be defined
     * as an explicit veto.
     *
     * @param <T> The type of the payload
     * @return The shared stop result
     */
    @SuppressWarnings("unchecked")
    public static <T> CompoundEventResult<T> stop() {
        return (CompoundEventResult<T>) STOP;
    }

    /**
     * Get the control value of this result.
     *
     * @return The control value, never {@code null}
     */
    public EventResult result() {
        return result;
    }

    /**
     * Get the payload, if a listener provided one.
     *
     * @return The payload, or {@code null}; see {@link #isEmpty()}
     */
    public @Nullable T value() {
        return value;
    }

    /**
     * Compare this result with another object.
     * <p>
     * Two results are equal when they carry the same control value and equal payloads.
     *
     * @param o The object to compare with
     * @return True if {@code o} is a {@link CompoundEventResult} with the same control value and payload
     */
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof CompoundEventResult<?> that)) return false;
        return result == that.result && Objects.equals(value, that.value);
    }

    /**
     * Get the hash code of this result, consistent with {@link #equals}.
     *
     * @return The hash code of the control value and the payload
     */
    @Override
    public int hashCode() {
        return Objects.hash(result, value);
    }

    /**
     * Get a string representation of this result, listing the control value and the payload.
     *
     * @return A human-readable description of this result
     */
    @Override
    public String toString() {
        return "CompoundEventResult[" +
                "result=" + result + ", " +
                "value=" + value + ']';
    }

}
