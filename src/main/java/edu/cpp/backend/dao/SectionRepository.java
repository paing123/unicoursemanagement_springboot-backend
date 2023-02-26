package edu.cpp.backend.dao;

import edu.cpp.backend.entity.Section;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@CrossOrigin("http://localhost:4200")
public interface SectionRepository extends JpaRepository<Section, Long> {
    Optional<Section> findByNumberAndCourseId(@RequestParam("number") int number, @RequestParam("courseId") long courseId);

    Page<Section> findByCourseId(@RequestParam("courseId") Long courseId, Pageable pageable);

    Page<Section> findByYearAndSemester(@RequestParam("year") int year, @RequestParam("semester") String semester,Pageable pageable);
}