package io.metersphere.controller;

import io.metersphere.controller.handler.ResultHolder;
import io.metersphere.dto.ModuleDTO;
import io.metersphere.service.DemoService;
import jakarta.annotation.Resource;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("demo")
public class DemoController {

    @Resource
    private DiscoveryClient discoveryClient;
    @Resource
    private DemoService demoService;

    @GetMapping("/module/list")
    public Mono<ResultHolder> listModule() {
        List<ModuleDTO> moduleList = demoService.listModule();
        discoveryClient.getServices().forEach(service->{
            moduleList.stream().filter(module->service.equalsIgnoreCase(module.getName()))
                    .findAny().orElseGet(()->{
                        ModuleDTO module = new ModuleDTO();
                        moduleList.add(module);
                        module.setName(service);
                        return module;
                    }).setPort(discoveryClient.getInstances(service).get(0).getPort());
        });
        return Mono.just(ResultHolder.success(moduleList));
    }

}
