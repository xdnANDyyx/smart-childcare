<template>
  <div class="login-container">
    <div class="login-box">
      <div class="login-left">
        <div class="login-left-content">
          <h1>智慧托育综合应用平台</h1>
          <p>面向托育机构的综合管理解决方案</p>
          <div class="features">
            <div class="feature-item">
              <el-icon size="24"><School /></el-icon>
              <span>机构/班级/儿童管理</span>
            </div>
            <div class="feature-item">
              <el-icon size="24"><Calendar /></el-icon>
              <span>出勤/保育日志管理</span>
            </div>
            <div class="feature-item">
              <el-icon size="24"><FirstAidKit /></el-icon>
              <span>健康档案/专案管理</span>
            </div>
            <div class="feature-item">
              <el-icon size="24"><Monitor /></el-icon>
              <span>IoT设备智能集成</span>
            </div>
          </div>
        </div>
      </div>
      <div class="login-right">
        <el-form ref="formRef" :model="form" :rules="rules" class="login-form">
          <h2>欢迎登录</h2>
          <el-form-item prop="username">
            <el-input v-model="form.username" placeholder="请输入用户名" :prefix-icon="User" size="large" />
          </el-form-item>
          <el-form-item prop="password">
            <el-input v-model="form.password" type="password" placeholder="请输入密码" :prefix-icon="Lock" size="large" show-password @keyup.enter="handleLogin" />
          </el-form-item>
          <el-form-item prop="captcha">
            <div class="captcha-row">
              <el-input v-model="form.captcha" placeholder="请输入验证码" :prefix-icon="Key" size="large" />
              <img v-if="captchaImg" :src="captchaImg" class="captcha-img" @click="loadCaptcha" alt="验证码" />
            </div>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" size="large" style="width: 100%" :loading="loading" @click="handleLogin">
              登 录
            </el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage, type FormInstance } from 'element-plus'
import { User, Lock, Key } from '@element-plus/icons-vue'
import { useUserStore } from '@/store/user'
import { authApi } from '@/api'

const router = useRouter()
const userStore = useUserStore()
const formRef = ref<FormInstance>()
const loading = ref(false)
const captchaImg = ref('')

const form = reactive({
  username: 'admin',
  password: 'admin123',
  captcha: '',
  captchaKey: '',
})

const rules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
  captcha: [{ required: true, message: '请输入验证码', trigger: 'blur' }],
}

async function loadCaptcha() {
  try {
    const res: any = await authApi.getCaptcha()
    captchaImg.value = res.data.captchaImg
    form.captchaKey = res.data.captchaKey
  } catch (e) {
    // 忽略
  }
}

async function handleLogin() {
  if (!formRef.value) return
  await formRef.value.validate(async (valid) => {
    if (!valid) return
    loading.value = true
    try {
      await userStore.login({
        username: form.username,
        password: form.password,
        captcha: form.captcha,
        captchaKey: form.captchaKey,
      })
      ElMessage.success('登录成功')
      router.push('/')
    } catch (e) {
      loadCaptcha()
    } finally {
      loading.value = false
    }
  })
}

onMounted(() => {
  loadCaptcha()
})
</script>

<style scoped lang="scss">
.login-container {
  height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

.login-box {
  width: 800px;
  height: 480px;
  display: flex;
  border-radius: 12px;
  overflow: hidden;
  box-shadow: 0 20px 60px rgba(0, 0, 0, 0.3);
}

.login-left {
  width: 400px;
  background: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%);
  color: #fff;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 40px;

  .login-left-content {
    h1 {
      font-size: 24px;
      margin-bottom: 12px;
    }
    p {
      font-size: 14px;
      opacity: 0.9;
      margin-bottom: 30px;
    }
  }

  .features {
    display: flex;
    flex-direction: column;
    gap: 16px;

    .feature-item {
      display: flex;
      align-items: center;
      gap: 10px;
      font-size: 14px;
      opacity: 0.95;
    }
  }
}

.login-right {
  flex: 1;
  background: #fff;
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 40px;

  .login-form {
    width: 100%;
    max-width: 320px;

    h2 {
      text-align: center;
      margin-bottom: 30px;
      color: #303133;
    }
  }
}

.captcha-row {
  display: flex;
  gap: 10px;
  width: 100%;

  .el-input {
    flex: 1;
  }

  .captcha-img {
    height: 40px;
    width: 120px;
    border-radius: 4px;
    cursor: pointer;
    border: 1px solid #dcdfe6;
  }
}
</style>
