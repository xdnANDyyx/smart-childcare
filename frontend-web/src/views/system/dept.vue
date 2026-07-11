<template>
  <div>
    <el-card shadow="never">
      <el-form inline>
        <el-form-item label="关键词">
          <el-input v-model="keyword" placeholder="部门名称" clearable @keyup.enter="loadData" style="width: 200px" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="loadData">查询</el-button>
          <el-button type="primary" @click="handleAdd">新增部门</el-button>
        </el-form-item>
      </el-form>
      <el-table :data="tableData" v-loading="loading" row-key="deptId" border :tree-props="{ children: 'children' }" default-expand-all>
        <el-table-column prop="deptName" label="部门名称" width="200" />
        <el-table-column prop="orderNum" label="排序" width="80" />
        <el-table-column prop="leader" label="负责人" width="100" />
        <el-table-column prop="phone" label="联系电话" width="130" />
        <el-table-column prop="status" label="状态" width="80">
          <template #default="{ row }">
            <el-tag :type="row.status === '0' ? 'success' : 'danger'" size="small">{{ row.status === '0' ? '正常' : '停用' }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="200" fixed="right">
          <template #default="{ row }">
            <el-button link type="primary" @click="handleAdd(row)">新增子部门</el-button>
            <el-button link type="primary" @click="handleEdit(row)">编辑</el-button>
            <el-button link type="danger" @click="handleDelete(row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { deptApi } from '@/api'

const loading = ref(false)
const tableData = ref<any[]>([])
const keyword = ref('')

async function loadData() {
  loading.value = true
  try {
    const res: any = await deptApi.list(keyword.value)
    tableData.value = res.data || []
  } finally {
    loading.value = false
  }
}

function handleAdd(row?: any) {
  ElMessage.info('部门编辑弹窗开发中')
}

function handleEdit(row: any) {
  ElMessage.info('部门编辑功能开发中')
}

async function handleDelete(row: any) {
  await ElMessageBox.confirm('确定删除该部门？', '提示', { type: 'warning' })
  await deptApi.remove(row.deptId)
  ElMessage.success('删除成功')
  loadData()
}

onMounted(() => loadData())
</script>
