package com.hongshu.lowcode.designer.assembler.workspace;


import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ProjectDTO implements Serializable {
    private String name;
    private String directoryName;
    private String workspaceDirectoryName;
    private String fullPath;
    private Date dateCreated;
}
