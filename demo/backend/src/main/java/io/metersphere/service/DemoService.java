package io.metersphere.service;

import io.metersphere.base.domain.SystemParameterExample;
import io.metersphere.base.mapper.SystemParameterMapper;
import io.metersphere.dto.ModuleDTO;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DemoService {
    private static final String PREFIX = "metersphere.module.";
    @Resource
    private SystemParameterMapper systemParameterMapper;

    public List<ModuleDTO> listModule() {
        SystemParameterExample example = new SystemParameterExample();
        example.createCriteria().andParamKeyLike(PREFIX + "%");
        return systemParameterMapper.selectByExample(example).stream().map(systemParameter->{
            ModuleDTO moduleDTO = new ModuleDTO();
            moduleDTO.setName(StringUtils.removeStart(systemParameter.getParamKey(), PREFIX));
            moduleDTO.setStatus(systemParameter.getParamValue());
            return moduleDTO;
        }).collect(Collectors.toList());
    }
}
