package com.eyescloud.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.eyescloud.entity.FileModle;
import com.eyescloud.entity.UserHistoryFile;

import java.util.List;


public interface FileHandlerService extends IService<FileModle> {

    void saveFile(FileModle fileModle);
    void saveMiddleFile(String userId , String fileId);

    UserHistoryFile getUserHistory(String userId);
    void updateFile(FileModle fileModle);
    List<FileModle> getUserFileHistory(String userId);
    IPage<FileModle> getUserFileHistory(Page<FileModle> page,String userId);
}
