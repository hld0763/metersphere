package io.metersphere.api.dto.definition;

import io.metersphere.api.dto.ApiRunModeRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
public class ApiTestCaseBatchRunRequest extends ApiTestCaseBatchRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "接口pk")
    @Size(max = 50, message = "{api_definition.id.length_range}")
    private String apiDefinitionId;

    @Valid
    @Schema(description = "运行模式配置")
    private ApiRunModeRequest runModeConfig;
}
