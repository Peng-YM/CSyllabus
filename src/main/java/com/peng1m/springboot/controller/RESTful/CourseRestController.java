package com.peng1m.springboot.controller.RESTful;

import com.peng1m.springboot.model.Course;
import com.peng1m.springboot.model.User;
import com.peng1m.springboot.model.School;
import com.peng1m.springboot.service.FileService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.json.JSONObject;
import com.peng1m.springboot.service.CourseService;
import com.peng1m.springboot.service.SchoolService;
import com.peng1m.springboot.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
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
    private FileService fileService;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    public CourseRestController(CourseService courseService, SchoolService schoolService, UserService userService, FileService fileService) throws IOException {
        this.courseService = courseService;
        this.schoolService = schoolService;
        this.userService = userService;
        this.fileService = fileService;
        setPath();
    }


    final String data_path = "data/course_syllabus/";

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

    // #5 DELETE api/course/{course_id}
    @DeleteMapping(value = "/{course_id}")
    public void deleteCourse(@PathVariable("course_id") int course_id) {
        courseService.deleteCourse(course_id);
    }

    //#6 GET api/course/{course_id}/syllabus
    @GetMapping(value = "/{course_id}/syllabus")
    @ResponseBody
    public FileSystemResource getFile(@PathVariable("course_id") int course_id) {
        return new FileSystemResource(data_path + String.valueOf(course_id) + ".syllabus");
        //return new FileSystemResource(fileService.getFileFor(fileName));
    }


    //#7 POST api/course/{course_id}/syllabus + PDF
    @PostMapping(value = "/{course_id}/syllabus")
    public String uploadfile(@RequestParam("file") MultipartFile file, @PathVariable("course_id") int course_id) {
        if (!file.isEmpty()) {
            try {
                String filename = getfile_nameById(course_id);
                fileService.saveFile(file.getBytes(), filename);
                //saveFile(file.getBytes(), course_id, file.getOriginalFilename());
            } catch (FileNotFoundException e) {
                return "upload failed" + e.getMessage();
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
    public String deletefile(@PathVariable("course_id") int course_id) {
        //String fileName = data_path + String.valueOf(course_id) + ".syllabus";
        String fileName = getfile_nameById(course_id);
        if (fileService.delete(fileName)) {
            return "delete successfully";
        } else return "delete failed";
    }

    private String getfile_nameById(int course_id) {
        return data_path + String.valueOf(course_id) + ".syllabus";
    }

    /*
        private void saveFile(byte[] bytes, int course_id, String file_name) throws IOException {
            String path = setPath(course_id);
            BufferedOutputStream stream = null;
            //String[] file_name_split = file_name.split("\\.");
            //String file_type = file_name_split[file_name_split.length - 1];
            stream = new BufferedOutputStream(new FileOutputStream(new File(path + String.valueOf(course_id) + ".syllabus")));
            stream.write(bytes);
            stream.close();
        }
    */
    private String setPath() throws IOException {
        String path = data_path;

        File file = new File(path);
        if (!file.exists() && !file.isDirectory()) {
            file.mkdirs();
        }
        return path;
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
