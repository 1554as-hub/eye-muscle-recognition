package com.eyescloud;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileHandlerUtil {



    public boolean isZip(String fileName) {
        if(fileName == null) {
            return false;
        }
        if(fileName.endsWith(".rar")) {
            return true;

        }else if(fileName.endsWith(".7z")){
            return  true;

        } else if(fileName.endsWith(".zip")) {
            return true;

        }else
        return false;
    }
}
