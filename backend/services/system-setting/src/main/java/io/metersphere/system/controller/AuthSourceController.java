package io.metersphere.system.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import io.metersphere.sdk.constants.PermissionConstants;
import io.metersphere.sdk.dto.BasePageRequest;
import io.metersphere.sdk.log.annotation.Log;
import io.metersphere.sdk.log.constants.OperationLogModule;
import io.metersphere.sdk.log.constants.OperationLogType;
import io.metersphere.sdk.util.PageUtils;
import io.metersphere.sdk.util.Pager;
import io.metersphere.system.domain.AuthSource;
import io.metersphere.system.request.AuthSourceRequest;
import io.metersphere.system.service.AuthSourceService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/authsource")
public class AuthSourceController {
    @Resource
    private AuthSourceService authSourceService;

    @PostMapping("/list")
    @Operation(summary = "认证设置列表查询")
    @RequiresPermissions(PermissionConstants.SYSTEM_SETTING_READ)
    public Pager<List<AuthSource>> list(@Validated @RequestBody BasePageRequest request) {
        Page<Object> page = PageHelper.startPage(request.getCurrent(), request.getPageSize(),
                StringUtils.isNotBlank(request.getSortString()) ? request.getSortString() : "create_time desc");
        return PageUtils.setPageInfo(page, authSourceService.list());
    }

    @PostMapping("/add")
    @Operation(summary = "新增认证设置")
    @RequiresPermissions(PermissionConstants.SYSTEM_SETTING_READ_CREAT)
    @Log(type = OperationLogType.ADD, module = OperationLogModule.SYSTEM_PARAMETER_SETTING,
            details = "认证设置")
    public void add(@Validated @RequestBody AuthSourceRequest authSource) {
        authSourceService.addAuthSource(authSource);
    }

    @PostMapping("/update")
    @Operation(summary = "更新认证设置")
    @RequiresPermissions(PermissionConstants.SYSTEM_SETTING_READ_UPDATE)
    @Log(type = OperationLogType.UPDATE, module = OperationLogModule.SYSTEM_PARAMETER_SETTING,
            details = "认证设置", sourceId = "#authSource.id")
    public void update(@Validated @RequestBody AuthSourceRequest authSource) {
        authSourceService.updateAuthSource(authSource);
    }

    @GetMapping("/get/{id}")
    @Operation(summary = "获取认证设置详细信息")
    @RequiresPermissions(PermissionConstants.SYSTEM_SETTING_READ)
    public AuthSource get(@PathVariable(value = "id") String id) {
        return authSourceService.getAuthSource(id);
    }

    @GetMapping("/delete/{id}")
    @Operation(summary = "删除认证设置")
    @RequiresPermissions(PermissionConstants.SYSTEM_SETTING_READ_DELETE)
    @Log(type = OperationLogType.DELETE, module = OperationLogModule.SYSTEM_PARAMETER_SETTING,
            details = "认证设置", sourceId = "#id")
    public void delete(@PathVariable(value = "id") String id) {
        authSourceService.deleteAuthSource(id);
    }


    @GetMapping("/update/{authId}/status/{status}")
    @Log(type = OperationLogType.UPDATE, module = OperationLogModule.SYSTEM_PARAMETER_SETTING,
            details = "认证设置", sourceId = "#authSource.authId")
    public void updateStatus(@PathVariable(value = "authId") String authId, @PathVariable("status") String status) {
        authSourceService.updateStatus(authId, status);
    }
}
