package snw.mods.rfm.api.game;

import lombok.Getter;

/**
 * The role a player takes in a game, which decides their abilities and what the game expects from them.
 * <p>
 * Some constants are <b>derived</b> from a base identity: they are distinct constants for behaviour and
 * display purposes, but they count as the base identity when a check asks for the base — see
 * {@link #isBasedOn}. For example {@link #OUT} is derived from {@link #RUNNER}, so an eliminated player is
 * still a runner for any rule that treats runners loosely, while the strict
 * {@link #isBasedOn} comparison can still tell the two apart.
 */
public enum Identity {
    /**
     * Hunts the runners and may arrest them.
     */
    HUNTER,
    /**
     * Flees from the hunters; wins if still alive when the game ends.
     */
    RUNNER,
    /**
     * A non-player character which does not participate in the win conditions.
     */
    NPC,
    /**
     * A runner who was arrested or otherwise eliminated. Derived from {@link #RUNNER}.
     */
    OUT(RUNNER),
    /**
     * A player who left the game on their own initiative.
     */
    ABANDONER

    ;
    /**
     * The actual identity this instance represents. <br>
     * Some identities were derived from another identity for simplifying our code.
     *
     * @return The identity this constant represents, which is this constant itself for a base identity
     */
    @Getter
    private final Identity underlying;

    Identity() {
        this.underlying = this;
    }

    Identity(Identity underlying) {
        this.underlying = underlying;
    }

    /**
     * Check if this identity is derived from the given base identity, or is the base identity itself. <br>
     * The {@link #getUnderlying()} chain is walked upwards, so for example
     *  {@code OUT.isBasedOn(RUNNER)} is {@code true} while {@code RUNNER.isBasedOn(OUT)} is {@code false}.
     *
     * @param base The base identity
     * @return True if this identity is, or is derived from, {@code base}
     */
    public boolean isBasedOn(Identity base) {
        // The underlying chain is declared flat: every constant points at a constant that represents itself.
        return this == base || (underlying != this && underlying.isBasedOn(base));
    }
}
