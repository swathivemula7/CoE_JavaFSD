package week1;
import java.util.*;

class Task implements Comparable<Task> {
    private String id;
    private String description;
    private int priority;

    public Task(String id, String description, int priority) {
        this.id = id;
        this.description = description;
        this.priority = priority;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public int getPriority() {
        return priority;
    }

    @Override
    public int compareTo(Task other) {
        return Integer.compare(other.priority, this.priority); // Higher priority first
    }

    @Override
    public String toString() {
        return "Task ID: " + id + ", Description: " + description + ", Priority: " + priority;
    }
}

class TaskManager {
    private PriorityQueue<Task> taskQueue;

    public TaskManager() {
        taskQueue = new PriorityQueue<>();
    }

    public void addTask(String id, String description, int priority) {
        Task newTask = new Task(id, description, priority);
        taskQueue.add(newTask);
    }

    public void removeTask(String id) {
        taskQueue.removeIf(task -> task.getId().equals(id));
    }

    public Task getHighestPriorityTask() {
        return taskQueue.peek();
    }

    public void printTasks() {
        for (Task task : taskQueue) {
            System.out.println(task);
        }
    }
}

public class Main1 {
    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();

        taskManager.addTask("1", "Complete project report", 2);
        taskManager.addTask("2", "Fix critical bug", 5);
        taskManager.addTask("3", "Update website", 3);

        System.out.println("Highest Priority Task: " + taskManager.getHighestPriorityTask());

        taskManager.printTasks();

        taskManager.removeTask("2");
        System.out.println("\nAfter removing task with ID 2:");
        taskManager.printTasks();
    }
}
