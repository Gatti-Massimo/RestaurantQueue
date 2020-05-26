package com.company;

/* Useful stuff:
 * https://stackoverflow.com/questions/2332537/producer-consumer-threads-using-a-queue
 * https://stackoverflow.com/questions/18065347/producer-consumer-scenario-with-reentrant-lock-and-condition-in-java
 * https://docs.oracle.com/javase/7/docs/api/java/util/concurrent/locks/Condition.html
 * https://mkyong.com/java/java-blockingqueue-examples/
 * https://www.youtube.com/watch?v=UOr9kMCCa5g
 */

class Main {
    public static void main(String[] args) {

        System.out.println("\n\n");
        int NumberOfMeals = 25; //For each cook
        RestaurantQueue queue = new RestaurantQueue(10);


        //Producer runnable object
        final Runnable producer = () -> {
            for (int i = 0; i < NumberOfMeals; i++) {
                try {
                    //The two cooks will produce a meal in simultaneous,
                    //and they will have the same number as recognitive name
                    queue.put(i+1);
                    Thread.sleep(600);

                } catch (InterruptedException e) { }
            }
        };

        new Thread(producer).start();
        new Thread(producer).start();



        //Consumer runnable  object
        final Runnable consumer = () -> {
            for (int i = 0; i < NumberOfMeals; i++) {
                try {
                    queue.take();
                    Thread.sleep(600);

                } catch (InterruptedException e) { }
            }
        };

        new Thread(consumer).start();
        new Thread(consumer).start();
    }
}