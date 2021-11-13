package com.eyescloud.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserHistoryFile implements Serializable {

    private String uid;

    private List<FileModle> fileModles;

    @Override
    public String toString() {
        return "UserHistoryFile{" +
                "uid='" + uid + '\'' +
                ", fileModles=" + fileModles +
                '}';
    }


}
