<template>
  <el-card shadow="never">
    <el-form inline>
      <el-form-item>
        <el-button type="primary" @click="loadData">刷新</el-button>
        <el-button type="primary" @click="handleAdd">新增班级</el-button>
      </el-form-item>
    </el-form>
    <el-table :data="tableData" v-loading="loading" border stripe>
      <el-table-column prop="className" label="班级名称" width="150" />
      <el-table-column prop="classType" label="类型" width="100">
        <template #default="{ row }">{{ row.classType === '1' ? '全天班' : '半天班' }}</template>
      </el-table-column>
      <el-table-column prop="ageRange" label="月龄范围" width="100" />
      <el-table-column prop="capacity" label="容量" width="80" />
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
import { classApi } from '@/api'

const loading = ref(false)
const tableData = ref<any[]>([])

async function loadData() {
  loading.value = true
  try {
    const res: any = await classApi.list({})
    tableData.value = res.data || []
  } finally {
    loading.value = false
  }
}

function handleAdd() { ElMessage.info('班级新增功能开发中') }
function handleEdit(row: any) { ElMessage.info('班级编辑功能开发中') }

async function handleDelete(row: any) {
  await ElMessageBox.confirm('确定删除该班级？', '提示', { type: 'warning' })
  await classApi.remove(row.id)
  ElMessage.success('删除成功')
  loadData()
}

onMounted(() => loadData())
</script>
