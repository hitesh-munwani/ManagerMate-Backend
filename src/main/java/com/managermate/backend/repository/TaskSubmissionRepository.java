package com.managermate.backend.repository;

import com.managermate.backend.model.TaskSubmission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskSubmissionRepository extends JpaRepository<TaskSubmission, Integer> {

    List<TaskSubmission> findByUserUserId(Integer userId);

    List<TaskSubmission> findByTaskTaskId(Integer taskId);
}
