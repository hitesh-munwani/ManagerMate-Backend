package com.managermate.backend.model;

import com.managermate.backend.util.enums.TaskStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "tasks")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    private Integer taskId;

    @Column(name = "task_name")
    private String taskName;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "created_by", referencedColumnName = "user_id")
    private User createdBy;

    @ManyToOne
    @JoinColumn(name = "assigned_by", referencedColumnName = "user_id")
    private User assignedBy;

    @ManyToOne
    @JoinColumn(name = "assigned_to", referencedColumnName = "user_id")
    private User assignedTo;

    @ManyToOne
    @JoinColumn(name = "location_id", referencedColumnName = "location_id")
    private UserLocation location;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private TaskStatus status;

    @Column(name = "due_date")
    private LocalDateTime dueDate;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "started_at")
    private LocalDateTime startedAt;

    public TaskStatus getStatus() {
        return status != null ? TaskStatus.fromString(status.getKey()) : null;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }
}

