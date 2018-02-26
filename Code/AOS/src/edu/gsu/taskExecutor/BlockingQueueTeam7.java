package edu.gsu.taskExecutor;

import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BlockingQueueTeam7 {

    private ArrayList<Task> FiFoqueue;
    
    // Set initial max limit
    private int limit = 100;
    
    // For mutual exclusion mechanism (monitor locking)
    private final Lock lock = new ReentrantLock();

    // For blocking when queue is full or empty
    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();

    public BlockingQueueTeam7(int limit) {
        
    	FiFoqueue = new ArrayList<Task>();
        this.limit = limit;
    }

    public void put(Object item) throws InterruptedException {      
        lock.lock();
        
        try {
            //  Wait till there is space on queue
            while(FiFoqueue.size() == limit) {
                notFull.await();
            }
            FiFoqueue.add((Task)item);

            // Notify a thread waiting to take a task
            notEmpty.signal();
            
        } finally{
            lock.unlock();
        }    
    }

    public synchronized Task take() throws InterruptedException {
        
        lock.lock();
        
        try {
            //  Wait for there to be something in queue
            while(FiFoqueue.isEmpty()) {
                notEmpty.await();
            }

            // Notify a thread waiting for there to be space
            notFull.signal();
            
            return FiFoqueue.remove(0);
            
        } finally{
            lock.unlock();
        }
    }
}
