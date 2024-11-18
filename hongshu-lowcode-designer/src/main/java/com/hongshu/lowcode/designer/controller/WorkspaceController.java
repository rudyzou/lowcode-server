package com.hongshu.lowcode.designer.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.hongshu.boot.starter.api.Result;
import com.hongshu.core.exception.BusinessException;
import com.hongshu.lowcode.designer.assembler.workspace.WorkspaceDTO;
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
@RequestMapping("/workspace")
public class WorkspaceController {

    @Value(value = "hongshu.lowcode.designer.root")
    private String designerRoot;

    @GetMapping("/list")
    public Result list() throws BusinessException{
        checkDesignerRoot();
        String userName = StpUtil.getLoginIdAsString();
       String fullPath = designerRoot + File.separator + userName;
       boolean isExist = FileUtil.exist(fullPath);
       if (!isExist){
           return Result.buildSuccess(new ArrayList<>());
       }
       File[]  files = FileUtil.ls(fullPath);
        List<WorkspaceDTO> workspaceDTOList = new ArrayList<>();
        for (File file : files) {
            if (file.isDirectory()) {
                WorkspaceDTO workspaceDTO = new WorkspaceDTO();
                workspaceDTO.setName(file.getName());
                workspaceDTO.setFullPath(file.getAbsolutePath());
                workspaceDTOList.add(workspaceDTO);
            }
        }
       return Result.buildSuccess(workspaceDTOList);
    }

    private void checkDesignerRoot() {
        if (StrUtil.isBlank(designerRoot)){
            throw BusinessException.build("error-designer-root-not-configured");
        }
    }

    @GetMapping
    public Result getWorkspace(@RequestParam(value = "workspaceName") String workspaceName){
        checkDesignerRoot();
        String userName = StpUtil.getLoginIdAsString();
        // 读取workspace.json
        String workspaceSettingFullPath = designerRoot + File.separator +userName
                + File.separator + workspaceName + File.separator
                + File.separator + "workspace.json";
        if(FileUtil.isFile(workspaceSettingFullPath)){
            String workspaceSettingStr = FileUtil.readString(workspaceSettingFullPath, Charset.defaultCharset());
            JSONObject workspaceSetting = JSON.parseObject(workspaceSettingStr);
            return Result.buildSuccess(workspaceSetting);
        }
        return Result.buildSuccess();
    }

    @PutMapping
    public Result createWorkspace(@RequestBody WorkspaceDTO settings){
        checkDesignerRoot();
        String userName = StpUtil.getLoginIdAsString();
        String workspaceName = settings.getName();
        String directoryName = settings.getDirectoryName();
        if(StrUtil.isBlank(directoryName)){
            directoryName = workspaceName;
        }
        String workspaceSettingFullPath = designerRoot + File.separator +userName
                + File.separator + workspaceName + File.separator
                + File.separator + "workspace.json";
        FileUtil.writeBytes(JSONObject.toJSONString(settings).getBytes(StandardCharsets.UTF_8)
                ,new File(workspaceSettingFullPath));
        return Result.buildSuccess();
    }

    @PatchMapping
    public Result updateWorkspace(@RequestBody WorkspaceDTO settings){
        checkDesignerRoot();
        String userName = StpUtil.getLoginIdAsString();
        String workspaceName = settings.getName();
        String directoryName = settings.getDirectoryName();
        if(StrUtil.isBlank(directoryName)){
            directoryName = workspaceName;
        }
        String workspaceSettingFullPath = designerRoot + File.separator +userName
                + File.separator + workspaceName + File.separator
                + File.separator + "workspace.json";
        FileUtil.writeBytes(JSONObject.toJSONString(settings).getBytes(StandardCharsets.UTF_8)
                ,new File(workspaceSettingFullPath));
        return Result.buildSuccess();
    }

    @DeleteMapping
    public Result removeWorkspace(@RequestParam(value = "workspaceName") String workspaceName){
        checkDesignerRoot();
        String userName = StpUtil.getLoginIdAsString();
        String workspaceFullPath = designerRoot + File.separator +userName
                + File.separator + workspaceName ;
        if(FileUtil.exists(Paths.get(workspaceFullPath), false) && FileUtil.isDirectory(workspaceFullPath)){
            FileUtil.del(workspaceFullPath);
            return Result.buildSuccess();
        }
        return Result.buildSuccess();
    }

}
