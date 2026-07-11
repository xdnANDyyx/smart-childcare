<script>
export default {
  onLaunch() {
    console.log('App Launch - 智慧托育移动端')
  },
  onShow() {
    console.log('App Show')
  },
  onHide() {
    console.log('App Hide')
  }
}
</script>

<style>
/* 全局样式 */
page {
  background-color: #f5f5f5;
  font-size: 28rpx;
  color: #333;
}

.container {
  padding: 20rpx;
}

/* 卡片 */
.card {
  background: #fff;
  border-radius: 16rpx;
  padding: 24rpx;
  margin-bottom: 20rpx;
  box-shadow: 0 2rpx 12rpx rgba(0, 0, 0, 0.05);
}

/* 标题 */
.card-title {
  font-size: 32rpx;
  font-weight: bold;
  margin-bottom: 16rpx;
  color: #303133;
}

/* 标签 */
.tag {
  display: inline-block;
  padding: 4rpx 16rpx;
  border-radius: 8rpx;
  font-size: 24rpx;
  margin-right: 8rpx;
}
.tag-success { background: #f0f9eb; color: #67c23a; }
.tag-warning { background: #fdf6ec; color: #e6a23c; }
.tag-danger { background: #fef0f0; color: #f56c6c; }
.tag-info { background: #f4f4f5; color: #909399; }
.tag-primary { background: #ecf5ff; color: #409eff; }

/* 按钮 */
.btn-primary {
  background: #409eff;
  color: #fff;
  border: none;
  border-radius: 12rpx;
  padding: 20rpx 0;
  text-align: center;
  font-size: 30rpx;
}
</style>
