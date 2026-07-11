<template>
  <el-card shadow="never">
    <el-form inline>
      <el-form-item label="类型">
        <el-select v-model="query.recordType" clearable placeholder="全部" style="width: 120px">
          <el-option label="体检" value="0" />
          <el-option label="体测" value="1" />
          <el-option label="专案" value="2" />
          <el-option label="日常" value="3" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="loadData">查询</el-button>
      </el-form-item>
    </el-form>
    <el-table :data="tableData" v-loading="loading" border stripe>
      <el-table-column prop="recordDate" label="记录日期" width="120" />
      <el-table-column prop="recordType" label="类型" width="80">
        <template #default="{ row }">{{ { '0': '体检', '1': '体测', '2': '专案', '3': '日常' }[row.recordType] }}</template>
      </el-table-column>
      <el-table-column prop="height" label="身高(cm)" width="100" />
      <el-table-column prop="weight" label="体重(kg)" width="100" />
      <el-table-column prop="temperature" label="体温" width="80" />
      <el-table-column prop="allergyInfo" label="过敏信息" show-overflow-tooltip />
      <el-table-column prop="growthEvaluation" label="生长评价" show-overflow-tooltip />
      <el-table-column prop="specialType" label="专案" width="80">
        <template #default="{ row }">
          <el-tag v-if="row.specialType" type="warning" size="small">
            {{ { '0': '体弱儿', '1': '肥胖儿', '2': '过敏', '3': '其他' }[row.specialType] }}
          </el-tag>
        </template>
      </el-table-column>
    </el-table>
  </el-card>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { healthApi } from '@/api'

const loading = ref(false)
const tableData = ref<any[]>([])
const query = reactive({ recordType: '', page: 1, size: 10 })

async function loadData() {
  loading.value = true
  try {
    const res: any = await healthApi.list(query)
    tableData.value = res.data?.rows || []
  } finally {
    loading.value = false
  }
}

onMounted(() => loadData())
</script>
