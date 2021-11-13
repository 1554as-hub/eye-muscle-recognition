package com.eyescloud.mapper;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eyescloud.entity.FileModle;
import com.eyescloud.entity.UserHistoryFile;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FileHandlerMapper extends BaseMapper<FileModle> {



    @Insert("insert into user_updatefile_middle_info (uid, hid) values (#{userId} , #{fileId})")
    public void saveMiddlerFile(@Param("userId") String userId ,@Param("fileId") String fileId);

    @Select(" select mid.uid uid , uf.id fid , uf.status status , uf.name name , uf.date date ,uf.file_path filePath ," +
            " uf.totalSize totalSize , uf.zip_path zipPath " +
            " from user_updatefile_middle_info mid , user_history_file uf " +
            " where uid = #{userId} and mid.hid = uf.id")
    UserHistoryFile getUserHistory(String userId);


    @Select("  select  uf.id , uf.status status , uf.name name , uf.date date ,uf.file_path filePath ," +
            "  uf.totalSize totalSize , uf.zip_path zipPath " +
            "  from user_updatefile_middle_info mid , user_history_file uf " +
            "  where uid = #{userId} and mid.hid = uf.id")
    List<FileModle> getUserFileHistory(String userId);

    @Select("  select  uf.id , uf.status status , uf.name name , uf.date date ,uf.file_path filePath ," +
            "  uf.totalSize totalSize , uf.zip_path zipPath " +
            "  from user_updatefile_middle_info mid , user_history_file uf " +
            "  ${ew.customSqlSegment} and mid.hid = uf.id")
    IPage<FileModle> getUserFileHistory(Page<?> page
            , @Param(Constants.WRAPPER)QueryWrapper<FileModle> wrapper);

}
