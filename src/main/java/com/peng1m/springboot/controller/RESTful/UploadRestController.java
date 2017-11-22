package com.peng1m.springboot.controller.RESTful;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/upload")
public class UploadRestController {
    private static final Logger logger = LoggerFactory.getLogger(UploadRestController.class);

    private static String UPLOAD_FOLDER = "";

    // Single file upload
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @PostMapping("/")
    public ResponseEntity<?> uploadFile(
            @RequestParam("file")MultipartFile file){
        logger.info("Single file upload");
        if (file.isEmpty()){
            return new ResponseEntity<>("Please select a file!", HttpStatus.OK);
        }
        try {
            saveFiles(Arrays.asList(file));
        }
        catch (IOException e){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Successfully uploaded - "
                + file.getOriginalFilename(), new HttpHeaders(), HttpStatus.OK);
    }

    //multi files upload
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @PostMapping("/multi")
    public ResponseEntity<?> uploadMultiFile(
            @RequestParam("extraField") String extraField,
            @RequestParam("files") MultipartFile[] files) {
        logger.info("Multiple file upload");

        String uploadedFileName = Arrays.stream(files).map(x->x.getOriginalFilename())
                .filter(x -> !StringUtils.isEmpty(x)).collect(Collectors.joining(", "));

        if (StringUtils.isEmpty(uploadedFileName)){
            return new ResponseEntity<>("Please select a file", HttpStatus.OK);
        }

        try {
            saveFiles(Arrays.asList(files));
        }
        catch (IOException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("Successfully uploaded - " + uploadedFileName, HttpStatus.OK);
    }

    // save files
    private void saveFiles(List<MultipartFile> files) throws IOException{
        for (MultipartFile file: files){
            if (file.isEmpty()){
                continue;
            }

            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOAD_FOLDER + file.getOriginalFilename());
            Files.write(path, bytes);
        }
    }
}
