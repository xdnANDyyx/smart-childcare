<template>
  <view class="container">
    <view class="card" v-for="b in bills" :key="b.id">
      <view class="bill-header">
        <text class="bill-name">{{ b.name }}</text>
        <text :class="['tag', b.paid ? 'tag-success' : 'tag-warning']">{{ b.paid ? '已缴' : '待缴' }}</text>
      </view>
      <text class="bill-amount">¥{{ b.amount }}</text>
      <text class="bill-period">{{ b.period }}</text>
      <button v-if="!b.paid" class="btn-primary pay-btn" @tap="handlePay(b)">立即缴费</button>
    </view>
  </view>
</template>

<script>
export default {
  data() {
    return {
      bills: [
        { id: 1, name: '7月托育费', amount: '2500.00', period: '2026年7月', paid: false },
        { id: 2, name: '7月餐费', amount: '600.00', period: '2026年7月', paid: false },
        { id: 3, name: '6月托育费', amount: '2500.00', period: '2026年6月', paid: true },
      ]
    }
  },
  methods: {
    handlePay(b) {
      uni.showToast({ title: `缴费${b.name}（开发中）`, icon: 'none' })
    }
  }
}
</script>

<style scoped>
.bill-header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 12rpx; }
.bill-name { font-size: 30rpx; font-weight: bold; }
.bill-amount { display: block; font-size: 48rpx; font-weight: bold; color: #f56c6c; }
.bill-period { display: block; font-size: 24rpx; color: #909399; margin-top: 4rpx; }
.pay-btn { margin-top: 20rpx; font-size: 28rpx; }
</style>
