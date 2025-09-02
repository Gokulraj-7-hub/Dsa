[9/2, 1:10 PM] sri ganesh: // Producer-Consumer problem using wait() and notify()

class SharedResource {
    private int data;
    private boolean available = false;

    // Producer puts data
    public synchronized void produce(int value) {
        while (available) {
            try {
                wait(); // Wait if data is already available
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        data = value;
        available = true;
        System.out.println("Produced: " + value);
        notify(); // Notify consumer
    }

    // Consumer takes data
    public synchronized int consume() {
        while (!available) {
            try {
                wait(); // Wait if no data is available
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        available = false;
        System.out.println("Consumed: " + data);
        notify(); // Notify producer
        return data;
    }
}

// Producer thread
class Producer extends Thread {
    private SharedResource resource;

    Producer(SharedResource r) {
        this.resource = r;
    }

    public void run() {
        for (int i = 1; i <= 5; i++) {
            resource.produce(i);
            try {
                Thread.sleep(500); // Delay for visibility
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

// Consumer thread
class Consumer extends Thread {
    private SharedResource resource;

    Consumer(SharedResource r) {
        this.resource = r;
    }

    public void run() {
        for (int i = 1; i <= 5; i++) {
            resource.consume();
            try {
                Thread.sleep(800); // Delay for visibility
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

// Main class
public class ThreadCommunicationDemo {
    public static void main(String[] args) {
        SharedResource resource = new SharedResource();
        Producer producer = new Producer(resource);
        Consumer consumer = new Consumer(resource);

        producer.start();
        consumer.start();
    }
}
[9/2, 1:11 PM] sri ganesh: class SharedResource {
    private int data;
    private boolean available = false;

    // Producer method
    public synchronized void produce(int value) {
        while (available) {
            try { wait(); } catch (InterruptedException e) {}
        }
        data = value;
        available = true;
        System.out.println("Produced: " + value);
        notify(); // Wake up consumer
    }

    // Consumer method
    public synchronized int consume() {
        while (!available) {
            try { wait(); } catch (InterruptedException e) {}
        }
        available = false;
        System.out.println("Consumed: " + data);
        notify(); // Wake up producer
        return data;
    }
}

// Producer Thread
class Producer extends Thread {
    private SharedResource r;
    Producer(SharedResource r) { this.r = r; }
    public void run() {
        for (int i = 1; i <= 5; i++) {
            r.produce(i);
            try { Thread.sleep(500); } catch (Exception e) {}
        }
    }
}

// Consumer Thread
class Consumer extends Thread {
    private SharedResource r;
    Consumer(SharedResource r) { this.r = r; }
    public void run() {
        for (int i = 1; i <= 5; i++) {
            r.consume();
            try { Thread.sleep(800); } catch (Exception e) {}
        }
    }
}

// Main Class
public class InterThreadCommunicationDemo {
    public static void main(String[] args) {
        SharedResource r = new SharedResource();
        Producer p = new Producer(r);
        Consumer c = new Consumer(r);

        p.start();
        c.start();
    }
}