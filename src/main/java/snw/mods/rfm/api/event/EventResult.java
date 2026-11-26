package snw.mods.rfm.api.event;

import org.jetbrains.annotations.Nullable;

/**
 * The control value returned by a listener of an event-result event, or carried by a
 * {@link CompoundEventResult}.
 * <p>
 * Each constant answers two questions: whether the remaining listeners should still be evaluated
 * ({@link #interruptsFurtherEvaluation()}), and what answer the event reports ({@link #value()}).
 * {@link #PASS} is the only constant that keeps the evaluation going; when every listener passed, the
 * event reports it as the final result.
 *
 * @see CompoundEventResult
 */
public enum EventResult {
    /**
     * The listener made an affirmative decision, stop evaluating.
     */
    TRUE(true, true),
    /**
     * The listener made a negative decision, stop evaluating.
     */
    FALSE(true, false),
    /**
     * The listener expressed no opinion, let the remaining listeners evaluate.
     */
    PASS(false, null),
    /**
     * The listener decided that nothing more should happen, but gave no true/false answer.
     */
    STOP(true, null);

    private final boolean interruptsFurtherEvaluation;
    private final @Nullable Boolean value;

    EventResult(boolean interruptsFurtherEvaluation, @Nullable Boolean value) {
        this.interruptsFurtherEvaluation = interruptsFurtherEvaluation;
        this.value = value;
    }

    /**
     * Check whether this result ends the evaluation of the remaining listeners.
     *
     * @return True if no further listener should be called
     */
    public boolean interruptsFurtherEvaluation() {
        return interruptsFurtherEvaluation;
    }

    /**
     * Get the answer this result reports.
     *
     * @return The answer, or {@code null} if this result carries none ({@link #PASS} and
     *         {@link #STOP})
     */
    public @Nullable Boolean value() {
        return value;
    }

    /**
     * Check whether this result carries no answer.
     *
     * @return True if {@link #value()} is {@code null}
     */
    public boolean isEmpty() {
        return value == null;
    }

    /**
     * Check whether this result is an affirmative answer.
     *
     * @return True only for {@link #TRUE}
     */
    public boolean isTrue() {
        return Boolean.TRUE.equals(value);
    }

    /**
     * Check whether this result is a negative answer.
     *
     * @return True only for {@link #FALSE}
     */
    public boolean isFalse() {
        return Boolean.FALSE.equals(value);
    }
}
