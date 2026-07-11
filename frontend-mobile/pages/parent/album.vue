<template>
  <view class="container">
    <view class="card">
      <view class="card-title">成长相册</view>
      <view class="album-grid">
        <view class="album-item" v-for="(p, i) in photos" :key="i" @tap="previewPhoto(i)">
          <view class="photo-placeholder">📷</view>
          <text class="photo-date">{{ p.date }}</text>
          <text class="photo-desc">{{ p.desc }}</text>
        </view>
      </view>
    </view>
    <view class="card">
      <view class="card-title">作品集</view>
      <view class="album-grid">
        <view class="album-item" v-for="(w, i) in works" :key="i">
          <view class="photo-placeholder">🎨</view>
          <text class="photo-date">{{ w.date }}</text>
          <text class="photo-desc">{{ w.name }}</text>
        </view>
      </view>
    </view>
  </view>
</template>

<script>
export default {
  data() {
    return {
      photos: [
        { date: '07-10', desc: '户外游戏' },
        { date: '07-09', desc: '手工课' },
        { date: '07-08', desc: '绘本阅读' },
        { date: '07-07', desc: '音乐活动' },
      ],
      works: [
        { date: '07-09', name: '手指画：小太阳' },
        { date: '07-05', name: '折纸：小飞机' },
      ]
    }
  },
  methods: {
    previewPhoto(i) {
      uni.showToast({ title: '图片预览（开发中）', icon: 'none' })
    }
  }
}
</script>

<style scoped>
.album-grid { display: flex; flex-wrap: wrap; gap: 16rpx; }
.album-item { width: calc(33.33% - 12rpx); }
.photo-placeholder {
  width: 100%;
  aspect-ratio: 1;
  background: #f5f7fa;
  border-radius: 12rpx;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 48rpx;
}
.photo-date { display: block; font-size: 22rpx; color: #909399; text-align: center; margin-top: 4rpx; }
.photo-desc { display: block; font-size: 24rpx; text-align: center; }
</style>
