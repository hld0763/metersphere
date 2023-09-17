import {get} from "metersphere-frontend/src/plugins/request"

export function isLogin() {
  return get('/is-login')
}
