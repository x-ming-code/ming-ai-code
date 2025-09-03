<script setup lang="ts">
import { ref, computed, watch } from 'vue';
import { RouterLink, useRoute } from 'vue-router';

// 获取当前路由
const route = useRoute();

// 菜单配置
const menuItems = ref([
  {
    key: 'home',
    title: '首页',
    path: '/'
  },
  {
    key: 'about',
    title: '关于',
    path: '/about'
  }
]);

// 根据当前路径获取对应的菜单key
const getMenuKeyByPath = (path: string) => {
  const item = menuItems.value.find(item => item.path === path);
  return item ? item.key : 'home';
};

// 当前选中的菜单项
const selectedKeys = ref([getMenuKeyByPath(route.path)]);

// 监听路由变化，更新选中的菜单项
watch(
  () => route.path,
  (newPath) => {
    selectedKeys.value = [getMenuKeyByPath(newPath)];
  }
);
</script>

<template>
  <a-layout-header class="header">
    <div class="logo-container">
      <img src="@/assets/logo.svg" alt="Logo" class="logo" />
      <h1 class="site-title">代码生成</h1>
    </div>
    <a-menu
      v-model:selectedKeys="selectedKeys"
      theme="light"
      mode="horizontal"
      :style="{ lineHeight: '64px', flex: 1 }"
    >
      <a-menu-item v-for="item in menuItems" :key="item.key">
        <RouterLink :to="item.path">{{ item.title }}</RouterLink>
      </a-menu-item>
    </a-menu>
    <div class="user-info">
      <a-button type="primary">登录</a-button>
    </div>
  </a-layout-header>
</template>

<style scoped>
.header {
  display: flex;
  align-items: center;
  padding: 0 24px;
  background-color: white;
}

.logo-container {
  display: flex;
  align-items: center;
  margin-right: 24px;
}

.logo {
  height: 32px;
  margin-right: 12px;
}

.site-title {
  color: #1890ff;
  margin: 0;
  font-size: 18px;
}

.user-info {
  margin-left: 16px;
}
</style>
