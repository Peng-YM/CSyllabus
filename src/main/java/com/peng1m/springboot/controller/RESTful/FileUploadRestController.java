package com.peng1m.springboot.controller.RESTful;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.List;

@RestController
@RequestMapping("/api/upload")
public class FileUploadRestController {

    String path_name = "Data/";


    @PostMapping(value = "/")
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

    @RequestMapping(value = "/batch", method = RequestMethod.POST)
    public @ResponseBody
    String batchUpload(HttpServletRequest request) {
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
        MultipartFile file = null;
        for (int i = 0; i < files.size(); ++i) {
            file = files.get(i);
            if (!file.isEmpty()) {
                try {
                    byte[] bytes = file.getBytes();
                    saveFile(bytes, path_name, file.getOriginalFilename());
                } catch (Exception e) {
                    System.out.print(e.getMessage());
                    return "You failed to upload " + i + " => " + e.getMessage();
                }
            } else {
                return "You failed to upload " + i + " because the file was empty.";
            }
        }
        return "upload successful";
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
}
