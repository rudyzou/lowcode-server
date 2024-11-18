package com.hongshu.lowcode.designer.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.hongshu.boot.starter.api.Result;
import com.hongshu.core.exception.BusinessException;
import com.hongshu.lowcode.designer.assembler.workspace.ProjectDTO;
import com.hongshu.lowcode.designer.domain.workspace.projectSettingProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/project")
public class ProjectController {
    @Value(value = "hongshu.lowcode.designer.root")
    private String designerRoot;
    @GetMapping("/ls")
    public Result list(@RequestParam(value = "workspaceName") String workspaceName){
        checkDesignerRoot();
        String userName = StpUtil.getLoginIdAsString();
        String wsFullPath = designerRoot + File.separator +userName + File.separator + workspaceName;
        boolean isExist = FileUtil.exist(wsFullPath);
        if (!isExist){
            return Result.buildSuccess(new ArrayList<>());
        }
        File[]  files = FileUtil.ls(wsFullPath);
        List<ProjectDTO> projectList = new ArrayList<>();
        for (File file : files) {
            if (file.isDirectory()) {
                ProjectDTO project = new ProjectDTO();
                project.setDirectoryName(file.getName());
                project.setFullPath(file.getAbsolutePath());
                // 读取project.json
                String projectSettingFullPath = designerRoot + File.separator +userName
                        + File.separator + workspaceName + File.separator
                        + file.getName() + File.separator + "project.json";
                if(FileUtil.isFile(projectSettingFullPath)){
                   String projectSettingStr = FileUtil.readString(projectSettingFullPath, Charset.defaultCharset());
                    JSONObject projectSetting = JSON.parseObject(projectSettingStr);
                    String projectName = projectSetting.getString((projectSettingProperties.PROJECT_NAME));
                    project.setName(projectName);
                }
                projectList.add(project);
            }
        }
       return Result.buildSuccess(projectList);
    }
    private void checkDesignerRoot() {
        if (StrUtil.isBlank(designerRoot)){
            throw BusinessException.build("error-designer-root-not-configured");
        }
    }
    @GetMapping
    public Result get(@RequestParam(value = "workspaceName") String workspaceName
            ,@RequestParam(value = "projectName") String projectName){
        checkDesignerRoot();
        String userName = StpUtil.getLoginIdAsString();
        // 读取project.json
        String projectSettingFullPath = designerRoot + File.separator +userName
                + File.separator + workspaceName + File.separator
                + projectName + File.separator + "project.json";
        if(FileUtil.isFile(projectSettingFullPath)){
            String projectSettingStr = FileUtil.readString(projectSettingFullPath, Charset.defaultCharset());
            JSONObject projectSetting = JSON.parseObject(projectSettingStr);
            return Result.buildSuccess(projectSetting);
        }
        return Result.buildSuccess();
    }
    @PutMapping
    public Result create(@RequestBody ProjectDTO settings){
        checkDesignerRoot();
        String userName = StpUtil.getLoginIdAsString();
        String workspaceName = settings.getWorkspaceDirectoryName();
        String projectName = settings.getName();
        String directoryName = settings.getDirectoryName();
        if(StrUtil.isBlank(directoryName)){
            directoryName = projectName;
        }
        String projectSettingFullPath = designerRoot + File.separator +userName
                + File.separator + workspaceName + File.separator
                + projectName + File.separator + "project.json";
        FileUtil.writeBytes(JSONObject.toJSONString(settings).getBytes(StandardCharsets.UTF_8)
                ,new File(projectSettingFullPath));
        return Result.buildSuccess();
    }

    @PatchMapping
    public Result update(@RequestBody ProjectDTO settings){
        checkDesignerRoot();
        String userName = StpUtil.getLoginIdAsString();
        String workspaceName = settings.getWorkspaceDirectoryName();
        String projectName = settings.getName();
        String directoryName = settings.getDirectoryName();
        if(StrUtil.isBlank(directoryName)){
            directoryName = projectName;
        }
        String projectSettingFullPath = designerRoot + File.separator +userName
                + File.separator + workspaceName + File.separator
                + projectName + File.separator + "project.json";
        FileUtil.writeBytes(JSONObject.toJSONString(settings).getBytes(StandardCharsets.UTF_8)
                ,new File(projectSettingFullPath));
        return Result.buildSuccess();
    }

    @DeleteMapping
    public Result remove(@RequestParam(value = "workspaceName") String workspaceName
            ,@RequestParam(value = "projectName") String projectName){
        checkDesignerRoot();
        String userName = StpUtil.getLoginIdAsString();
        String projectFullPath = designerRoot + File.separator +userName
                + File.separator + workspaceName + File.separator
                + projectName;
        if(FileUtil.exists(Paths.get(projectFullPath), false) && FileUtil.isDirectory(projectFullPath)){
            FileUtil.del(projectFullPath);
            return Result.buildSuccess();
        }
        return Result.buildSuccess();
    }
 
}
