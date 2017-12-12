package com.peng1m.springboot.controller.RESTful;

import com.peng1m.springboot.model.Course;
import com.peng1m.springboot.model.User;
import com.peng1m.springboot.model.School;
import org.json.JSONObject;
import com.peng1m.springboot.service.CourseService;
import com.peng1m.springboot.service.SchoolService;
import com.peng1m.springboot.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/course")
public class CourseRestController {
    public static final Logger logger = LoggerFactory.getLogger(CourseRestController.class);

    private CourseService courseService;
    private SchoolService schoolService;
    private UserService userService;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    public CourseRestController(CourseService courseService, SchoolService schoolService, UserService userService) {
        this.courseService = courseService;
        this.schoolService = schoolService;
        this.userService = userService;
    }


    String path_name = "Data/";

    //#1 GET api/course
    @GetMapping(value = "")
    public List<Integer> getAllCourses() {
        return courseService.getCoursesID();
    }

    //#2 GET api/course/{course_id}
    @GetMapping(value = "/{course_id}")
    public String getCourse(@PathVariable("course_id") int course_id) {
        Course course = courseService.getCourseByID(course_id);
        if (course != null)
            return course_to_courseinfo(course);
        else return null;
    }

    //#3 POST api/course+json
    @PostMapping(value = "")
    public String addCourse(@RequestBody Object courseinfo) {
        Course course = courseinfo_to_course(courseinfo);

        course.setLast_modify(getCurrentTime());
        // Need to set author here!
        logger.info("Add Course: {}", course);
        courseService.addCourse(course);
        return course_to_courseinfo(course);
    }


    //#4 PUT	api/course/{course_id}	+	json
    @PutMapping(value = "/{course_id}")
    public String updateCourse(@RequestBody Object courseinfo, @PathVariable("course_id") int course_id) {
        Course course = courseinfo_to_course(courseinfo);
        if (course != null) {
            course.setLast_modify(getCurrentTime());
            // need to set author here!
            Course course1 = courseService.updateCourse(course, course_id);
            if (course1 != null)
                return course_to_courseinfo(course1);
            else return null;
        }
        return null;
    }

    @DeleteMapping(value = "/{course_id}")
    public void deleteCourse(@PathVariable("course_id") int course_id) {
        courseService.deleteCourse(course_id);
    }

    @PostMapping(value = "/{course_id}")
    public String Upload(@RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                saveFile(file.getBytes(), path_name, file.getOriginalFilename());
            } catch (FileNotFoundException e) {
                return "upload failed" + e.getMessage();
            } catch (IOException e) {
                return "upload failed" + e.getMessage();
            }
            return "upload successfully";
        } else {
            return "empty file";
        }
    }

    private void saveFile(byte[] bytes, String path_name, String file_name) throws IOException {
        setPath(path_name);
        BufferedOutputStream stream = null;
        stream = new BufferedOutputStream(new FileOutputStream(new File(path_name + file_name)));
        stream.write(bytes);
        stream.close();
    }

    private void setPath(String path_name) throws IOException {
        File file = new File(path_name);
        if (!file.exists() && !file.isDirectory()) {
            file.mkdirs();
        }
    }

    public String getCurrentTime() {
        return (dateFormat.format(new Date()));
    }
    //#3


    private String course_to_courseinfo(Course course) {
        JSONObject courseinfo = new JSONObject();
        courseinfo.put("course_name", course.getCourse_name());
        courseinfo.put("course_code", course.getCourse_code());
        if (course.getSchool() != null)
            courseinfo.put("school", course.getSchool().getSchoolid());
        courseinfo.put("description", course.getDescription());
        courseinfo.put("professor", course.getProfessor());
        if (course.getAuthor() != null)
            courseinfo.put("author", course.getAuthor().getId());
        courseinfo.put("syllabuspath", course.getSyllabuspath());
        courseinfo.put("last_modify", course.getLast_modify());

        return courseinfo.toString();
    }

    //convert a courseinfo t course
    private Course courseinfo_to_course(Object courseinfo) {
        //Course course = courseinfo
        String course_name = null;
        String course_code = null;
        School school = null;
        String description = null;
        String prof = null;
        User author = null;
        String syllabuspath = null;

        course_name = (String) ((Map) courseinfo).get("course_name");
        course_code = (String) ((Map) courseinfo).get("course_code");

        Integer schoolid = (Integer) ((Map) courseinfo).get("school");
        Integer userid = (Integer) ((Map) courseinfo).get("author");

        description = (String) ((Map) courseinfo).get("description");
        prof = (String) ((Map) courseinfo).get("professor");


        syllabuspath = (String) ((Map) courseinfo).get("syllabuspath");
        if (schoolid != null)
            school = schoolService.findByID(schoolid);
        if (userid != null)
            author = userService.findById(userid);
        Course course = new Course(course_name, course_code, school, description, prof, null, author, syllabuspath);
        return course;
    }
}
