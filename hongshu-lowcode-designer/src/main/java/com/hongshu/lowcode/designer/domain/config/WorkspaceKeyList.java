package com.hongshu.lowcode.designer.domain.config;

import com.hongshu.comnsvc.base.domain.config.KeyList;
import com.querydsl.core.annotations.QueryEntity;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@DiscriminatorValue("WKL")
@Data
@QueryEntity
@EqualsAndHashCode(callSuper = true)
public class WorkspaceKeyList extends KeyList {
    private Long workspaceId;
}
