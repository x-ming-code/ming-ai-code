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
  padding: 0;
  display: flex;
  flex-direction: column;
  width: 100%;
}

.content-container {
  background: #fff;
  padding: 0;
  min-height: 280px;
  margin: 0;
  flex: 1;
  width: 100%;
}

@media (max-width: 768px) {
  .content {
    padding: 0;
  }
  
  .content-container {
    padding: 0;
    margin: 0;
  }
}
</style>