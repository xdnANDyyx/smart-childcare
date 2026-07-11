<template>
  <el-card shadow="never">
    <el-form inline>
      <el-form-item label="日志类型">
        <el-select v-model="query.logType" clearable placeholder="全部" style="width: 120px">
          <el-option label="进餐" value="meal" />
          <el-option label="饮水" value="water" />
          <el-option label="睡眠" value="sleep" />
          <el-option label="如厕" value="toilet" />
          <el-option label="情绪" value="mood" />
          <el-option label="活动" value="activity" />
        </el-select>
      </el-form-item>
      <el-form-item label="日期">
        <el-date-picker v-model="query.logDate" type="date" value-format="YYYY-MM-DD" placeholder="选择日期" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="loadData">查询</el-button>
      </el-form-item>
    </el-form>
    <el-table :data="tableData" v-loading="loading" border stripe>
      <el-table-column prop="logDate" label="日期" width="120" />
      <el-table-column prop="logType" label="类型" width="80">
        <template #default="{ row }">
          <el-tag size="small">{{ { meal: '进餐', water: '饮水', sleep: '睡眠', toilet: '如厕', mood: '情绪', activity: '活动' }[row.logType] }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="recorderName" label="记录人" width="100" />
      <el-table-column prop="content" label="内容" show-overflow-tooltip />
      <el-table-column prop="temperature" label="体温" width="80" />
      <el-table-column prop="createTime" label="记录时间" width="170" />
    </el-table>
  </el-card>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { nurseryLogApi } from '@/api'

const loading = ref(false)
const tableData = ref<any[]>([])
const query = reactive({ logType: '', logDate: '', page: 1, size: 10 })

async function loadData() {
  loading.value = true
  try {
    const res: any = await nurseryLogApi.list(query)
    tableData.value = res.data?.rows || []
  } finally {
    loading.value = false
  }
}

onMounted(() => loadData())
</script>
