package com.cs319group3.backend.Entities.RequestEntities;


import com.cs319group3.backend.Entities.Course;
import com.cs319group3.backend.Entities.TaskType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ta_workload_request")
public class TAWorkloadRequest extends Request{

    private int TACount;

    @ManyToOne
    @JoinColumn(name = "task_type_id")
    private TaskType taskType;

    private int timeSpent;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;
}
