<template>
  <view class="login-page">
    <view class="login-header">
      <view class="logo-icon">👶</view>
      <text class="title">智慧托育</text>
      <text class="subtitle">综合应用平台</text>
    </view>

    <view class="login-form">
      <view class="form-item">
        <input v-model="form.username" placeholder="请输入用户名" class="input" />
      </view>
      <view class="form-item">
        <input v-model="form.password" type="password" placeholder="请输入密码" class="input" />
      </view>
      <view class="form-item captcha-row">
        <input v-model="form.captcha" placeholder="验证码" class="input captcha-input" />
        <image v-if="captchaImg" :src="captchaImg" class="captcha-img" @tap="loadCaptcha" mode="aspectFit" />
      </view>
      <view class="form-item">
        <view class="role-tabs">
          <view :class="['role-tab', form.role === 'teacher' && 'active']" @tap="form.role = 'teacher'">老师</view>
          <view :class="['role-tab', form.role === 'parent' && 'active']" @tap="form.role = 'parent'">家长</view>
          <view :class="['role-tab', form.role === 'admin' && 'active']" @tap="form.role = 'admin'">管理者</view>
        </view>
      </view>
      <view class="form-item">
        <button class="btn-primary" @tap="handleLogin" :loading="loading">登 录</button>
      </view>
    </view>
  </view>
</template>

<script>
import { authApi } from '@/api'
import { useUserStore } from '@/store/user'

export default {
  data() {
    return {
      form: { username: '', password: '', captcha: '', captchaKey: '', role: 'teacher' },
      captchaImg: '',
      loading: false,
    }
  },
  onLoad() {
    this.loadCaptcha()
  },
  methods: {
    async loadCaptcha() {
      try {
        const res = await authApi.getCaptcha()
        this.captchaImg = res.data.captchaImg
        this.form.captchaKey = res.data.captchaKey
      } catch (e) {}
    },
    async handleLogin() {
      if (!this.form.username) { uni.showToast({ title: '请输入用户名', icon: 'none' }); return }
      if (!this.form.password) { uni.showToast({ title: '请输入密码', icon: 'none' }); return }

      this.loading = true
      try {
        const userStore = useUserStore()
        await userStore.login({
          username: this.form.username,
          password: this.form.password,
          captcha: this.form.captcha,
          captchaKey: this.form.captchaKey,
        })
        userStore.setRole(this.form.role)
        uni.showToast({ title: '登录成功', icon: 'success' })

        const redirect = this.form.role === 'parent' ? '/pages/parent/index'
          : this.form.role === 'admin' ? '/pages/admin/index'
          : '/pages/teacher/index'
        setTimeout(() => uni.reLaunch({ url: redirect }), 500)
      } catch (e) {
        this.loadCaptcha()
      } finally {
        this.loading = false
      }
    }
  }
}
</script>

<style scoped>
.login-page {
  min-height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  display: flex;
  flex-direction: column;
  align-items: center;
  padding-top: 120rpx;
}

.login-header {
  text-align: center;
  margin-bottom: 60rpx;
}

.logo-icon {
  font-size: 80rpx;
  margin-bottom: 16rpx;
}

.title {
  display: block;
  font-size: 44rpx;
  color: #fff;
  font-weight: bold;
}

.subtitle {
  display: block;
  font-size: 28rpx;
  color: rgba(255,255,255,0.8);
  margin-top: 8rpx;
}

.login-form {
  width: 600rpx;
  background: #fff;
  border-radius: 24rpx;
  padding: 40rpx;
}

.form-item {
  margin-bottom: 24rpx;
}

.input {
  border: 1rpx solid #dcdfe6;
  border-radius: 12rpx;
  padding: 20rpx 24rpx;
  font-size: 28rpx;
}

.captcha-row {
  display: flex;
  align-items: center;
  gap: 16rpx;
}

.captcha-input {
  flex: 1;
}

.captcha-img {
  width: 180rpx;
  height: 72rpx;
  border: 1rpx solid #dcdfe6;
  border-radius: 12rpx;
}

.role-tabs {
  display: flex;
  gap: 16rpx;
}

.role-tab {
  flex: 1;
  text-align: center;
  padding: 16rpx 0;
  border: 1rpx solid #dcdfe6;
  border-radius: 12rpx;
  font-size: 28rpx;
  color: #606266;
}

.role-tab.active {
  background: #409eff;
  color: #fff;
  border-color: #409eff;
}

.btn-primary {
  background: #409eff;
  color: #fff;
  border: none;
  border-radius: 12rpx;
  font-size: 32rpx;
  padding: 20rpx 0;
}
</style>
