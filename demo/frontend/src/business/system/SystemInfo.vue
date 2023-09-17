<template>
  <div v-loading="loading">
    <el-card class="card">
      <div class="div">
        <el-card style="height: 100%;width: 100%">
          <div slot="header" style="padding: 5px 0">
            <span style="font-size: 18px;font-weight: bold;">{{ $t('demo.system.info') }}</span>
          </div>
          <div style="position: absolute; top: 50%; left: 50%;transform: translate(-50%, -50%);">
            <div class="div-item">
              {{ $t('demo.system.name') }}：{{ $t('demo.system.port') }}：{{ $t('demo.system.status') }}
            </div>
            <div v-for="module in modules" class="div-item" :key="module.name">
              {{ module.name }}：{{ module.port || '未启动' }}：{{ module.status }}
            </div>
          </div>
        </el-card>
      </div>
    </el-card>
  </div>

</template>

<script>
import {modules} from "@/api/system";

export default {
  name: "SystemInfo",
  data() {
    return {
      modules: [],
      loading: false,
    };
  },
  mounted() {
    this.loading = true;
    modules()
      .then(res => {
        this.loading = false;
        this.modules = res.data;
      })
      .catch(() => {
        this.loading = false;
      });
  }
};
</script>

<style scoped>
.div {
  width: 60vh;
  height: 60vh;
  position: absolute;
  top: 50%;
  left: 50%;
  min-width: 400px;
  min-height: 400px;
  transform: translate(-50%, -50%);
}

.card {
  height: calc(100vh - 55px);
  position: relative;
}

.div-item {
  width: 100%;
  padding: 3px;
}

</style>
