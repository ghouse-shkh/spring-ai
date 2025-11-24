package com.learn.mcphttp.tools;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springaicommunity.mcp.annotation.McpTool;
import org.springframework.stereotype.Service;

@Service
public class TaskTool {

    private record Task(int taskId, String task, String priority, boolean completed) {
    }

    private List<Task> tasks = new ArrayList<>();

    private static int taskId = 1;

    @McpTool(name = "addTask", description = "Add a new task to Tasklist. Specify task and priority")
    public String addTask(String task, String priority) {
        tasks.add(new Task(TaskTool.taskId++, task, priority, false));
        return "Task added successfully";
    }

    @McpTool(name = "getTasks", description = "Get all tasks from Tasklist. Specify completed to get only completed tasks")
    public List<Task> getTasks(boolean completed) {
        return tasks.stream().filter(task -> task.completed() == completed).toList();
    }

    @McpTool(name = "completeTask", description = "Complete a task in Tasklist. Specify taskId to complete a task")
    public String completeTask(int taskId) {
        tasks = tasks.stream()
                .map(task -> {
                    if (task.taskId() == taskId) {
                        return new Task(task.taskId(), task.task(), task.priority(), true);
                    }
                    return task;
                })
                .collect(Collectors.toList());
        return "Task completed successfully";
    }

}
