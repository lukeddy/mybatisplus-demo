package com.luke.mybatisplus.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.luke.mybatisplus.entity.Account;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.format.datetime.joda.LocalDateTimeParser;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.time.LocalDateTime;

/**
 * 自动填充某些表格字段
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    /**
     * 自动填充创建时间和创建人ID
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        if(metaObject.hasSetter("createTime")){
            //this.strictInsertFill(metaObject,"createTime", ()->LocalDateTime.now(),LocalDateTime.class);
            this.strictInsertFill(metaObject, "createTime", () -> LocalDateTime.now(), LocalDateTime.class);
        }
        if(metaObject.hasSetter("createAccountId")){
            //从Session中获取用户信息
            Account account= (Account) (RequestContextHolder.getRequestAttributes()
                    .getAttribute("account", RequestAttributes.SCOPE_SESSION));
            if(null!=account){
                this.strictInsertFill(metaObject,"createAccountId",Long.class,account.getAccountId());
            }
        }
    }

    /**
     * 自动填充修改时间和修改人ID
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        if(metaObject.hasSetter("modifiedTime")){
            //this.strictUpdateFill(metaObject,"modifiedTime", ()->LocalDateTime.now(),LocalDateTime.class);
            this.strictUpdateFill(metaObject, "modifiedTime", () -> LocalDateTime.now(), LocalDateTime.class);
        }
        if(metaObject.hasSetter("modifiedAccountId")){
            //从Session中获取用户信息
            Account account= (Account) (RequestContextHolder.getRequestAttributes()
                    .getAttribute("account", RequestAttributes.SCOPE_SESSION));
            if(null!=account){
                this.strictUpdateFill(metaObject,"modifiedAccountId",Long.class,account.getAccountId());
            }
        }
    }
}
