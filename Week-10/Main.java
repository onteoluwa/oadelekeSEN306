public class Main {
    public static void main(String[] args) {

        // Exercise 1: Queue ADT
        System.out.println("=== Exercise 1: Queue ADT ===\n");

        System.out.println("-- ArrayListQueue --");
        testQueue(new ArrayListQueue());

        System.out.println("\n-- LinkedQueue (same client code, different impl) --");
        testQueue(new LinkedQueue());  

        //Exercise 2: OverdraftAccount 
        System.out.println("\n=== Exercise 2: OverdraftAccount ===\n");

        OverdraftAccount acc = new OverdraftAccount();
        acc.deposit(100);
        acc.withdraw(50);
        acc.withdraw(200); 
        acc.withdraw(400);   
        System.out.println("Final balance: £" + acc.getBalance());
    }


    static void testQueue(QueueADT queue) {
        queue.enqueue(10);
        queue.enqueue(20);
        queue.enqueue(30);
        System.out.println("Size after 3 enqueues: " + queue.size());
        System.out.println("Dequeue: " + queue.dequeue());   
        System.out.println("Dequeue: " + queue.dequeue());   
        System.out.println("isEmpty: " + queue.isEmpty());   
        System.out.println("Dequeue: " + queue.dequeue());   
        System.out.println("isEmpty: " + queue.isEmpty());  
    }
}
