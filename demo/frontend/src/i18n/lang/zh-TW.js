import el from "element-ui/lib/locale/lang/zh-TW";
import fu from "fit2cloud-ui/src/locale/lang/zh-TW"; // 加载fit2cloud的内容
import mf from "metersphere-frontend/src/i18n/lang/zh-TW"

const message = {
  demo: {
    user: {
      info: "用戶信息",
      id: "ID",
      name: "名稱",
      email: "郵箱"
    },
    system: {
      info: "系統信息",
      name: "模塊名",
      port: "服務端口",
      status: "啟用狀態"
    }
  }
}

export default {
  ...el,
  ...fu,
  ...mf,
  ...message
};
