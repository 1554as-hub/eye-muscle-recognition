package com.eyescloud.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@TableName("user_history_file")
@ApiModel(value = "用户历史文件对象" )
public class FileModle implements Serializable {


    @TableId("id")
    private String id;

    private double totalSize;
    private String date;
    private int status;
    private String name ;

    @TableField("file_path")
    private String filePath;

    @TableField("zip_path")
    private String zipPath;


    @Override
    public String toString() {
        return "FileModle{" +
                "id='" + id + '\'' +
                ", totalSize=" + totalSize +
                ", date='" + date + '\'' +
                ", status=" + status +
                ", name='" + name + '\'' +
                ", filePath='" + filePath + '\'' +
                ", zipPath='" + zipPath + '\'' +
                '}';
    }


}
