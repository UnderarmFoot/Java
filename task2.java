public class WorkerThread {

    private final BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>();
    private final Thread thread;
    private volatile boolean running = true;

    public WorkerThread() {
        thread = new Thread(() -> {
            while (running) {
                try {
                    Runnable task = queue.take();
                    task.run();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });

        thread.start();
    }

    public void execute(Runnable task) {
        if (!running) {
            throw new IllegalStateException("WorkerThread is stopped");
        }

        queue.offer(task);
    }

    public void shutdown() {
        running = false;
        thread.interrupt();
    }
}
