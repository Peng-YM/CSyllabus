package com.peng1m.springboot.service.impl;

import com.peng1m.springboot.service.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.*;

@Service
public class FileServiceImpl implements FileService {

    final String data_path = "data/course_syllabus/";

    @Override
    public String getFileFor(String fileName) {
        return null;
    }

    @Override
    public void setDataPath() {
        setPath(data_path);
    }

    private void setPath(String path) {
        File file = new File(path);
        if (!file.exists() && !file.isDirectory()) {
            file.mkdirs();
        }

    }

    @Override
    public String getSyllabusFilenamByCourseID(int course_id) {
        return data_path + String.valueOf(course_id) + ".syllabus";
    }

    @Override
    public File getSyllabusByCourseID(int courseID) throws FileNotFoundException {
        String file_name = getSyllabusFilenamByCourseID(courseID);
        File file = new File(file_name);
        if (!file.exists()) {
            throw new FileNotFoundException("file with path: " + file_name + "was not found");
        }
        return file;
    }

    @Override
    public String deleteSyllabusByCourseID(@PathVariable("course_id") int course_id) {
        //String fileName = data_path + String.valueOf(course_id) + ".syllabus";
        String fileName = getSyllabusFilenamByCourseID(course_id);
        if (delete(fileName)) {
            return "delete successfully";
        } else return "delete failed";
    }


    @Override
    public boolean delete(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            System.out.println("删除文件失败：" + fileName + "文件不存在");
            return false;
        } else {
            if (file.isFile()) {

                return deleteFile(fileName);
            } else {
                return deleteDirectory(fileName);
            }
        }
    }

    /**
     * 删除单个文件
     *
     * @param fileName 被删除文件的文件名
     * @return 单个文件删除成功返回true, 否则返回false
     */
    @Override
    public boolean deleteFile(String fileName) {
        File file = new File(fileName);
        if (file.isFile() && file.exists()) {
            file.delete();
            System.out.println("删除单个文件" + fileName + "成功！");
            return true;
        } else {
            System.out.println("删除单个文件" + fileName + "失败！");
            return false;
        }
    }

    /**
     * 删除目录（文件夹）以及目录下的文件
     *
     * @param dir 被删除目录的文件路径
     * @return 目录删除成功返回true, 否则返回false
     */
    @Override
    public boolean deleteDirectory(String dir) {
        //如果dir不以文件分隔符结尾，自动添加文件分隔符
        if (!dir.endsWith(File.separator)) {
            dir = dir + File.separator;
        }
        File dirFile = new File(dir);
        //如果dir对应的文件不存在，或者不是一个目录，则退出
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            System.out.println("删除目录失败" + dir + "目录不存在！");
            return false;
        }
        boolean flag = true;
        //删除文件夹下的所有文件(包括子目录)
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            //删除子文件
            if (files[i].isFile()) {
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag) {
                    break;
                }
            }
            //删除子目录
            else {
                flag = deleteDirectory(files[i].getAbsolutePath());
                if (!flag) {
                    break;
                }
            }
        }

        if (!flag) {
            System.out.println("删除目录失败");
            return false;
        }

        //删除当前目录
        if (dirFile.delete()) {
            System.out.println("删除目录" + dir + "成功！");
            return true;
        } else {
            System.out.println("删除目录" + dir + "失败！");
            return false;
        }
    }

    @Override
    public void saveFile(byte[] bytes, String file_name) throws IOException {
        BufferedOutputStream stream = null;
        //String[] file_name_split = file_name.split("\\.");
        //String file_type = file_name_split[file_name_split.length - 1];
        stream = new BufferedOutputStream(new FileOutputStream(new File(file_name)));
        stream.write(bytes);
        stream.close();
    }
}
