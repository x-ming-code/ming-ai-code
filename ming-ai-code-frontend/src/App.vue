<script setup lang="ts">
import BasicLayout from './layouts/BasicLayout.vue'
import { health } from '@/api/health'

health().then((res) => {
  console.log(res)
})

</script>

<template>
  <BasicLayout />
</template>

<style>
/* 全局样式 */
html, body {
  margin: 0;
  padding: 0;
  height: 100%;
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, 'Helvetica Neue', Arial, sans-serif;
}
</style>
