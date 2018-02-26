package edu.gsu.taskExecutorImpl;

import edu.gsu.taskExecutor.BlockingQueueTeam7;
import edu.gsu.taskExecutor.Task;
import edu.gsu.taskExecutor.TaskExecutor;
import edu.gsu.taskExecutor.TaskRunner;

import java.util.ArrayList;

public class TaskExecutorImpl implements TaskExecutor {

    // Create a static object for the sub classes to use
    public static TaskExecutorImpl taskExecutor;
    // Holds threads executing the tasks
    ArrayList<TaskRunner> taskRunners;
    // Holds tasks waiting to be exectued
    BlockingQueueTeam7 taskQueue;

    public TaskExecutorImpl() {
    }

    public TaskExecutorImpl(int thdnum) {

        // Initialize the task queue
        taskQueue = new BlockingQueueTeam7(thdnum);

        // Create the number of thread TaskRunners, add to ArrayList
        taskRunners = new ArrayList<TaskRunner>();

        while (thdnum > 0) {
            StringBuilder strbld = new StringBuilder();
            strbld.append(thdnum);
            TaskRunner taskRunner = new TaskRunner(strbld.toString());
            taskRunners.add(taskRunner);
            thdnum--;
        }

        // Set the static object to this instance of TaskExecutor
        taskExecutor = this;

        // Start the threads
        for (TaskRunner taskRunner : taskRunners) {
            Thread insertThread = new Thread(taskRunner);
            insertThread.start();
        }
    }

    @Override
    public void addTask(Task task) {
        try {
            // Add tasks to blocking queue and block it if full
            taskExecutor.taskQueue.put(task);
        } catch (InterruptedException e) {
        	e.printStackTrace();
        }
    }

    public Object getTask() throws InterruptedException {
        return taskExecutor.taskQueue.take();
    }
}
