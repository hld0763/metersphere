package io.metersphere.project.service;

import io.metersphere.api.domain.ApiScenario;
import io.metersphere.bug.domain.Bug;
import io.metersphere.functional.domain.CaseReview;
import io.metersphere.load.domain.LoadTest;
import io.metersphere.plan.domain.TestPlan;
import io.metersphere.project.dto.MessageTemplateFieldDTO;
import io.metersphere.project.dto.MessageTemplateResultDTO;
import io.metersphere.sdk.constants.TemplateScene;
import io.metersphere.sdk.dto.ApiDefinitionCaseDTO;
import io.metersphere.sdk.dto.FunctionalCaseMessageDTO;
import io.metersphere.sdk.dto.OptionDTO;
import io.metersphere.sdk.util.Translator;
import io.metersphere.system.domain.CustomField;
import io.metersphere.system.domain.CustomFieldExample;
import io.metersphere.system.domain.Schedule;
import io.metersphere.system.mapper.CustomFieldMapper;
import io.metersphere.system.notice.constants.NoticeConstants;
import io.metersphere.system.notice.utils.MessageTemplateUtils;
import io.metersphere.ui.domain.UiScenario;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
@Transactional(rollbackFor = Exception.class)
public class NoticeTemplateService {

    @Resource
    protected CustomFieldMapper customFieldMapper;

    public List<MessageTemplateFieldDTO> getDomainTemplateFields(String projectId, String taskType) {
        List<MessageTemplateFieldDTO> messageTemplateFieldDTOList = new ArrayList<>();
        switch (taskType) {
            case NoticeConstants.TaskType.API_DEFINITION_TASK -> {
                Field[] allFields = FieldUtils.getAllFields(ApiDefinitionCaseDTO.class);
                addOptionDto(messageTemplateFieldDTOList, allFields, null);
                addCustomFiled(messageTemplateFieldDTOList, projectId, TemplateScene.API.toString());
                //TODO：获取报告
            }
            case NoticeConstants.TaskType.API_SCENARIO_TASK -> {
                Field[] allFields = FieldUtils.getAllFields(ApiScenario.class);
                addOptionDto(messageTemplateFieldDTOList, allFields, "api_scenario_");
                //TODO：获取报告
            }
            case NoticeConstants.TaskType.TEST_PLAN_TASK -> {
                Field[] allFields = FieldUtils.getAllFields(TestPlan.class);
                addOptionDto(messageTemplateFieldDTOList, allFields, "test_plan_");
                addCustomFiled(messageTemplateFieldDTOList, projectId, TemplateScene.TEST_PLAN.toString());
                //TODO：获取报告
            }
            case NoticeConstants.TaskType.CASE_REVIEW_TASK -> {
                Field[] allFields = FieldUtils.getAllFields(CaseReview.class);
                addOptionDto(messageTemplateFieldDTOList, allFields, "case_review_");
                //TODO：获取报告
            }
            case NoticeConstants.TaskType.FUNCTIONAL_CASE_TASK -> {
                Field[] allFields = FieldUtils.getAllFields(FunctionalCaseMessageDTO.class);
                addOptionDto(messageTemplateFieldDTOList, allFields, null);
                addCustomFiled(messageTemplateFieldDTOList, projectId, TemplateScene.FUNCTIONAL.toString());
                //TODO：获取报告
            }
            case NoticeConstants.TaskType.BUG_TASK -> {
                Field[] allFields = FieldUtils.getAllFields(Bug.class);
                addOptionDto(messageTemplateFieldDTOList, allFields, "bug_");
                addCustomFiled(messageTemplateFieldDTOList, projectId, TemplateScene.BUG.toString());
                //TODO：获取报告
            }
            case NoticeConstants.TaskType.UI_SCENARIO_TASK -> {
                Field[] allFields = FieldUtils.getAllFields(UiScenario.class);
                addOptionDto(messageTemplateFieldDTOList, allFields, "ui_");
                addCustomFiled(messageTemplateFieldDTOList, projectId, TemplateScene.UI.toString());
                //TODO：获取报告
            }
            case NoticeConstants.TaskType.LOAD_TEST_TASK -> {
                Field[] allFields = FieldUtils.getAllFields(LoadTest.class);
                addOptionDto(messageTemplateFieldDTOList, allFields, "load_");
                //TODO：获取报告
            }
            case NoticeConstants.TaskType.SCHEDULE_TASK -> {
                Field[] allFields = FieldUtils.getAllFields(Schedule.class);
                addOptionDto(messageTemplateFieldDTOList, allFields, "schedule_");
                //TODO：获取报告
            }
            case NoticeConstants.TaskType.JENKINS_TASK -> {
                MessageTemplateFieldDTO messageTemplateFieldOperator = new MessageTemplateFieldDTO();
                messageTemplateFieldOperator.setId("JENKINS_NAME");
                messageTemplateFieldOperator.setFieldSource(NoticeConstants.FieldSource.CASE_FIELD);
                messageTemplateFieldOperator.setName(Translator.get("message.jenkins_name"));
                messageTemplateFieldDTOList.add(messageTemplateFieldOperator);
                //TODO：获取报告
            }
            default -> messageTemplateFieldDTOList = new ArrayList<>();
        }

        return messageTemplateFieldDTOList;
    }

    /**
     * 添加自定义字段
     *
     * @param messageTemplateFieldDTOS messageTemplateFieldDTOS
     * @param projectId                projectId
     * @param scene                    对应场景
     */
    private void addCustomFiled(List<MessageTemplateFieldDTO> messageTemplateFieldDTOS, String projectId, String scene) {
        CustomFieldExample example = new CustomFieldExample();
        example.createCriteria().andScopeIdEqualTo(projectId).andSceneEqualTo(scene);
        List<CustomField> customFields = customFieldMapper.selectByExample(example);
        for (CustomField customField : customFields) {
            MessageTemplateFieldDTO messageTemplateFieldDTO = new MessageTemplateFieldDTO();
            messageTemplateFieldDTO.setId(customField.getName());
            messageTemplateFieldDTO.setName(StringUtils.isBlank(customField.getRemark()) ? "-" : customField.getRemark());
            messageTemplateFieldDTO.setFieldSource(NoticeConstants.FieldSource.CUSTOM_FIELD);
            messageTemplateFieldDTOS.add(messageTemplateFieldDTO);
        }
    }

    /**
     * 添加数据库字段
     *
     * @param messageTemplateFieldDTOS messageTemplateFieldDTOS
     * @param allFields                allFields
     */
    private static void addOptionDto(List<MessageTemplateFieldDTO> messageTemplateFieldDTOS, Field[] allFields, String tableName) {
        Field[] sensitiveFields = FieldUtils.getAllFields(NoticeConstants.SensitiveField.class);
        ArrayList<Field> sensitiveFieldList = new ArrayList<>(sensitiveFields.length);
        Collections.addAll(sensitiveFieldList, sensitiveFields);
        List<String> nameList = sensitiveFieldList.stream().map(Field::getName).toList();
        for (Field allField : allFields) {
            MessageTemplateFieldDTO messageTemplateFieldDTO = new MessageTemplateFieldDTO();
            if (nameList.contains(allField.getName())) {
                continue;
            }

            Schema annotation = allField.getAnnotation(Schema.class);
            if (annotation != null) {
                messageTemplateFieldDTO.setId(allField.getName());
                String description;
                if (StringUtils.isBlank(tableName)) {
                    description = Translator.get(annotation.description());
                } else {
                    description = Translator.get("message.domain." + tableName + allField.getName());
                }
                messageTemplateFieldDTO.setName(description);
                messageTemplateFieldDTO.setFieldSource(NoticeConstants.FieldSource.CASE_FIELD);
                messageTemplateFieldDTOS.add(messageTemplateFieldDTO);
            }
        }
        MessageTemplateFieldDTO messageTemplateFieldOperator = new MessageTemplateFieldDTO();
        messageTemplateFieldOperator.setId(NoticeConstants.RelatedUser.OPERATOR);
        messageTemplateFieldOperator.setFieldSource(NoticeConstants.FieldSource.CASE_FIELD);
        messageTemplateFieldOperator.setName(Translator.get("message.operator"));
        messageTemplateFieldDTOS.add(messageTemplateFieldOperator);
        MessageTemplateFieldDTO messageTemplateFieldFollow = new MessageTemplateFieldDTO();
        messageTemplateFieldFollow.setId(NoticeConstants.RelatedUser.FOLLOW_PEOPLE);
        messageTemplateFieldFollow.setFieldSource(NoticeConstants.FieldSource.CASE_FIELD);
        messageTemplateFieldFollow.setName(Translator.get("message.follow_people"));
        messageTemplateFieldDTOS.add(messageTemplateFieldFollow);
        MessageTemplateFieldDTO messageTemplateFieldTriggerMode = new MessageTemplateFieldDTO();
        messageTemplateFieldTriggerMode.setId("TRIGGER_MODE");
        messageTemplateFieldTriggerMode.setFieldSource(NoticeConstants.FieldSource.CASE_FIELD);
        messageTemplateFieldTriggerMode.setName(Translator.get("message.trigger_mode"));
        messageTemplateFieldDTOS.add(messageTemplateFieldTriggerMode);
    }

    public MessageTemplateResultDTO getTemplateFields(String projectId, String taskType) {
        MessageTemplateResultDTO messageTemplateResultDTO = new MessageTemplateResultDTO();
        List<MessageTemplateFieldDTO> domainTemplateFields = getDomainTemplateFields(projectId, taskType);
        messageTemplateResultDTO.setFieldList(domainTemplateFields);
        Map<String, String> fieldSourceMap = MessageTemplateUtils.getFieldSourceMap();
        List<OptionDTO> optionDTOList = new ArrayList<>();
        fieldSourceMap.forEach((k, v) -> {
            OptionDTO optionDTO = new OptionDTO();
            optionDTO.setId(k);
            optionDTO.setName(v);
            optionDTOList.add(optionDTO);
        });
        messageTemplateResultDTO.setFieldSourceList(optionDTOList);
        return messageTemplateResultDTO;
    }

}
