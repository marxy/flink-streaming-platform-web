package com.flink.streaming.web.mapper;

import com.flink.streaming.web.model.entity.SavepointBackup;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SavepointBackupMapper {

    int insert(SavepointBackup savepointBackup);

    List<SavepointBackup> selectByLimt10(@Param("jobConfigId") Long jobConfigId);

    List<SavepointBackup> selectByAfterLimt10(@Param("jobConfigId") Long jobConfigId);

    SavepointBackup getSavepointBackupById(@Param("jobConfigId") Long jobConfigId, @Param("id") Long id);

    int deleteSavepoint(@Param("jobConfigId") Long jobConfigId);
}
