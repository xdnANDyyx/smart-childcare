<template>
  <el-card shadow="never">
    <el-form inline>
      <el-form-item label="日期范围">
        <el-date-picker v-model="dateRange" type="daterange" value-format="YYYY-MM-DD" start-placeholder="开始日期" end-placeholder="结束日期" />
      </el-form-item>
      <el-form-item label="状态">
        <el-select v-model="query.status" clearable placeholder="全部" style="width: 120px">
          <el-option label="正常" value="0" />
          <el-option label="请假" value="1" />
          <el-option label="缺勤" value="2" />
          <el-option label="迟到" value="3" />
          <el-option label="早退" value="4" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="loadData">查询</el-button>
      </el-form-item>
    </el-form>
    <el-table :data="tableData" v-loading="loading" border stripe>
      <el-table-column prop="attendanceDate" label="日期" width="120" />
      <el-table-column prop="checkInTime" label="入园时间" width="170" />
      <el-table-column prop="checkOutTime" label="离园时间" width="170" />
      <el-table-column prop="status" label="状态" width="80">
        <template #default="{ row }">
          <el-tag :type="row.status === '0' ? 'success' : row.status === '1' ? 'warning' : 'danger'" size="small">
            {{ { '0': '正常', '1': '请假', '2': '缺勤', '3': '迟到', '4': '早退' }[row.status] }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="checkType" label="打卡方式" width="100">
        <template #default="{ row }">
          {{ { '0': '人脸', '1': '手工', '2': '卡片' }[row.checkType] || '-' }}
        </template>
      </el-table-column>
      <el-table-column prop="remark" label="备注" show-overflow-tooltip />
    </el-table>
  </el-card>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted, watch } from 'vue'
import { attendanceApi } from '@/api'

const loading = ref(false)
const tableData = ref<any[]>([])
const dateRange = ref<string[]>([])
const query = reactive<any>({ startDate: '', endDate: '', status: '', page: 1, size: 10 })

watch(dateRange, (val) => {
  query.startDate = val?.[0] || ''
  query.endDate = val?.[1] || ''
})

async function loadData() {
  loading.value = true
  try {
    const res: any = await attendanceApi.list(query)
    tableData.value = res.data?.rows || []
  } finally {
    loading.value = false
  }
}

onMounted(() => loadData())
</script>
