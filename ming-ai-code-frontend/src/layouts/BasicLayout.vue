<script setup lang="ts">
import { RouterView } from 'vue-router';
import GlobalHeader from '@/components/layout/GlobalHeader.vue';
import GlobalFooter from '@/components/layout/GlobalFooter.vue';
</script>

<template>
  <a-layout class="layout">
    <GlobalHeader />
    <a-layout-content class="content">
      <div class="content-container">
        <RouterView />
      </div>
    </a-layout-content>
    <GlobalFooter />
  </a-layout>
</template>

<style scoped>
.layout {
  min-height: 100vh;
}

.content {
  padding: 0 50px;
  display: flex;
  flex-direction: column;
}

.content-container {
  background: #fff;
  padding: 24px;
  min-height: 280px;
  margin: 24px 0;
  flex: 1;
}

@media (max-width: 768px) {
  .content {
    padding: 0 20px;
  }
  
  .content-container {
    padding: 16px;
    margin: 16px 0;
  }
}
</style>