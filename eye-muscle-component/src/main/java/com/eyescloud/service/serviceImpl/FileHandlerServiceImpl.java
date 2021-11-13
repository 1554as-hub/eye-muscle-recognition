package com.eyescloud.service.serviceImpl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.eyescloud.entity.FileModle;
import com.eyescloud.entity.UserHistoryFile;
import com.eyescloud.mapper.FileHandlerMapper;
import com.eyescloud.service.FileHandlerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class FileHandlerServiceImpl extends ServiceImpl<FileHandlerMapper ,FileModle> implements FileHandlerService {

    @Resource
    private FileHandlerMapper fileHandlerMapper;

    @Override
    public void saveFile(FileModle fileModle) {
        fileHandlerMapper.insert(fileModle);
    }

    @Override
    public void saveMiddleFile(String userId, String fileId) {
        fileHandlerMapper.saveMiddlerFile(userId , fileId);
    }

    @Override
    public UserHistoryFile getUserHistory(String userId) {
        return fileHandlerMapper.getUserHistory(userId);
    }

    @Override
    public void updateFile(FileModle fileModle) {
        fileHandlerMapper.updateById(fileModle);
    }

    @Override
    public List<FileModle> getUserFileHistory(String userId) {
        return fileHandlerMapper.getUserFileHistory(userId);
    }

    public IPage<FileModle> getUserFileHistory(Page<FileModle> page,String userId) {
        QueryWrapper<FileModle> query = Wrappers.query();
        query.eq("uid" , userId);

        return fileHandlerMapper.getUserFileHistory(page , query);

    }

}
