package com.cs319group3.backend.CompositeIDs;


import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CourseStudentKey implements Serializable {
    private int offeredCourseId;
    private int studentId;
}
