package io.metersphere.dto;

import lombok.Data;

@Data
public class ModuleDTO {
    private String name;
    private Integer port;
    private String status;
}
