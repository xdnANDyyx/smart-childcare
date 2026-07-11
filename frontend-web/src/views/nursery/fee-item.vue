<template>
  <el-card shadow="never">
    <el-form inline>
      <el-form-item>
        <el-button type="primary" @click="loadData">刷新</el-button>
        <el-button type="primary" @click="handleAdd">新增项目</el-button>
      </el-form-item>
    </el-form>
    <el-table :data="tableData" v-loading="loading" border stripe>
      <el-table-column prop="itemName" label="项目名称" width="150" />
      <el-table-column prop="itemType" label="类型" width="100">
        <template #default="{ row }">
          {{ { '0': '托育费', '1': '餐费', '2': '住宿费', '3': '教材费', '4': '保险费', '5': '其他' }[row.itemType] }}
        </template>
      </el-table-column>
      <el-table-column prop="standardFee" label="收费标准(元)" width="120" />
      <el-table-column prop="billingCycle" label="计费周期" width="100">
        <template #default="{ row }">
          {{ { '0': '月', '1': '季', '2': '学期', '3': '年', '4': '次' }[row.billingCycle] }}
        </template>
      </el-table-column>
      <el-table-column prop="status" label="状态" width="80">
        <template #default="{ row }">
          <el-tag :type="row.status === '0' ? 'success' : 'danger'" size="small">{{ row.status === '0' ? '正常' : '停用' }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="150" fixed="right">
        <template #default="{ row }">
          <el-button link type="primary" @click="handleEdit(row)">编辑</el-button>
          <el-button link type="danger" @click="handleDelete(row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
  </el-card>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { feeItemApi } from '@/api'

const loading = ref(false)
const tableData = ref<any[]>([])

async function loadData() {
  loading.value = true
  try {
    const res: any = await feeItemApi.list({})
    tableData.value = res.data || []
  } finally {
    loading.value = false
  }
}

function handleAdd() { ElMessage.info('收费项目新增功能开发中') }
function handleEdit(row: any) { ElMessage.info('收费项目编辑功能开发中') }

async function handleDelete(row: any) {
  await ElMessageBox.confirm('确定删除该收费项目？', '提示', { type: 'warning' })
  await feeItemApi.remove(row.id)
  ElMessage.success('删除成功')
  loadData()
}

onMounted(() => loadData())
</script>
