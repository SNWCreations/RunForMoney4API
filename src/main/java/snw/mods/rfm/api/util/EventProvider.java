package snw.mods.rfm.api.util;

import snw.mods.rfm.api.event.Event;

/**
 * A type that may produce {@link Event}s.
 * <p>
 * Implementations group related events behind a single accessor, so a caller can reach them without
 * knowing the concrete implementation type.
 *
 * @param <T> A type that instances provides {@link Event} instances
 */
public interface EventProvider<T> {
    /**
     * Get the group of events of this instance.
     * <p>
     * The returned object stays the same for the whole lifetime of the instance.
     *
     * @return The event group of this instance, never null
     */
    T events();
}
