<template>
  <div id="menu-bar">
    <el-row type="flex">
      <el-col :span="14">
        <el-menu class="header-menu" :unique-opened="true" mode="horizontal" router
                 :default-active="pathName">
          <el-menu-item :index="'/demo/home'">
            {{ $t('demo.user.info') }}
          </el-menu-item>
          <el-menu-item :index="'/demo/system'">
            {{ $t('demo.system.info') }}
          </el-menu-item>
        </el-menu>
      </el-col>
      <el-col :span="10">
        <ms-header-right-menus/>
      </el-col>
    </el-row>
  </div>

</template>
<script>

import MsHeaderRightMenus from "metersphere-frontend/src/components/layout/HeaderRightMenus";

export default {
  components: {MsHeaderRightMenus},
  data() {
    return {
      pathName: ''
    };
  },
  watch: {
    '$route': {
      immediate: true,
      handler(to) {
        this.pathName = to.path;
      }
    }
  },
};

</script>

<style scoped>
#menu-bar {
  border-bottom: 1px solid #E6E6E6;
  background-color: #FFF;
}

.header-menu {
  margin-left: 190px;
}

.el-menu-item {
  padding: 0 10px;
}
</style>
