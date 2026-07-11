<template>
  <view class="container">
    <view class="search-bar">
      <input v-model="keyword" placeholder="搜索儿童姓名" class="search-input" @confirm="loadData" />
    </view>
    <view class="card" v-for="s in list" :key="s.id" @tap="viewDetail(s)">
      <view class="student-row">
        <view class="student-avatar">{{ s.name?.[0] || '?' }}</view>
        <view class="student-info">
          <text class="student-name">{{ s.name }}</text>
          <text class="student-meta">{{ s.gender === '0' ? '男' : '女' }} | {{ s.birthDate || '-' }}</text>
        </view>
        <text :class="['tag', s.status === '0' ? 'tag-success' : 'tag-info']">
          {{ { '0': '在托', '1': '离托', '2': '待入托' }[s.status] }}
        </text>
      </view>
    </view>
    <view v-if="list.length === 0" class="empty">暂无数据</view>
  </view>
</template>

<script>
import { studentApi } from '@/api'

export default {
  data() {
    return {
      keyword: '',
      list: []
    }
  },
  onLoad() {
    this.loadData()
  },
  methods: {
    async loadData() {
      try {
        const res = await studentApi.list({ keyword: this.keyword, page: 1, size: 100 })
        this.list = res.data?.rows || []
      } catch (e) {}
    },
    viewDetail(s) {
      uni.showToast({ title: `查看${s.name}详情（开发中）`, icon: 'none' })
    }
  }
}
</script>

<style scoped>
.search-bar { margin-bottom: 20rpx; }
.search-input {
  background: #fff;
  border-radius: 12rpx;
  padding: 20rpx 24rpx;
  font-size: 28rpx;
}
.student-row {
  display: flex;
  align-items: center;
}
.student-avatar {
  width: 80rpx;
  height: 80rpx;
  border-radius: 50%;
  background: #409eff;
  color: #fff;
  text-align: center;
  line-height: 80rpx;
  font-size: 32rpx;
  margin-right: 20rpx;
}
.student-info { flex: 1; }
.student-name { display: block; font-size: 30rpx; font-weight: bold; }
.student-meta { display: block; font-size: 24rpx; color: #909399; margin-top: 4rpx; }
.empty { text-align: center; color: #909399; padding: 60rpx; }
</style>
