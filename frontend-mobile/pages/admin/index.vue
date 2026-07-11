<template>
  <view class="container">
    <view class="header-card">
      <text class="header-title">运营看板</text>
      <text class="header-date">{{ today }}</text>
    </view>

    <!-- KPI卡片 -->
    <view class="kpi-row">
      <view class="kpi-card">
        <text class="kpi-value">128</text>
        <text class="kpi-label">在托儿童</text>
      </view>
      <view class="kpi-card">
        <text class="kpi-value">116</text>
        <text class="kpi-label">今日出勤</text>
      </view>
    </view>
    <view class="kpi-row">
      <view class="kpi-card">
        <text class="kpi-value">90.6%</text>
        <text class="kpi-label">出勤率</text>
      </view>
      <view class="kpi-card">
        <text class="kpi-value">23</text>
        <text class="kpi-label">招生线索</text>
      </view>
    </view>
    <view class="kpi-row">
      <view class="kpi-card">
        <text class="kpi-value">5</text>
        <text class="kpi-label">班级数</text>
      </view>
      <view class="kpi-card">
        <text class="kpi-value">12</text>
        <text class="kpi-label">教职工</text>
      </view>
    </view>

    <!-- 收费统计 -->
    <view class="card">
      <view class="card-title">本月收费统计</view>
      <view class="finance-row">
        <text class="finance-label">应收</text>
        <text class="finance-value">¥58,200.00</text>
      </view>
      <view class="finance-row">
        <text class="finance-label">已收</text>
        <text class="finance-value" style="color: #67c23a">¥45,600.00</text>
      </view>
      <view class="finance-row">
        <text class="finance-label">未收</text>
        <text class="finance-value" style="color: #f56c6c">¥12,600.00</text>
      </view>
    </view>

    <!-- 班级出勤 -->
    <view class="card">
      <view class="card-title">班级出勤</view>
      <view class="class-row" v-for="c in classes" :key="c.name">
        <text class="class-name">{{ c.name }}</text>
        <view class="class-bar">
          <view class="class-bar-fill" :style="{ width: c.rate + '%', background: c.color }"></view>
        </view>
        <text class="class-rate">{{ c.rate }}%</text>
      </view>
    </view>
  </view>
</template>

<script>
export default {
  data() {
    return {
      today: '2026-07-10',
      classes: [
        { name: '太阳班', rate: 92, color: '#409eff' },
        { name: '月亮班', rate: 88, color: '#67c23a' },
        { name: '星星班', rate: 95, color: '#e6a23c' },
        { name: '云朵班', rate: 85, color: '#f56c6c' },
        { name: '彩虹班', rate: 90, color: '#909399' },
      ]
    }
  }
}
</script>

<style scoped>
.header-card {
  background: linear-gradient(135deg, #409eff, #66b1ff);
  border-radius: 16rpx;
  padding: 30rpx;
  margin-bottom: 20rpx;
}
.header-title { display: block; font-size: 36rpx; color: #fff; font-weight: bold; }
.header-date { display: block; font-size: 26rpx; color: rgba(255,255,255,0.85); margin-top: 8rpx; }

.kpi-row { display: flex; gap: 20rpx; margin-bottom: 20rpx; }
.kpi-card {
  flex: 1;
  background: #fff;
  border-radius: 16rpx;
  padding: 30rpx;
  text-align: center;
  box-shadow: 0 2rpx 12rpx rgba(0,0,0,0.05);
}
.kpi-value { display: block; font-size: 44rpx; font-weight: bold; color: #303133; }
.kpi-label { display: block; font-size: 24rpx; color: #909399; margin-top: 8rpx; }

.finance-row {
  display: flex;
  justify-content: space-between;
  padding: 16rpx 0;
  border-bottom: 1rpx solid #f5f5f5;
}
.finance-row:last-child { border-bottom: none; }
.finance-label { font-size: 28rpx; color: #606266; }
.finance-value { font-size: 32rpx; font-weight: bold; }

.class-row { display: flex; align-items: center; padding: 16rpx 0; }
.class-name { width: 120rpx; font-size: 28rpx; }
.class-bar { flex: 1; height: 20rpx; background: #f5f5f5; border-radius: 10rpx; margin: 0 20rpx; }
.class-bar-fill { height: 100%; border-radius: 10rpx; transition: width 0.3s; }
.class-rate { width: 80rpx; text-align: right; font-size: 26rpx; color: #606266; }
</style>
