package com.managermate.backend.service;

import com.managermate.backend.exception.TaskNotFoundException;
import com.managermate.backend.model.Task;
import com.managermate.backend.model.TaskSubmission;
import com.managermate.backend.model.User;
import com.managermate.backend.repository.TaskRepository;
import com.managermate.backend.repository.TaskSubmissionRepository;
import com.managermate.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskSubmissionService {

    private final TaskSubmissionRepository taskSubmissionRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public List<TaskSubmission> getAllTaskSubmissions() {
        return taskSubmissionRepository.findAll();
    }

    public List<TaskSubmission> getSubmissionsByUserId(Integer userId) {
        return taskSubmissionRepository.findByUserUserId(userId);
    }

    public List<TaskSubmission> getSubmissionsByTaskId(Integer taskId) {
        return taskSubmissionRepository.findByTaskTaskId(taskId);
    }

    public void submitTask(Integer taskId, Integer userId, String comment, MultipartFile file) throws IOException, TaskNotFoundException {
        byte[] fileData = file.getBytes();

        Optional<Task> task = taskRepository.findByTaskId(taskId);
        Optional<User> user = userRepository.findByUserIdAndIsActiveTrueOrIsActiveNull(userId);

        if (task.isPresent() && user.isPresent()) {
            TaskSubmission taskSubmission = TaskSubmission.builder()
                    .task(task.get())
                    .user(user.get())
                    .submissionTime(LocalDateTime.now())
                    .submissionComment(comment)
                    .submissionImage(fileData)
                    .build();

            taskSubmissionRepository.save(taskSubmission);
        } else {
            throw new TaskNotFoundException("The task does not exist");
        }
    }
}
