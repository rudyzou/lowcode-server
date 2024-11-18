package com.hongshu.lowcode.designer.domain.config;

import com.hongshu.comnsvc.base.domain.config.KeyValue;
import com.querydsl.core.annotations.QueryEntity;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@DiscriminatorValue("WKV")
@Data
@QueryEntity
@EqualsAndHashCode(callSuper = true)
public class WorkspaceKeyValue extends KeyValue {
    private Long workspaceId;
}
