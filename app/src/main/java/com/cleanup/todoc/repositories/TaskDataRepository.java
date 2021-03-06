package com.cleanup.todoc.repositories;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.database.dao.TaskDao;
import com.cleanup.todoc.model.Task;

import java.util.List;

public class TaskDataRepository {
    private final TaskDao taskDao;

    public TaskDataRepository(TaskDao itemDao) {
        this.taskDao = itemDao;
    }

    // --- GET ---

    public LiveData<List<Task>> getTasksFromProjectId(long projectId) {
        return this.taskDao.getTasksFromProjectId(projectId);
    }

    public LiveData<List<Task>> getTasks() {
        return this.taskDao.getTasks();
    }

    // --- CREATE ---

    public void createTask(Task task) {
        taskDao.insertTask(task);
    }

    // --- DELETE ---
    public void deleteTask(long taskId) {
        taskDao.deleteTask(taskId);
    }

    // --- UPDATE ---
    public void updateTask(Task task) {
        taskDao.updateTask(task);
    }

    // --- DELETE ALL TASKS (TESTING) ---
    public void deleteAllTasks() {
        taskDao.deleteAllTask();
    }
}
