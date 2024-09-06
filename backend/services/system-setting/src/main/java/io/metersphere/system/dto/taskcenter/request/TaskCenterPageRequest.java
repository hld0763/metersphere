package io.metersphere.system.dto.taskcenter.request;

import io.metersphere.sdk.constants.TaskCenterResourceType;
import io.metersphere.system.dto.sdk.BasePageRequest;
import io.metersphere.sdk.valid.EnumValue;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author: LAN
 * @date: 2024/1/17 11:21
 * @version: 1.0
 */
@Data
public class TaskCenterPageRequest extends BasePageRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description =  "所属模块", requiredMode = Schema.RequiredMode.REQUIRED)
    @EnumValue(enumClass = TaskCenterResourceType.class)
    private String moduleType = TaskCenterResourceType.API_CASE.toString();

}
