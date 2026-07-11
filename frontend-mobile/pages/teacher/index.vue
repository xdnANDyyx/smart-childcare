<template>
  <view class="container">
    <!-- 顶部统计 -->
    <view class="stats-bar">
      <view class="stat-item">
        <text class="stat-value">28</text>
        <text class="stat-label">在班儿童</text>
      </view>
      <view class="stat-item">
        <text class="stat-value">26</text>
        <text class="stat-label">今日出勤</text>
      </view>
      <view class="stat-item">
        <text class="stat-value">2</text>
        <text class="stat-label">请假</text>
      </view>
    </view>

    <!-- 快捷功能 -->
    <view class="card">
      <view class="card-title">快捷操作</view>
      <view class="quick-grid">
        <view class="quick-item" @tap="goTo('/pages/teacher/student-list')">
          <text class="quick-icon">👶</text>
          <text class="quick-text">花名册</text>
        </view>
        <view class="quick-item" @tap="goTo('/pages/teacher/nursery-log')">
          <text class="quick-icon">📝</text>
          <text class="quick-text">保育日志</text>
        </view>
        <view class="quick-item" @tap="goTo('/pages/teacher/notification')">
          <text class="quick-icon">📢</text>
          <text class="quick-text">发通知</text>
        </view>
        <view class="quick-item">
          <text class="quick-icon">📊</text>
          <text class="quick-text">评价</text>
        </view>
        <view class="quick-item">
          <text class="quick-icon">🗓️</text>
          <text class="quick-text">活动</text>
        </view>
        <view class="quick-item">
          <text class="quick-icon">📋</text>
          <text class="quick-text">问卷</text>
        </view>
        <view class="quick-item">
          <text class="quick-icon">💊</text>
          <text class="quick-text">病假跟踪</text>
        </view>
        <view class="quick-item">
          <text class="quick-icon">⚠️</text>
          <text class="quick-text">特殊体质</text>
        </view>
      </view>
    </view>

    <!-- 今日待办 -->
    <view class="card">
      <view class="card-title">今日待办</view>
      <view class="todo-item" v-for="(t, i) in todos" :key="i">
        <view class="todo-dot" :style="{ background: t.color }"></view>
        <view class="todo-content">
          <text class="todo-text">{{ t.content }}</text>
          <text class="todo-time">{{ t.time }}</text>
        </view>
      </view>
    </view>
  </view>
</template>

<script>
export default {
  data() {
    return {
      todos: [
        { content: '完成晨检记录', time: '09:00', color: '#409eff' },
        { content: '填写太阳班进餐日志', time: '11:30', color: '#67c23a' },
        { content: '张小明家长沟通', time: '15:00', color: '#e6a23c' },
        { content: '填写午睡观察记录', time: '14:00', color: '#f56c6c' },
      ]
    }
  },
  methods: {
    goTo(url) {
      uni.navigateTo({ url })
    }
  }
}
</script>

<style scoped>
.stats-bar {
  display: flex;
  background: linear-gradient(135deg, #409eff, #66b1ff);
  border-radius: 16rpx;
  padding: 30rpx 0;
  margin-bottom: 20rpx;
}
.stat-item {
  flex: 1;
  text-align: center;
  border-right: 1rpx solid rgba(255,255,255,0.3);
}
.stat-item:last-child { border-right: none; }
.stat-value { display: block; font-size: 44rpx; font-weight: bold; color: #fff; }
.stat-label { display: block; font-size: 24rpx; color: rgba(255,255,255,0.85); margin-top: 4rpx; }

.quick-grid {
  display: flex;
  flex-wrap: wrap;
  gap: 20rpx;
}
.quick-item {
  width: calc(25% - 15rpx);
  text-align: center;
  padding: 20rpx 0;
}
.quick-icon { display: block; font-size: 48rpx; }
.quick-text { display: block; font-size: 24rpx; color: #606266; margin-top: 8rpx; }

.todo-item {
  display: flex;
  align-items: center;
  padding: 20rpx 0;
  border-bottom: 1rpx solid #f0f0f0;
}
.todo-item:last-child { border-bottom: none; }
.todo-dot {
  width: 16rpx;
  height: 16rpx;
  border-radius: 50%;
  margin-right: 20rpx;
}
.todo-content { flex: 1; }
.todo-text { display: block; font-size: 28rpx; }
.todo-time { display: block; font-size: 24rpx; color: #909399; margin-top: 4rpx; }
</style>
