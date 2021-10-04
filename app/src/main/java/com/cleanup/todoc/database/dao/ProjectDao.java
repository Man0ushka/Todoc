package com.cleanup.todoc.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.cleanup.todoc.model.Project;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;

@Dao
public interface ProjectDao {
    @Query("SELECT * FROM Project WHERE id=:projectId")
    LiveData<Project> getProject(long projectId);

    @Query("SELECT * FROM Project")
    LiveData<List<Project>> getAllProjects();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long createProject(Project project);
}