package edu.cpp.backend.rest;

import edu.cpp.backend.dao.CourseRepository;
import edu.cpp.backend.dao.SectionRepository;
import edu.cpp.backend.entity.Section;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class SectionController {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private SectionRepository sectionRepository;

    //insert a Section record into the section table
    @PostMapping("/course/{courseId}/section")
    public ResponseEntity<Section> createSection(@PathVariable Long courseId, @RequestBody
    Section sectionRequest) throws ResourceNotFoundException {
        Section section = courseRepository.findById(courseId).map(co -> {
            sectionRequest.setCourse(co);
            return sectionRepository.save(sectionRequest);
        }).orElseThrow(() -> new ResourceNotFoundException("Not found Course with id = " +
                courseId));
        return new ResponseEntity<>(section, HttpStatus.CREATED);
    }

    // delete a Section record by course id and section number
    @DeleteMapping("/course/{courseId}/section/{number}")
    public ResponseEntity < ? > deleteCourse(@PathVariable(value = "courseId") long courseId,
                                             @PathVariable(value = "number") int number) throws ResourceNotFoundException {
        return sectionRepository.findByNumberAndCourseId(number, courseId).map(sec -> {
            sectionRepository.delete(sec);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Not found course with id " +
                courseId + " and section number " + number));
    }
}

