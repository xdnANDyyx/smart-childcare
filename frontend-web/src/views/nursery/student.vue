<template>
  <el-card shadow="never">
    <el-form inline>
      <el-form-item label="关键词">
        <el-input v-model="query.keyword" placeholder="儿童姓名" clearable @keyup.enter="loadData" style="width: 180px" />
      </el-form-item>
      <el-form-item label="状态">
        <el-select v-model="query.status" clearable placeholder="全部" style="width: 100px">
          <el-option label="在托" value="0" />
          <el-option label="离托" value="1" />
          <el-option label="待入托" value="2" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="loadData">查询</el-button>
        <el-button type="primary" @click="handleAdd">新增儿童</el-button>
      </el-form-item>
    </el-form>
    <el-table :data="tableData" v-loading="loading" border stripe>
      <el-table-column prop="name" label="姓名" width="100" />
      <el-table-column prop="gender" label="性别" width="60">
        <template #default="{ row }">{{ row.gender === '0' ? '男' : '女' }}</template>
      </el-table-column>
      <el-table-column prop="birthDate" label="出生日期" width="120" />
      <el-table-column prop="enrollDate" label="入托日期" width="120" />
      <el-table-column prop="status" label="状态" width="80">
        <template #default="{ row }">
          <el-tag :type="row.status === '0' ? 'success' : row.status === '2' ? 'info' : 'danger'" size="small">
            {{ { '0': '在托', '1': '离托', '2': '待入托' }[row.status] }}
          </el-tag>
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
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { studentApi } from '@/api'

const loading = ref(false)
const tableData = ref<any[]>([])
const query = reactive({ keyword: '', status: '', page: 1, size: 10 })

async function loadData() {
  loading.value = true
  try {
    const res: any = await studentApi.list(query)
    tableData.value = res.data?.rows || []
  } finally {
    loading.value = false
  }
}

function handleAdd() { ElMessage.info('儿童新增功能开发中') }
function handleEdit(row: any) { ElMessage.info('儿童编辑功能开发中') }

async function handleDelete(row: any) {
  await ElMessageBox.confirm('确定删除该儿童记录？', '提示', { type: 'warning' })
  await studentApi.remove(row.id)
  ElMessage.success('删除成功')
  loadData()
}

onMounted(() => loadData())
</script>
