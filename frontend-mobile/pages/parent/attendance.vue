<template>
  <view class="container">
    <view class="card">
      <view class="card-title">本月考勤</view>
      <view class="calendar">
        <view class="cal-header">
          <text v-for="d in ['一','二','三','四','五','六','日']" :key="d" class="cal-day-header">{{ d }}</text>
        </view>
        <view class="cal-body">
          <view v-for="(day, i) in calendar" :key="i" :class="['cal-day', day.status]">
            <text class="cal-num">{{ day.day || '' }}</text>
            <text v-if="day.status" class="cal-mark">{{ { normal: '✓', leave: '假', absent: '×' }[day.status] }}</text>
          </view>
        </view>
      </view>
    </view>
    <view class="card">
      <view class="stat-row">
        <view class="stat-item">
          <text class="stat-value">22</text>
          <text class="stat-label">出勤</text>
        </view>
        <view class="stat-item">
          <text class="stat-value">2</text>
          <text class="stat-label">请假</text>
        </view>
        <view class="stat-item">
          <text class="stat-value">1</text>
          <text class="stat-label">缺勤</text>
        </view>
      </view>
    </view>
  </view>
</template>

<script>
export default {
  data() {
    const calendar = []
    for (let i = 0; i < 35; i++) {
      const day = i - 1 // 偏移调整
      if (day < 1 || day > 31) {
        calendar.push({ day: '' })
      } else if (day <= 10) {
        calendar.push({ day, status: day % 7 === 0 || day % 7 === 6 ? '' : 'normal' })
      } else if (day === 15 || day === 16) {
        calendar.push({ day, status: 'leave' })
      } else if (day === 20) {
        calendar.push({ day, status: 'absent' })
      } else {
        calendar.push({ day, status: day > 10 ? '' : 'normal' })
      }
    }
    return { calendar }
  }
}
</script>

<style scoped>
.cal-header { display: flex; margin-bottom: 10rpx; }
.cal-day-header { flex: 1; text-align: center; font-size: 24rpx; color: #909399; }
.cal-body { display: flex; flex-wrap: wrap; }
.cal-day {
  width: calc(100% / 7);
  text-align: center;
  padding: 16rpx 0;
  position: relative;
}
.cal-num { font-size: 28rpx; }
.cal-mark { display: block; font-size: 20rpx; margin-top: 4rpx; }
.cal-day.normal .cal-mark { color: #67c23a; }
.cal-day.leave .cal-mark { color: #e6a23c; }
.cal-day.absent .cal-mark { color: #f56c6c; }

.stat-row { display: flex; }
.stat-item { flex: 1; text-align: center; }
.stat-value { display: block; font-size: 40rpx; font-weight: bold; color: #409eff; }
.stat-label { display: block; font-size: 24rpx; color: #909399; margin-top: 4rpx; }
</style>
