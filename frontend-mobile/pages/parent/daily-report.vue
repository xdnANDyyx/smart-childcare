<template>
  <view class="container">
    <view class="card">
      <view class="card-title">2026年7月10日 每日报告</view>
      <view class="report-section">
        <text class="section-title">📊 今日概况</text>
        <text class="section-content">出勤正常，情绪愉快，活动参与积极。</text>
      </view>
      <view class="report-section">
        <text class="section-title">🍚 进餐情况</text>
        <text class="section-content">早餐：牛奶+面包（全部吃完）\n午餐：米饭+鸡肉+蔬菜（大部分吃完）\n下午茶：水果+酸奶</text>
      </view>
      <view class="report-section">
        <text class="section-title">😴 午睡情况</text>
        <text class="section-content">12:30入睡，14:15醒来，睡眠质量良好。</text>
      </view>
      <view class="report-section">
        <text class="section-title">🏃 活动记录</text>
        <text class="section-content">参加了户外游戏和手工活动，表现积极。</text>
      </view>
      <view class="report-section">
        <text class="section-title">😊 情绪状态</text>
        <text class="section-content">整体情绪愉快，与同伴互动良好。</text>
      </view>
    </view>
  </view>
</template>

<script>
export default {}
</script>

<style scoped>
.report-section { margin-bottom: 24rpx; padding-bottom: 20rpx; border-bottom: 1rpx solid #f5f5f5; }
.report-section:last-child { border-bottom: none; }
.section-title { display: block; font-size: 28rpx; font-weight: bold; color: #303133; margin-bottom: 8rpx; }
.section-content { display: block; font-size: 28rpx; color: #606266; line-height: 1.8; }
</style>
