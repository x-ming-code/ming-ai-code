<template>
  <div id="userInfoPage">
    <h2 class="title">用户信息</h2>
    <a-descriptions bordered>
      <a-descriptions-item label="用户名" :span="3">
        {{ loginUserStore.loginUser.userName }}
      </a-descriptions-item>
      <a-descriptions-item label="账号" :span="3">
        {{ loginUserStore.loginUser.userAccount }}
      </a-descriptions-item>
      <a-descriptions-item label="头像" :span="3">
        <a-avatar :size="64" :src="loginUserStore.loginUser.userAvatar" />
      </a-descriptions-item>
      <a-descriptions-item label="角色" :span="3">
        {{ loginUserStore.loginUser.userRole }}
      </a-descriptions-item>
      <a-descriptions-item label="个人简介" :span="3">
        {{ loginUserStore.loginUser.userProfile || '暂无简介' }}
      </a-descriptions-item>
      <a-descriptions-item label="创建时间" :span="3">
        {{ loginUserStore.loginUser.createTime }}
      </a-descriptions-item>
    </a-descriptions>
    <div class="actions">
      <a-button type="primary" @click="goBack">返回</a-button>
    </div>
  </div>
</template>

<script lang="ts" setup>
import { useLoginUserStore } from "@/stores/LoginUser.ts";
import { useRouter } from "vue-router";

const loginUserStore = useLoginUserStore();
const router = useRouter();

const goBack = () => {
  router.back();
};
</script>

<style scoped>
#userInfoPage {
  max-width: 800px;
  margin: 0 auto;
  padding: 24px;
}

.title {
  margin-bottom: 24px;
}

.actions {
  margin-top: 24px;
  display: flex;
  justify-content: flex-end;
}
</style>