<script setup lang="ts">
import BasicLayout from './layouts/BasicLayout.vue'
import { health } from '@/api/health'
import { useLoginUserStore } from "@/stores/LoginUser.ts";

// 获取登录用户信息
const loginUserStore = useLoginUserStore();
loginUserStore.fetchLoginUser();



</script>

<template>
  <BasicLayout />
</template>

<style>
/* 全局样式 */
* {
  box-sizing: border-box;
}

body {
  margin: 0;
  padding: 0;
  font-family:
    -apple-system, BlinkMacSystemFont, 'Segoe UI', 'Roboto', 'Oxygen', 'Ubuntu', 'Cantarell',
    'Fira Sans', 'Droid Sans', 'Helvetica Neue', sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  overflow-x: hidden;
}

html {
  overflow-x: hidden;
}
</style>
