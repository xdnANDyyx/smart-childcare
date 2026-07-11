<template>
  <div class="dashboard">
    <!-- 统计卡片 -->
    <el-row :gutter="16" class="stat-row">
      <el-col :span="6" v-for="(item, i) in stats" :key="i">
        <el-card shadow="hover" class="stat-card">
          <div class="stat-content">
            <div class="stat-icon" :style="{ background: item.bg }">
              <el-icon size="28" color="#fff"><component :is="item.icon" /></el-icon>
            </div>
            <div class="stat-info">
              <div class="stat-value">{{ item.value }}</div>
              <div class="stat-label">{{ item.label }}</div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 图表区 -->
    <el-row :gutter="16" style="margin-top: 16px">
      <el-col :span="16">
        <el-card shadow="hover">
          <template #header>出勤统计（近7天）</template>
          <div ref="chartRef" style="height: 300px"></div>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card shadow="hover">
          <template #header>班级人数分布</template>
          <div ref="pieRef" style="height: 300px"></div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 待办事项 -->
    <el-row :gutter="16" style="margin-top: 16px">
      <el-col :span="12">
        <el-card shadow="hover">
          <template #header>待处理事项</template>
          <el-timeline>
            <el-timeline-item v-for="(t, i) in todos" :key="i" :timestamp="t.time" :type="t.type as any">
              {{ t.content }}
            </el-timeline-item>
          </el-timeline>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card shadow="hover">
          <template #header>最新招生线索</template>
          <el-table :data="leads" size="small">
            <el-table-column prop="childName" label="儿童姓名" width="100" />
            <el-table-column prop="parentName" label="家长姓名" width="100" />
            <el-table-column prop="phone" label="电话" width="130" />
            <el-table-column prop="level" label="意向" width="80">
              <template #default="{ row }">
                <el-tag :type="row.levelType" size="small">{{ row.level }}</el-tag>
              </template>
            </el-table-column>
            <el-table-column prop="status" label="状态" />
          </el-table>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import * as echarts from 'echarts'

const chartRef = ref<HTMLElement>()
const pieRef = ref<HTMLElement>()

const stats = ref([
  { label: '在托儿童', value: 128, icon: 'User', bg: '#409eff' },
  { label: '今日出勤', value: 116, icon: 'Calendar', bg: '#67c23a' },
  { label: '招生线索', value: 23, icon: 'Pointer', bg: '#e6a23c' },
  { label: '待办事项', value: 7, icon: 'Bell', bg: '#f56c6c' },
])

const todos = ref([
  { time: '2026-07-10 09:00', content: '完成今日儿童晨检记录', type: 'primary' },
  { time: '2026-07-10 10:30', content: '张小明家长到访参观', type: 'success' },
  { time: '2026-07-10 14:00', content: '月亮班保育日志待填写', type: 'warning' },
  { time: '2026-07-10 15:30', content: '月度收费账单待生成', type: 'danger' },
])

const leads = ref([
  { childName: '王小明', parentName: '王大伟', phone: '138****8888', level: '高', levelType: 'danger', status: '跟进中' },
  { childName: '李小红', parentName: '李强', phone: '139****6666', level: '中', levelType: 'warning', status: '新线索' },
  { childName: '张小华', parentName: '张伟', phone: '137****7777', level: '高', levelType: 'danger', status: '已到访' },
  { childName: '刘小婷', parentName: '刘洋', phone: '136****5555', level: '低', levelType: 'info', status: '新线索' },
])

onMounted(() => {
  // 出勤统计柱状图
  if (chartRef.value) {
    const chart = echarts.init(chartRef.value)
    chart.setOption({
      tooltip: { trigger: 'axis' },
      legend: { data: ['出勤', '请假', '缺勤'] },
      grid: { left: '3%', right: '4%', bottom: '3%', containLabel: true },
      xAxis: { type: 'category', data: ['7/4', '7/5', '7/6', '7/7', '7/8', '7/9', '7/10'] },
      yAxis: { type: 'value' },
      series: [
        { name: '出勤', type: 'bar', stack: 'total', data: [115, 118, 112, 110, 116, 0, 116], itemStyle: { color: '#67c23a' } },
        { name: '请假', type: 'bar', stack: 'total', data: [8, 6, 10, 12, 8, 0, 8], itemStyle: { color: '#e6a23c' } },
        { name: '缺勤', type: 'bar', stack: 'total', data: [5, 4, 6, 6, 4, 0, 4], itemStyle: { color: '#f56c6c' } },
      ],
    })
    window.addEventListener('resize', () => chart.resize())
  }

  // 班级人数饼图
  if (pieRef.value) {
    const pie = echarts.init(pieRef.value)
    pie.setOption({
      tooltip: { trigger: 'item' },
      legend: { bottom: 0 },
      series: [
        {
          type: 'pie',
          radius: ['40%', '70%'],
          data: [
            { value: 25, name: '太阳班' },
            { value: 22, name: '月亮班' },
            { value: 28, name: '星星班' },
            { value: 30, name: '云朵班' },
            { value: 23, name: '彩虹班' },
          ],
        },
      ],
    })
    window.addEventListener('resize', () => pie.resize())
  }
})
</script>

<style scoped lang="scss">
.stat-card {
  .stat-content {
    display: flex;
    align-items: center;
    gap: 16px;
  }

  .stat-icon {
    width: 56px;
    height: 56px;
    border-radius: 8px;
    display: flex;
    align-items: center;
    justify-content: center;
  }

  .stat-info {
    .stat-value {
      font-size: 28px;
      font-weight: bold;
      color: #303133;
    }
    .stat-label {
      font-size: 13px;
      color: #909399;
      margin-top: 4px;
    }
  }
}
</style>
