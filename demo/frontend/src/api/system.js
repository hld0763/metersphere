import {get} from "metersphere-frontend/src/plugins/request"

export function modules() {
  return get('/demo/module/list')
}
