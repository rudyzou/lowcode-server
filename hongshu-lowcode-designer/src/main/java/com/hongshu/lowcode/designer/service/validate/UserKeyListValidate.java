package com.hongshu.lowcode.designer.service.validate;

import com.hongshu.boot.starter.cdi.crud.AddPreAction;
import com.hongshu.boot.starter.cdi.crud.CrudContext;
import com.hongshu.comnsvc.base.domain.config.UserKeyList;
import com.hongshu.comnsvc.base.repository.config.UserKeyListRepository;
import com.hongshu.core.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.pf4j.Extension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class UserKeyListValidate implements AddPreAction<UserKeyList> {
    protected Logger log = LoggerFactory.getLogger(UserKeyListValidate.class);
    @Autowired
    private UserKeyListRepository repository;
    @Override
    public CrudContext<UserKeyList> execute(CrudContext<UserKeyList> context) throws BusinessException {
        List<UserKeyList> userKeyLists = context.getEntityList();
        log.info("UserKeyListValidate repository is null?:{}",repository.toString());
        for (UserKeyList keyList : userKeyLists) {

        }
        return context;
    }
}
