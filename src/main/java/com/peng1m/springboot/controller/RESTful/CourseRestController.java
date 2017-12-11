package com.peng1m.springboot.controller.RESTful;

import com.peng1m.springboot.model.Course;
import com.peng1m.springboot.service.CourseService;
import com.peng1m.springboot.service.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/course")
public class CourseRestController {

    private CourseService courseService;

    @Autowired
    public CourseRestController(CourseService courseService) {
        this.courseService = courseService;
    }

    String path_name = "Data/";

    //#1 GET api/course
    @GetMapping(value = "")
    public List<Integer> getAllCourses() {
        return courseService.getCoursesID();
    }

    //#2 GET api/course/{course_id}
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

    //#3
}
