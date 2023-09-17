<template>
  <el-col>
    <demo-header-menus/>
    <div>
      <transition>
        <keep-alive>
          <router-view/>
        </keep-alive>
      </transition>
    </div>
  </el-col>
</template>

<script>
import DemoHeaderMenus from "./head/DemoHeaderMenus.vue";

export default {
  components: {DemoHeaderMenus},
  data() {
    return {
      baseUrl: "demo"
    }
  },
}
</script>
<style scoped>

</style>
