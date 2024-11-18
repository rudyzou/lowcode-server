package com.hongshu.lowcode.designer.domain.workspace;

import com.hongshu.jpa.domain.AuditingEntity;
import com.querydsl.core.annotations.QueryEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@Table(name = "LC_GIT_CONFIG")
@QueryEntity
@EqualsAndHashCode(callSuper = true)
public class GitConfig extends AuditingEntity {
    private String userName;
    private String password;
    private String publicKey;
    private String domain;
    private Long workspaceId;
}
