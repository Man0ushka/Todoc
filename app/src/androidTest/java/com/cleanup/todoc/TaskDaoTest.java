package com.cleanup.todoc;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.cleanup.todoc.database.TodocDatabase;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class TaskDaoTest {
    private TodocDatabase database;
    // DATA SET FOR TEST
    private static long PROJECT_ID = 1;
    private static Project PROJECT_DEMO = new Project(PROJECT_ID, "Project test", 0xFFA3CED2);
    private static Task TASK_DEMO_0 = new Task(1,PROJECT_ID, "Task test 0", new Date().getTime());
    private static Task TASK_DEMO_1 = new Task(2,PROJECT_ID, "Task test 1", new Date().getTime());
    private static Task TASK_DEMO_2 = new Task(3,PROJECT_ID, "Task test 2", new Date().getTime());

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void initDb()
    {
        this.database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().getContext(),
                TodocDatabase.class)
                .allowMainThreadQueries()
                .build();
    }
    @After
    public void closeDb()
    {
        database.close();
    }
    @Test
    public void getItemsWhenNoItemInserted() throws InterruptedException {
        // TEST
        List<Task> tasks = LiveDataTestUtil.getValue(this.database.taskDao().getTasksFromProjectId(PROJECT_ID));
        assertTrue(tasks.isEmpty());
    }
    @Test
    public void insertAndGetTask() throws InterruptedException {
        // BEFORE : Adding a new user
        this.database.projectDao().createProject(PROJECT_DEMO);
        this.database.taskDao().insertTask(TASK_DEMO_0);
        this.database.taskDao().insertTask(TASK_DEMO_1);
        this.database.taskDao().insertTask(TASK_DEMO_2);
        // TEST
        List<Task> taskList = LiveDataTestUtil.getValue(this.database.taskDao().getTasksFromProjectId(PROJECT_ID));
        assertTrue(taskList.size() == 3);
    }
    @Test
    public void insertAndUpdateItem() throws InterruptedException {
        // BEFORE : Adding demo user & demo items. Next, update item added & re-save it
        this.database.projectDao().createProject(PROJECT_DEMO);
        this.database.taskDao().insertTask(TASK_DEMO_0);
        Task taskAdded = LiveDataTestUtil.getValue(this.database.taskDao().getTasksFromProjectId(PROJECT_ID)).get(0);
        taskAdded.setName("Task update");
        this.database.taskDao().updateTask(taskAdded);

        //TEST
        List<Task> tasks = LiveDataTestUtil.getValue(this.database.taskDao().getTasksFromProjectId(PROJECT_ID));
        assertTrue(tasks.size() == 1 && tasks.get(0).getName().equals("Task update"));
    }
    @Test
    public void insertAndDeleteItem() throws InterruptedException {
        // BEFORE : Adding demo user & demo items. Next, update item added & re-save it
        this.database.projectDao().createProject(PROJECT_DEMO);
        this.database.taskDao().insertTask(TASK_DEMO_0);
        Task taskAdded = LiveDataTestUtil.getValue(this.database.taskDao().getTasksFromProjectId(PROJECT_ID)).get(0);
        this.database.taskDao().deleteTask(taskAdded.getId());

        //TEST
        List<Task> tasks = LiveDataTestUtil.getValue(this.database.taskDao().getTasksFromProjectId(PROJECT_ID));
        assertTrue(tasks.isEmpty());
    }
}
