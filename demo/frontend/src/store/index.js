import {defineStore} from 'pinia';
import user from 'metersphere-frontend/src/store/modules/user';

let useUserStore = defineStore(user);

const useStore = () => ({
  user: useUserStore(),
});

export {
  useUserStore,
  useStore as default
};
