<script setup lang="ts">
import { ref, computed, watch } from 'vue'
import { RouterLink, useRoute, useRouter } from 'vue-router'
import { useLoginUserStore } from '@/stores/LoginUser.ts'
import { LogoutOutlined, UserOutlined, LockOutlined } from '@ant-design/icons-vue'
import { MenuProps, message } from 'ant-design-vue'
import { userLogout } from '@/api/userController.ts'
// 获取当前路由
const route = useRoute()

// 菜单配置项
const originItems = [
  {
    key: '/',
    label: '主页',
    title: '主页',
    path: '/',
  },
  {
    key: '/admin/UserManagePage',
    label: '用户管理',
    title: '用户管理',
    path: '/admin/UserManagePage',
  },
  {
    key: '/admin/AppManagePage',
    label: '应用管理',
    title: '应用管理',
    path: '/admin/AppManagePage',
  },
]

// 过滤菜单项
const filterMenus = (menus = [] as MenuProps['items']) => {
  return menus?.filter((menu) => {
    const menuKey = menu?.key as string
    if (menuKey?.startsWith('/admin')) {
      const loginUser = loginUserStore.loginUser
      if (!loginUser || loginUser.userRole !== 'admin') {
        return false
      }
    }
    return true
  })
}

// 展示在菜单的路由数组
const menuItems = computed<MenuProps['items']>(() => filterMenus(originItems))

const loginUserStore = useLoginUserStore()
// 根据当前路径获取对应的菜单key
const getMenuKeyByPath = (path: string) => {
  const item = menuItems.value.find((item) => item.path === path)
  return item ? item.key : 'home'
}

// 当前选中的菜单项
const selectedKeys = ref([getMenuKeyByPath(route.path)])

// 监听路由变化，更新选中的菜单项
watch(
  () => route.path,
  (newPath) => {
    selectedKeys.value = [getMenuKeyByPath(newPath)]
  },
)

const router = useRouter()

// 用户注销
const doLogout = async () => {
  try {
    const res = await userLogout()
    if (res.data.code === 0) {
      loginUserStore.setLoginUser({
        userName: '未登录',
      })
      message.success('退出登录成功')
      await router.push('/user/login')
    } else {
      message.error('退出登录失败，' + res.data.message)
    }
  } catch (error) {
    message.error('退出登录失败')
    console.error(error)
  }
}

// 跳转到用户信息页面
const goToUserInfo = () => {
  router.push('/user/info')
}

// 跳转到修改密码页面
const goToChangePassword = () => {
  router.push('/user/change-password')
}
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
      <div v-if="loginUserStore.loginUser.id">
        <a-dropdown>
          <a class="ant-dropdown-link user-dropdown-link" @click.prevent>
            <a-space>
              <a-avatar :src="loginUserStore.loginUser.userAvatar" />
              {{ loginUserStore.loginUser.userName ?? '无名' }}
            </a-space>
          </a>
          <template #overlay>
            <a-menu>
              <a-menu-item key="userInfo" @click="goToUserInfo">
                <UserOutlined />
                <span>用户信息</span>
              </a-menu-item>
              <a-menu-item key="changePassword" @click="goToChangePassword">
                <LockOutlined />
                <span>修改密码</span>
              </a-menu-item>
              <a-menu-divider />
              <a-menu-item key="logout" @click="doLogout">
                <LogoutOutlined />
                <span>退出登录</span>
              </a-menu-item>
            </a-menu>
          </template>
        </a-dropdown>
      </div>
      <div v-else>
        <a-button type="primary" href="/user/login">登录</a-button>
      </div>
    </div>
  </a-layout-header>
</template>

<style scoped>
.header {
  display: flex;
  align-items: center;
  padding: 0 24px;
  background-color: white;
  width: 100%;
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

.user-dropdown-link {
  display: flex;
  align-items: center;
  color: rgba(0, 0, 0, 0.85);
  cursor: pointer;
}

.user-dropdown-link:hover {
  color: #1890ff;
}
</style>
