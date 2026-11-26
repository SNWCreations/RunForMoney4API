package snw.mods.rfm.api.server.game.task;

/**
 * Task abstraction.
 * <p>
 * A task is run by the {@link ITickTaskManager} of a game. The return value of {@link #run()} tells the
 * manager whether the task is done for good, which is how a task removes itself without having to know the
 * manager.
 */
public interface Task {
    /**
     * Run this task.
     *
     * @return True if this task completed it's all work and should stop ticking forever
     */
    boolean run();
}
