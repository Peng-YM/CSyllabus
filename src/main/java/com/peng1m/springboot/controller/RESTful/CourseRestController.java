package com.peng1m.springboot.controller.RESTful;

import com.peng1m.springboot.model.Course;
import com.peng1m.springboot.model.User;
import com.peng1m.springboot.model.School;
import com.peng1m.springboot.service.*;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/course")
public class CourseRestController {
    public static final Logger logger = LoggerFactory.getLogger(CourseRestController.class);

    private CourseService courseService;
    private SchoolService schoolService;
    private UserService userService;
    private FileService fileService;
    private CourseTreeService courseTreeService;
    private StarCourseService starCourseService;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    public CourseRestController(CourseService courseService, SchoolService schoolService, UserService userService, FileService fileService, CourseTreeService courseTreeService, StarCourseService starCourseService) throws IOException {
        this.courseService = courseService;
        this.schoolService = schoolService;
        this.userService = userService;
        this.fileService = fileService;
        this.courseTreeService = courseTreeService;
        this.starCourseService = starCourseService;
        fileService.setDataPath();
    }

    //#1 GET api/course
    @GetMapping(value = "")
    public Map getAllCourses() {
        Map<String, List<Integer>> courseids = new HashMap<>();
        courseids.put("course_ids", courseService.getCoursesID());
        return courseids;
    }

    //#2 GET api/course/{course_id}
    @GetMapping(value = "/{course_id}")
    public String getCourse(@PathVariable("course_id") int course_id) {
        Course course = courseService.getCourseByID(course_id);
        if (course != null)
            return course_to_courseinfo(course);
        else return null;
    }

    //get school infos
    //POST api/course/multi
    @PostMapping(value = "/multi")
    public List<Course> findSchools(@RequestBody Map<String, List<Integer>> map) {
        List<Integer> course_ids = map.get("course_ids");
        List<Course> courses = new LinkedList<>();
        for (Integer id : course_ids) {
            courses.add(courseService.getCourseByID(id));
        }
        return courses;
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


    //#4 PUT api/course/{course_id}	+	json
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

    // #5 DELETE api/course/{course_id}
    @DeleteMapping(value = "/{course_id}")
    public void deleteCourse(@PathVariable("course_id") int course_id) {
        // delete course in database
        courseTreeService.deleteBySourceOrTarget(course_id);
        starCourseService.deleteStarCourseByCourseid(course_id);
        courseService.deleteCourse(course_id);
        //delete course syllabus in server
        deletefile(course_id);
    }

    //#6 GET api/course/{course_id}/syllabus
    @GetMapping(value = "/{course_id}/syllabus")
    public @ResponseBody
    HttpEntity<byte[]> download(@PathVariable("course_id") int course_id) throws IOException {
        File file = getFilebyID(course_id);
        byte[] document = FileCopyUtils.copyToByteArray(file);
        HttpHeaders header = new HttpHeaders();
        header.setContentType(new MediaType("application", "pdf"));
        header.set("Content-Disposition", "inline; filename=" + file.getName());
        header.setContentLength(document.length);
        return new HttpEntity<byte[]>(document, header);
    }

    private File getFilebyID(int courseID) throws FileNotFoundException {
        String file_name = fileService.getSyllabusFilenamByCourseID(courseID);
        File file = new File(file_name);
        if (!file.exists()) {
            throw new FileNotFoundException("file with path: " + file_name + "was not found");
        }
        return file;
    }

    //#7 POST api/course/{course_id}/syllabus + PDF
    @PostMapping(value = "/{course_id}/syllabus")
    public String uploadfile(@RequestParam("file") MultipartFile file, @PathVariable("course_id") int course_id) {
        if (!file.isEmpty()) {
            try {
                String filename = fileService.getSyllabusFilenamByCourseID(course_id);
                fileService.saveFile(file.getBytes(), filename);
                //saveFile(file.getBytes(), course_id, file.getOriginalFilename());
            } catch (IOException e) {
                return "upload failed" + e.getMessage();
            }
            return "upload successfully";
        } else {
            //logger.info("User {} is trying to login", userForm.getUsername());
            return "empty file";
        }
    }

    //#8 PUT api/course/{course_id}/syllabus + PDF
    @PutMapping(value = "/{course_id}/syllabus")
    public String updatefile(@RequestParam("file") MultipartFile file, @PathVariable("course_id") int course_id) {
        return uploadfile(file, course_id);
    }

    ;

    //#9 DELETE  api/course/{course_id}/syllabus
    @DeleteMapping(value = "/{course_id}/syllabus")
    public void deletefile(@PathVariable("course_id") int course_id) {
        fileService.deleteSyllabusByCourseID(course_id);
    }

    private String getCurrentTime() {
        return (dateFormat.format(new Date()));
    }


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
        courseinfo.put("courseid", course.getCourseid());
        courseinfo.put("star_num", starCourseService.getCourseStarNum(course.getCourseid()));
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
        Integer courseid = null;

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
