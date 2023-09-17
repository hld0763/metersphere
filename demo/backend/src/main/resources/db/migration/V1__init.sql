-- 在系统表添加模块，如果是和其它模块有关的通用模块，则该语句一般是通过system-setting模块执行
INSERT INTO system_parameter (param_key, param_value, type, sort)
VALUES ('metersphere.module.${module}', 'ENABLE', 'text', 1);