import el from "element-ui/lib/locale/lang/zh-CN"; // 加载element的内容
import fu from "fit2cloud-ui/src/locale/lang/zh-CN"; // 加载fit2cloud的内容
import mf from "metersphere-frontend/src/i18n/lang/zh-CN"

const message = {
  demo: {
    user: {
      info: "用户信息",
      id: "ID",
      name: "名称",
      email: "邮箱"
    },
    system: {
      info: "系统信息",
      name: "模块名",
      port: "服务端口",
      status: "启用状态"
    }
  }
}

export default {
  ...el,
  ...fu,
  ...mf,
  ...message
};
