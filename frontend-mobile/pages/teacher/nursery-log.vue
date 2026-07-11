<template>
  <view class="container">
    <!-- 日志类型选择 -->
    <scroll-view scroll-x class="type-tabs">
      <view v-for="t in logTypes" :key="t.value"
        :class="['type-tab', currentType === t.value && 'active']"
        @tap="switchType(t.value)">
        <text class="type-icon">{{ t.icon }}</text>
        <text>{{ t.label }}</text>
      </view>
    </scroll-view>

    <!-- 日志列表 -->
    <view class="card" v-for="log in list" :key="log.id">
      <view class="log-header">
        <text class="log-type">{{ getTypeName(log.logType) }}</text>
        <text class="log-time">{{ log.createTime }}</text>
      </view>
      <text class="log-content">{{ log.content }}</text>
      <view class="log-footer">
        <text class="log-recorder">记录人: {{ log.recorderName }}</text>
        <text v-if="log.temperature" class="log-temp">体温: {{ log.temperature }}℃</text>
      </view>
    </view>
    <view v-if="list.length === 0" class="empty">暂无日志记录</view>

    <!-- 新增按钮 -->
    <view class="fab" @tap="handleAdd">+</view>
  </view>
</template>

<script>
import { nurseryLogApi } from '@/api'

export default {
  data() {
    return {
      currentType: '',
      list: [],
      logTypes: [
        { value: '', label: '全部', icon: '📋' },
        { value: 'meal', label: '进餐', icon: '🍚' },
        { value: 'water', label: '饮水', icon: '💧' },
        { value: 'sleep', label: '睡眠', icon: '😴' },
        { value: 'toilet', label: '如厕', icon: '🚽' },
        { value: 'mood', label: '情绪', icon: '😊' },
        { value: 'activity', label: '活动', icon: '🏃' },
      ]
    }
  },
  onLoad() {
    this.loadData()
  },
  methods: {
    async loadData() {
      try {
        const res = await nurseryLogApi.list({ logType: this.currentType, page: 1, size: 50 })
        this.list = res.data?.rows || []
      } catch (e) {}
    },
    switchType(type) {
      this.currentType = type
      this.loadData()
    },
    getTypeName(type) {
      return this.logTypes.find(t => t.value === type)?.label || type
    },
    handleAdd() {
      uni.showToast({ title: '新增保育日志（开发中）', icon: 'none' })
    }
  }
}
</script>

<style scoped>
.type-tabs {
  white-space: nowrap;
  margin-bottom: 20rpx;
  padding: 0 10rpx;
}
.type-tab {
  display: inline-flex;
  flex-direction: column;
  align-items: center;
  padding: 16rpx 24rpx;
  background: #fff;
  border-radius: 12rpx;
  margin-right: 16rpx;
  font-size: 24rpx;
  color: #606266;
}
.type-tab.active { background: #409eff; color: #fff; }
.type-icon { font-size: 36rpx; margin-bottom: 4rpx; }

.log-header {
  display: flex;
  justify-content: space-between;
  margin-bottom: 12rpx;
}
.log-type { font-size: 28rpx; font-weight: bold; color: #409eff; }
.log-time { font-size: 24rpx; color: #909399; }
.log-content { font-size: 28rpx; color: #303133; line-height: 1.6; }
.log-footer {
  display: flex;
  gap: 30rpx;
  margin-top: 16rpx;
  padding-top: 16rpx;
  border-top: 1rpx solid #f0f0f0;
}
.log-recorder, .log-temp { font-size: 24rpx; color: #909399; }

.fab {
  position: fixed;
  right: 40rpx;
  bottom: 120rpx;
  width: 100rpx;
  height: 100rpx;
  border-radius: 50%;
  background: #409eff;
  color: #fff;
  font-size: 60rpx;
  text-align: center;
  line-height: 100rpx;
  box-shadow: 0 4rpx 20rpx rgba(64, 158, 255, 0.4);
}
.empty { text-align: center; color: #909399; padding: 60rpx; }
</style>
