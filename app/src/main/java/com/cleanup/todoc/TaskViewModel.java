package com.cleanup.todoc;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repositories.ProjectDataRepository;
import com.cleanup.todoc.repositories.TaskDataRepository;

import java.util.List;
import java.util.concurrent.Executor;

public class TaskViewModel extends ViewModel {
    private final ProjectDataRepository projectDataSource;
    private final TaskDataRepository taskDataSource;
    private final Executor executor;

    public TaskViewModel(ProjectDataRepository projectDataSource, TaskDataRepository taskDataSource, Executor executor) {
        this.projectDataSource = projectDataSource;
        this.taskDataSource = taskDataSource;
        this.executor = executor;
    }

    public LiveData<List<Task>> getTasksFromProjectId(long projectId) {
        return taskDataSource.getTasksFromProjectId(projectId);
    }
    public LiveData<List<Task>> getTasks() {
        return taskDataSource.getTasks();
    }
    public LiveData<List<Project>> getAllProjects() {
        return projectDataSource.getAllProjects();
    }
    public LiveData<Project> getProject(long projectId) {
        return projectDataSource.getProject(projectId);
    }
    public void createProject(Project project){
        executor.execute(() -> {
            projectDataSource.createProject(project);
        });
    }

    public void createTask(Task task) {
        executor.execute(() -> {
            taskDataSource.createTask(task);
        });
    }

    public void deleteTask(long taskId) {
        executor.execute(() -> {
            taskDataSource.deleteTask(taskId);
        });
    }

    public void updateTask(Task task) {
        executor.execute(() -> {
            taskDataSource.updateTask(task);
        });
    }

}
