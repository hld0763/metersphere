import el from "element-ui/lib/locale/lang/en";
import fu from "fit2cloud-ui/src/locale/lang/en_US"; // 加载fit2cloud的内容
import mf from "metersphere-frontend/src/i18n/lang/en-US"

const message = {
  demo: {
    user: {
      info: "user info",
      id: "User ID",
      name: "User Name",
      email: "User Email"
    },
    system: {
      info: "system info",
      name: "Module Name",
      port: "Module Port",
      status: "Module Status"
    }
  }
}
export default {
  ...el,
  ...fu,
  ...mf,
  ...message
};

