package com.peng1m.springboot.service;

import java.io.IOException;

public interface FileService {

    abstract String getFileFor(String fileName);

    boolean delete(String fileName);

    boolean deleteFile(String fileName);

    boolean deleteDirectory(String dir);

    void saveFile(byte[] bytes, String file_name) throws IOException;
}
