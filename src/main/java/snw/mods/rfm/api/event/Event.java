package snw.mods.rfm.api.event;

import snw.mods.rfm.api.platform.PlatformInstance;

/**
 * A list of listeners that are called together through a single {@link #invoker() invoker}.
 * <p>
 * The listener type is a functional interface chosen by the declaring code; its single method defines
 * the shape of a dispatch. How the return value of that method is treated depends on how the event was
 * created:
 * <ul>
 *     <li>{@link #makeLoop} ignores return values and calls every listener;</li>
 *     <li>{@link #makeEventResult} stops at the first listener that returns an {@link EventResult}
 *     which interrupts further evaluation;</li>
 *     <li>{@link #makeCompoundEventResult} does the same, reading the control value from the
 *     {@link EventResult} carried by a {@link CompoundEventResult}.</li>
 * </ul>
 * When every listener lets the evaluation pass, an event-result event reports {@link EventResult#PASS}
 * and a compound-result event reports {@link CompoundEventResult#pass()}.
 * <p>
 * Dispatching happens on the thread which calls the invoker, normally the server thread. Registering
 * and unregistering are not synchronized, so listeners are expected to be added and removed from the
 * mod initialization thread (or otherwise from the thread which dispatches the event).
 *
 * @param <T> The listener type
 */
public interface Event<T> {
    /**
     * Get an object which forwards a call to the registered listeners.
     * <p>
     * The returned object is replaced whenever the listener set changes, so it is meant to be obtained
     * at the call site instead of being cached across registrations.
     *
     * @return The invoker of this event
     */
    T invoker();

    /**
     * Add a listener to this event.
     * <p>
     * Registering the same listener instance twice adds it twice, and it will then be called twice.
     *
     * @param listener The listener to add
     */
    void register(T listener);

    /**
     * Remove one occurrence of a listener from this event.
     * <p>
     * Removing a listener that is not registered has no effect.
     *
     * @param listener The listener to remove
     */
    void unregister(T listener);

    /**
     * Check whether the given listener is currently registered.
     *
     * @param listener The listener to look for
     * @return True if at least one occurrence of the listener is registered
     */
    boolean isRegistered(T listener);

    /**
     * Create an event which calls every listener and ignores their return values.
     * <p>
     * The listener type is inferred from the assignment target, exactly like
     * {@link #makeEventResult}:
     * <pre>{@code
     * Event<SomeListener> SOME_EVENT = Event.makeLoop();
     * }</pre>
     *
     * @param typeGetter The empty varargs used for type inference, must not be supplied explicitly
     * @param <T>        The listener type
     * @return The created event
     */
    @SafeVarargs
    static <T> Event<T> makeLoop(T... typeGetter) {
        return PlatformInstance.getInstance().makeLoopEvent(typeGetter);
    }

    /**
     * Create an event whose listeners decide whether the remaining ones are still evaluated.
     * <p>
     * A listener stops the evaluation by returning an {@link EventResult} for which
     * {@link EventResult#interruptsFurtherEvaluation()} is true — that is, any result except
     * {@link EventResult#PASS}. The type is inferred from the assignment target:
     * <pre>{@code
     * Event<SomeListener> SOME_EVENT = Event.makeEventResult();
     * }</pre>
     *
     * @param typeGetter The empty varargs used for type inference, must not be supplied explicitly
     * @param <T>        The listener type
     * @return The created event
     */
    @SafeVarargs
    static <T> Event<T> makeEventResult(T... typeGetter) {
        return PlatformInstance.getInstance().makeEventResult(typeGetter);
    }

    /**
     * Create an event whose listeners both decide whether the remaining ones are still evaluated and
     * carry a payload for the caller.
     * <p>
     * Evaluation stops when a listener returns a {@link CompoundEventResult} whose
     * {@link CompoundEventResult#result()} interrupts further evaluation. The type is inferred from the
     * assignment target:
     * <pre>{@code
     * Event<SomeListener> SOME_EVENT = Event.makeCompoundEventResult();
     * }</pre>
     *
     * @param typeGetter The empty varargs used for type inference, must not be supplied explicitly
     * @param <T>        The listener type
     * @return The created event
     */
    @SafeVarargs
    static <T> Event<T> makeCompoundEventResult(T... typeGetter) {
        return PlatformInstance.getInstance().makeCompoundEventResult(typeGetter);
    }
}
