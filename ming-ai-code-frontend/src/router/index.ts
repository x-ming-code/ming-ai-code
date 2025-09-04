import { createRouter, createWebHistory } from 'vue-router'
import HomePage from '@/pages/HomePage.vue'
import UserManagePage from "@/pages/admin/UserManagePage.vue";
import UserRegisterPage from "@/pages/user/UserRegisterPage.vue";
import UserLoginPage from "@/pages/user/UserLoginPage.vue";
import UserInfoPage from "@/pages/user/UserInfoPage.vue";
import ChangePasswordPage from "@/pages/user/ChangePasswordPage.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomePage,
    },
    {
      path: '/user/login',
      name: '用户登录',
      component: UserLoginPage,
    },
    {
      path: '/user/register',
      name: '用户注册',
      component: UserRegisterPage,
    },
    {
      path: '/user/info',
      name: '用户信息',
      component: UserInfoPage,
    },
    {
      path: '/user/change-password',
      name: '修改密码',
      component: ChangePasswordPage,
    },
    {
      path: '/admin/UserManagePage',
      name: '用户管理',
      component: UserManagePage,
    },
  ],
})

export default router
