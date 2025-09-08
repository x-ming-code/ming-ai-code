import { createRouter, createWebHistory } from 'vue-router'
import HomePage from '@/pages/HomePage.vue'
import UserManagePage from "@/pages/admin/UserManagePage.vue";
import UserRegisterPage from "@/pages/user/UserRegisterPage.vue";
import UserLoginPage from "@/pages/user/UserLoginPage.vue";
import UserInfoPage from "@/pages/user/UserInfoPage.vue";
import ChangePasswordPage from "@/pages/user/ChangePasswordPage.vue";
import AppEditPage from "@/pages/app/AppEditPage.vue";
import AppChatPage from "@/pages/app/AppChatPage.vue";
import AppManagePage from "@/pages/admin/AppManagePage.vue";

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
    {
      path: '/admin/AppManagePage',
      name: '应用管理',
      component: AppManagePage,
    },
    {
      path: '/app/chat/:id',
      name: '应用对话',
      component: AppChatPage,
    },
    {
      path: '/app/edit/:id',
      name: '编辑应用',
      component: AppEditPage,
    },
  ],
})

export default router
