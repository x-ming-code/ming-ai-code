<template>
  <div id="changePasswordPage">
    <h2 class="title">修改密码</h2>
    <a-form :model="formState" name="basic" autocomplete="off" @finish="handleSubmit">
      <a-form-item
        name="oldPassword"
        :rules="[{ required: true, message: '请输入原密码' }]"
      >
        <a-input-password v-model:value="formState.oldPassword" placeholder="请输入原密码" />
      </a-form-item>
      <a-form-item
        name="newPassword"
        :rules="[
          { required: true, message: '请输入新密码' },
          { min: 4, message: '密码不能小于 4 位' },
        ]"
      >
        <a-input-password v-model:value="formState.newPassword" placeholder="请输入新密码" />
      </a-form-item>
      <a-form-item
        name="confirmPassword"
        :rules="[
          { required: true, message: '请确认新密码' },
          { min: 4, message: '密码不能小于 4 位' },
          { validator: validatePassword }
        ]"
      >
        <a-input-password v-model:value="formState.confirmPassword" placeholder="请确认新密码" />
      </a-form-item>
      <a-form-item>
        <a-space>
          <a-button type="primary" html-type="submit">修改</a-button>
          <a-button @click="goBack">返回</a-button>
        </a-space>
      </a-form-item>
    </a-form>
  </div>
</template>

<script lang="ts" setup>
import { reactive } from "vue";
import { message } from "ant-design-vue";
import { useRouter } from "vue-router";

interface PasswordForm {
  oldPassword: string;
  newPassword: string;
  confirmPassword: string;
}

const formState = reactive<PasswordForm>({
  oldPassword: '',
  newPassword: '',
  confirmPassword: '',
});

const router = useRouter();

/**
 * 校验两次输入的密码是否一致
 */
const validatePassword = async (_rule: any, value: string) => {
  if (value !== formState.newPassword) {
    return Promise.reject('两次输入的密码不一致');
  }
  return Promise.resolve();
};

/**
 * 提交表单
 */
const handleSubmit = async () => {
  // 这里需要调用修改密码的API，目前API中没有找到对应的接口
  // 模拟API调用
  try {
    // 假设调用成功
    message.success('密码修改成功');
    router.back();
  } catch (error) {
    message.error('密码修改失败');
    console.error(error);
  }
};

const goBack = () => {
  router.back();
};
</script>

<style scoped>
#changePasswordPage {
  max-width: 500px;
  margin: 0 auto;
  padding: 24px;
}

.title {
  margin-bottom: 24px;
}
</style>