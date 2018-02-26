package edu.gsu.taskExecutor;

import edu.gsu.taskExecutorImpl.TaskExecutorImpl;

public class TaskRunner extends TaskExecutorImpl implements Runnable{
    

    String threadName;
    
    public TaskRunner() {
    }
    
    public TaskRunner(String name) {
        threadName = name;
    }
  
    
    @Override
    public void run() {
        Thread.currentThread().setName(threadName);
        while(true) {
            Task task = null;
            try {
                // Get task from blocking queue in static class
                task = (Task)taskExecutor.getTask();
                task.execute();
                
            } catch (Exception e) {
            	e.printStackTrace();
            }
        }
    }
}
