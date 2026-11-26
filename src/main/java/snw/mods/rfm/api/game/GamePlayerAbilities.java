package snw.mods.rfm.api.game;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.jetbrains.annotations.MustBeInvokedByOverriders;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * The class which describes the abilities that a player could have. <br>
 * The conditions given in docs of the member variable can be applied to common game,
 * but not always true in some cases. <br>
 * The values in this class are <b>NOT</b> reliable if the player is not online.
 * <p>
 * Note: every field in this class must have an {@link EquivalentTo} annotation which points to their own
 * {@link VarHandleEnum} equivalent.
 */
/*
 * This class was reused from previous generation of the Run For Money game engine, original inspired by Minecraft,
 *  I think it is a genius design!
 */
@Getter
@RequiredArgsConstructor
@ToString
@EqualsAndHashCode
public class GamePlayerAbilities {
    /**
     * Determines if the player should be regarded as a regular player.<br>
     * This will be {@code false} if the player was abandoned from the game,
     * but should be {@code true} in <b>ANY OTHER</b> cases.
     *
     * @return True if the player is regarded as a regular player
     */
    @EquivalentTo(VarHandleEnum.IN_GAME)
    private boolean inGame;

    /**
     * Determines if the player is arrestable. <br>
     * This will be {@code false} only if the player is not a runner.
     *
     * @return True if other players may arrest this player
     */
    @EquivalentTo(VarHandleEnum.ARRESTABLE)
    private boolean arrestable;

    /**
     * Determines if the player should be regarded as arrested.<br>
     * This will be {@code true} if the player was a runner but was arrested.
     *
     * @return True if the player was arrested
     */
    @EquivalentTo(VarHandleEnum.ARRESTED)
    private boolean arrested;

    /**
     * Determines if the player can arrest other players who can be arrested.<br>
     * This will be {@code false} if the player is not a hunter.
     *
     * @return True if the player may arrest other players
     */
    @EquivalentTo(VarHandleEnum.CAN_ARREST_OTHERS)
    private boolean canArrestOthers;

    /**
     * Determines if the player can abandon the game.<br>
     * This will be {@code false} if the player is not a runner.
     *
     * @return True if the player may abandon the game
     */
    @EquivalentTo(VarHandleEnum.CAN_ABANDON)
    private boolean canAbandon;

    /**
     * Determines if the player can receive notifications from the game.<br>
     * This will be {@code false} if the player is not a runner.
     *
     * @return True if the player may receive notifications from the game
     */
    @EquivalentTo(VarHandleEnum.CAN_RECEIVE_NOTIFICATIONS)
    private boolean canReceiveNotifications;

    /**
     * Set whether the player should be regarded as a regular player.
     *
     * @param inGame The new value
     */
    public void setInGame(boolean inGame) {
        if (inGame != isInGame()) {
            this.inGame = inGame;
            onPropertyChanged();
        }
    }

    /**
     * Set whether the player is arrestable.
     *
     * @param arrestable The new value
     */
    public void setArrestable(boolean arrestable) {
        if (arrestable != isArrestable()) {
            this.arrestable = arrestable;
            onPropertyChanged();
        }
    }

    /**
     * Set whether the player should be regarded as arrested.
     *
     * @param arrested The new value
     */
    public void setArrested(boolean arrested) {
        if (arrested != isArrested()) {
            this.arrested = arrested;
            onPropertyChanged();
        }
    }

    /**
     * Set whether the player can abandon the game.
     *
     * @param canAbandon The new value
     */
    public void setCanAbandon(boolean canAbandon) {
        if (canAbandon != isCanAbandon()) {
            this.canAbandon = canAbandon;
            onPropertyChanged();
        }
    }

    /**
     * Set whether the player can arrest other players.
     *
     * @param canArrestOthers The new value
     */
    public void setCanArrestOthers(boolean canArrestOthers) {
        if (canArrestOthers != isCanArrestOthers()) {
            this.canArrestOthers = canArrestOthers;
            onPropertyChanged();
        }
    }

    /**
     * Set whether the player can receive notifications from the game.
     *
     * @param canReceiveNotifications The new value
     */
    public void setCanReceiveNotifications(boolean canReceiveNotifications) {
        if (canReceiveNotifications != isCanReceiveNotifications()) {
            this.canReceiveNotifications = canReceiveNotifications;
            onPropertyChanged();
        }
    }

    /**
     * Whether the abilities changed since the last {@link #handleDirty()} call, used to collapse a batch
     * of changes made through {@link #configure(Consumer)} into a single notification.
     *
     * @return True if a change is waiting to be reported
     */
    private boolean propertyChanged;

    private void onPropertyChanged() {
        propertyChanged = true;
        if (!configuring) {
            handleDirty();
        }
    }

    /**
     * Called whenever the abilities changed outside of a {@link #configure(Consumer)} call.
     * <p>
     * The default implementation only clears the "changed" flag. Override it to react to changes, e.g. to
     * push the new values to the client. Implementations must call the super implementation.
     */
    @MustBeInvokedByOverriders
    protected void handleDirty() {
        propertyChanged = false;
    }

    /**
     * Whether a {@link #configure(Consumer)} call is in progress, during which changes are not reported one
     * by one.
     *
     * @return True if a batch configuration is in progress
     */
    private boolean configuring;

    /**
     * Configure this object.
     * <p>
     * Changes are applied without calling {@link #handleDirty()} for each setter; the method calls it once
     * at the end if anything changed, so a batch of setters results in a single notification.
     *
     * @param consumer Configurer that calls setters on this method
     */
    public void configure(Consumer<GamePlayerAbilities> consumer) {
        configuring = true;
        consumer.accept(this);
        configuring = false;
        if (propertyChanged) {
            handleDirty();
        }
    }

    /**
     * The enum as a map of all known defined abilities, which also provides method to get/set underlying field value. <br>
     * Currently used for commands.
     * <p>
     * Note: every field in {@link GamePlayerAbilities} must have an {@link EquivalentTo} annotation
     * which points to their own {@link VarHandleEnum} equivalent.
     */
    /*
     * Useful for setting the ability values through commands, also provides constants for command completion.
     */
    @RequiredArgsConstructor
    public enum VarHandleEnum {
        /**
         * The {@link GamePlayerAbilities#isInGame() in-game} ability.
         */
        IN_GAME(GamePlayerAbilities::isInGame, GamePlayerAbilities::setInGame),
        /**
         * The {@link GamePlayerAbilities#isArrestable() arrestable} ability.
         */
        ARRESTABLE(GamePlayerAbilities::isArrestable, GamePlayerAbilities::setArrestable),
        /**
         * The {@link GamePlayerAbilities#isArrested() arrested} ability.
         */
        ARRESTED(GamePlayerAbilities::isArrested, GamePlayerAbilities::setArrested),
        /**
         * The {@link GamePlayerAbilities#isCanArrestOthers() can-arrest-others} ability.
         */
        CAN_ARREST_OTHERS(GamePlayerAbilities::isCanArrestOthers, GamePlayerAbilities::setCanArrestOthers),
        /**
         * The {@link GamePlayerAbilities#isCanAbandon() can-abandon} ability.
         */
        CAN_ABANDON(GamePlayerAbilities::isCanAbandon, GamePlayerAbilities::setCanAbandon),
        /**
         * The {@link GamePlayerAbilities#isCanReceiveNotifications() can-receive-notifications} ability.
         */
        CAN_RECEIVE_NOTIFICATIONS(GamePlayerAbilities::isCanReceiveNotifications, GamePlayerAbilities::setCanReceiveNotifications);
        private final Predicate<GamePlayerAbilities> getter;
        private final Setter setter;

        /**
         * Read the ability value of the given abilities object.
         *
         * @param abilities The abilities object to read from
         * @return The current value of this ability
         */
        public boolean get(GamePlayerAbilities abilities) {
            return getter.test(abilities);
        }

        /**
         * Write the ability value of the given abilities object.
         * <p>
         * Setting the value goes through the setter on {@link GamePlayerAbilities}, so a change is
         * reported like any other change.
         *
         * @param abilities The abilities object to write to
         * @param value     The new value
         */
        public void set(GamePlayerAbilities abilities, boolean value) {
            setter.set(abilities, value);
        }

        private interface Setter {
            void set(GamePlayerAbilities abilities, boolean value);
        }
    }

    /**
     * The annotation which is used to tell coder that which {@link VarHandleEnum} instance to related to
     * the field in {@link GamePlayerAbilities} class.
     */
    @Target(ElementType.FIELD)
    public @interface EquivalentTo {
        /**
         * Get the enum constant describing the annotated field.
         *
         * @return The enum constant equivalent to the annotated field
         */
        VarHandleEnum value();
    }
}
