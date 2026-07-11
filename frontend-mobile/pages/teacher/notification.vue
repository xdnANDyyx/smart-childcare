<template>
  <view class="container">
    <view class="card" v-for="n in notifications" :key="n.id">
      <view class="notif-header">
        <text class="notif-title">{{ n.title }}</text>
        <text :class="['tag', n.tagClass]">{{ n.type }}</text>
      </view>
      <text class="notif-content">{{ n.content }}</text>
      <text class="notif-time">{{ n.time }}</text>
    </view>
  </view>
</template>

<script>
export default {
  data() {
    return {
      notifications: [
        { id: 1, title: '关于暑期班报名通知', type: '通知', tagClass: 'tag-primary', content: '各位家长好，2026年暑期班开始报名啦...', time: '2026-07-10 10:00' },
        { id: 2, title: '本周家园互动活动', type: '活动', tagClass: 'tag-success', content: '本周五下午将举办亲子互动活动...', time: '2026-07-09 15:00' },
        { id: 3, title: '疫苗接种提醒', type: '提醒', tagClass: 'tag-warning', content: '请各位家长注意孩子的疫苗接种时间...', time: '2026-07-08 09:00' },
      ]
    }
  }
}
</script>

<style scoped>
.notif-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 12rpx;
}
.notif-title { font-size: 30rpx; font-weight: bold; }
.notif-content { font-size: 28rpx; color: #606266; line-height: 1.6; }
.notif-time { display: block; font-size: 24rpx; color: #909399; margin-top: 12rpx; }
</style>
