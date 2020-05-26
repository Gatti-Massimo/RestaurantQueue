package com.company;

import java.util.concurrent.locks.*;
import java.util.LinkedList;
import java.util.Queue;

class RestaurantQueue<Integer> {

    //These are finals 'cause you don't want to modify them
    private final ReentrantLock lock = new ReentrantLock(true);
    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();
    private final Queue<Integer> queue;
    private final int queueSize;

    public RestaurantQueue(int size) {
        this.queue = new LinkedList<>();
        this.queueSize = size;
    }



    public void put(Integer content) throws InterruptedException {

        //Acquires the lock, set the value to 1
        //If it is already held my the same thread the value is just incremented
        lock.lock();

        try {
            while (queue.size() == queueSize) {
                //Makes the thread waits for this to be interrupted
                //Will loop until the queue is full
                notFull.await();
            }

            //Add the element to queue
            queue.add(content);

            //Notify everyone that queue is no more empty
            //Wakes up waiting threads
            notEmpty.signalAll();

            System.out.println("Meal N°" + content + " added!");

        } finally {
            //Release the lock
            lock.unlock();
        }
    }



    public void take() throws InterruptedException {

        lock.lock();

        try {

            while (queue.size() == 0) {
                //Will wait until the queue is empty
                notEmpty.await();
            }

            //Removes the head of the queue,
            //retrieves the removed item and saves it in 'content'
            Integer content = queue.remove();

            //Notify everyone that queue is no more full
            notFull.signalAll();

            System.out.println("        Meal N°" + content + " removed!");

        } finally {
            //Release the lock
            lock.unlock();
        }
    }
}