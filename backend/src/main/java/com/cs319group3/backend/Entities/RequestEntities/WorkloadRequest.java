package com.cs319group3.backend.Entities.RequestEntities;


import com.cs319group3.backend.Entities.Course;
import com.cs319group3.backend.Entities.TaskType;
import com.cs319group3.backend.Entities.UserEntities.Instructor;
import com.cs319group3.backend.Entities.UserEntities.TA;
import com.cs319group3.backend.Entities.UserEntities.User;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import org.springframework.http.RequestEntity;

@Entity
@Table(name = "workload_request")
public class WorkloadRequest extends Request{

    

    @ManyToOne
    @JoinColumn(name = "task_type_id")
    private TaskType taskType;

    private int timeSpent;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;
}
