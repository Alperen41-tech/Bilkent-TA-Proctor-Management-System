package com.cs319group3.backend.Repositories;

import com.cs319group3.backend.CompositeIDs.CourseStudentKey;
import com.cs319group3.backend.Entities.Course;
import com.cs319group3.backend.Entities.RelationEntities.CourseStudentRelation;
import com.cs319group3.backend.Entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseStudentRelationRepo extends JpaRepository<CourseStudentRelation, CourseStudentKey> {

    List<CourseStudentRelation> findByCourse_Course(Course course);
    List<CourseStudentRelation> findByCourse_CourseAndCourse_SectionNo(Course course, int sectionNo);

}
