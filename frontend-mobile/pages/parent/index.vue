<template>
  <view class="container">
    <!-- 顶部儿童信息卡 -->
    <view class="child-card">
      <view class="child-avatar">{{ childName[0] }}</view>
      <view class="child-info">
        <text class="child-name">{{ childName }}</text>
        <text class="child-class">太阳班 · 今日出勤 ✓</text>
      </view>
    </view>

    <!-- 快捷入口 -->
    <view class="card">
      <view class="card-title">快捷入口</view>
      <view class="quick-grid">
        <view class="quick-item" @tap="goTo('/pages/parent/daily-report')">
          <text class="quick-icon">📋</text>
          <text class="quick-text">每日报告</text>
        </view>
        <view class="quick-item" @tap="goTo('/pages/parent/attendance')">
          <text class="quick-icon">📅</text>
          <text class="quick-text">考勤记录</text>
        </view>
        <view class="quick-item" @tap="goTo('/pages/parent/bill')">
          <text class="quick-icon">💰</text>
          <text class="quick-text">账单缴费</text>
        </view>
        <view class="quick-item" @tap="goTo('/pages/parent/album')">
          <text class="quick-icon">📸</text>
          <text class="quick-text">成长相册</text>
        </view>
      </view>
    </view>

    <!-- 今日动态 -->
    <view class="card">
      <view class="card-title">今日动态</view>
      <view class="timeline">
        <view class="timeline-item" v-for="(t, i) in timeline" :key="i">
          <view class="timeline-dot" :style="{ background: t.color }"></view>
          <view class="timeline-content">
            <text class="timeline-time">{{ t.time }}</text>
            <text class="timeline-text">{{ t.content }}</text>
          </view>
        </view>
      </view>
    </view>

    <!-- 通知 -->
    <view class="card">
      <view class="card-title">最新通知</view>
      <view class="notif-item" v-for="n in notifications" :key="n.id">
        <text class="notif-dot">●</text>
        <text class="notif-text">{{ n.title }}</text>
      </view>
    </view>
  </view>
</template>

<script>
export default {
  data() {
    return {
      childName: '小明',
      timeline: [
        { time: '08:30', content: '入园打卡 ✓', color: '#67c23a' },
        { time: '09:00', content: '晨检正常，体温36.5℃', color: '#409eff' },
        { time: '11:30', content: '午餐：米饭、鸡胸肉、西兰花（全部吃完）', color: '#e6a23c' },
        { time: '12:30', content: '午睡中 💤', color: '#909399' },
      ],
      notifications: [
        { id: 1, title: '暑期班报名通知' },
        { id: 2, title: '本周亲子活动邀请' },
      ]
    }
  },
  methods: {
    goTo(url) { uni.navigateTo({ url }) }
  }
}
</script>

<style scoped>
.child-card {
  display: flex;
  align-items: center;
  background: linear-gradient(135deg, #409eff, #66b1ff);
  border-radius: 16rpx;
  padding: 30rpx;
  margin-bottom: 20rpx;
}
.child-avatar {
  width: 100rpx;
  height: 100rpx;
  border-radius: 50%;
  background: rgba(255,255,255,0.3);
  color: #fff;
  text-align: center;
  line-height: 100rpx;
  font-size: 40rpx;
  margin-right: 24rpx;
}
.child-info { flex: 1; }
.child-name { display: block; font-size: 36rpx; color: #fff; font-weight: bold; }
.child-class { display: block; font-size: 26rpx; color: rgba(255,255,255,0.85); margin-top: 4rpx; }

.quick-grid { display: flex; flex-wrap: wrap; gap: 20rpx; }
.quick-item { width: calc(25% - 15rpx); text-align: center; padding: 20rpx 0; }
.quick-icon { display: block; font-size: 48rpx; }
.quick-text { display: block; font-size: 24rpx; color: #606266; margin-top: 8rpx; }

.timeline { padding-left: 10rpx; }
.timeline-item {
  display: flex;
  padding: 16rpx 0;
  border-bottom: 1rpx solid #f5f5f5;
}
.timeline-item:last-child { border-bottom: none; }
.timeline-dot {
  width: 16rpx; height: 16rpx;
  border-radius: 50%;
  margin-right: 20rpx;
  margin-top: 8rpx;
}
.timeline-time { display: block; font-size: 24rpx; color: #909399; }
.timeline-text { display: block; font-size: 28rpx; margin-top: 4rpx; }

.notif-item { display: flex; align-items: center; padding: 16rpx 0; }
.notif-dot { color: #409eff; margin-right: 12rpx; font-size: 16rpx; }
.notif-text { font-size: 28rpx; color: #303133; }
</style>
