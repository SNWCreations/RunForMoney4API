package snw.mods.rfm.api.server.game.task;

import java.util.function.BooleanSupplier;

/**
 * Tick-based task manager. <br>
 * The methods in this class <b>MUST</b> be called on main thread.
 * <p>
 * Once a task is registered, it is run by the game on its ticks. A task is removed when it reports that
 * it finished, which happens through the return value of {@link Task#run()}: a task returning {@code true}
 * is not run again.
 */
public interface ITickTaskManager {
    /**
     * Add a task that runs in every tick.
     *
     * @param task          The task
     * @param runInThisTick True to run the task once before registering it. If that first run reports
     *                      {@link Task#run() completion}, the task is not registered at all,
     *                      because there is nothing left for it to do
     * @return True if the task was registered, false otherwise
     */
    boolean addForEveryTick(Task task, boolean runInThisTick);

    /**
     * Add a task that runs periodically.
     *
     * @param task                The task
     * @param delayBeforeFirstRun The delay before first run of given task. A value of zero runs the task
     *                            once before registering it; if that first run reports
     *                            {@link Task#run() completion}, the task is not registered at all
     * @param interval            The interval between execution of given task
     * @return True if the task was registered, false otherwise
     * @throws IllegalArgumentException Thrown if {@code delayBeforeFirstRun} or {@code interval} is negative
     */
    boolean addPeriodic(Task task, int delayBeforeFirstRun, int interval);

    /**
     * Add a task that only run when given condition is true.
     * <p>
     * The condition is checked on every tick, and the task is run only on the ticks it is true for.
     *
     * @param executeCondition The condition used to decide if task should run
     * @param task             The task
     */
    void addConditional(BooleanSupplier executeCondition, Task task);
}
