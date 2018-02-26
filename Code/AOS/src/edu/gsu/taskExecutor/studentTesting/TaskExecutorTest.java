package edu.gsu.taskExecutor.studentTesting;

import edu.gsu.taskExecutor.Task;
import edu.gsu.taskExecutorImpl.TaskExecutorImpl;

public class TaskExecutorTest
{
    public void runTest()
	{
		// Initialize the executor with 10 threads. 
		final TaskExecutorImpl taskExecutor = new TaskExecutorImpl(10);
		
		// Inject 1000 tasks into the executor in a separate thread. 
		Runnable inserter = new Runnable() {
			public void run()
			{
				for (int idx = 0; idx < 500; idx++) {
					// Note that Threads are assigned names. 
					String name = "SimpleTask" + idx;
					Task myTask = new SimpleTestTask(name);
					System.out.println("******  Adding Task " + myTask.getName());
					taskExecutor.addTask(myTask);
					Thread.yield();
				}
			}
		};
		
		Thread insertThread = new Thread(inserter);
		insertThread.start();
	}

	public static void main(String args[])
	{
		TaskExecutorTest app = new TaskExecutorTest();
		app.runTest();
	}

}
