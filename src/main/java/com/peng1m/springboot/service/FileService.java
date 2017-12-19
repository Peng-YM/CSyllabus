package com.peng1m.springboot.service;

import org.springframework.web.bind.annotation.PathVariable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public interface FileService {

    abstract String getFileFor(String fileName);

    void setDataPath();

    String getSyllabusFilenamByCourseID(int course_id);

    File getSyllabusByCourseID(int courseID) throws FileNotFoundException;

    String deleteSyllabusByCourseID(@PathVariable("course_id") int course_id);

    boolean delete(String fileName);

    boolean deleteFile(String fileName);

    boolean deleteDirectory(String dir);

    void saveFile(byte[] bytes, String file_name) throws IOException;
}
