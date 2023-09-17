import Layout from "metersphere-frontend/src/business/app-layout";

export default {
  name: "Demo",
  path: "/demo",
  redirect: '/demo/home',
  component: Layout,
  children: [
    {
      path: 'home',
      component: () => import('../../business/home/DemoHome.vue')
    },
    {
      path: 'system',
      component: () => import('../../business/system/SystemInfo.vue')
    },
  ]
};

