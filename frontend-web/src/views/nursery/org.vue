<template>
  <div>
    <el-card shadow="never">
      <el-form inline>
        <el-form-item label="关键词">
          <el-input v-model="query.keyword" placeholder="机构名称" clearable @keyup.enter="loadData" style="width: 200px" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="loadData">查询</el-button>
          <el-button type="primary" @click="handleAdd">新增机构</el-button>
        </el-form-item>
      </el-form>
      <el-table :data="tableData" v-loading="loading" border stripe>
        <el-table-column prop="orgName" label="机构名称" width="200" />
        <el-table-column prop="orgType" label="类型" width="100">
          <template #default="{ row }">
            {{ { '0': '全日托', '1': '半日托', '2': '计时托', '3': '临时托' }[row.orgType] || '-' }}
          </template>
        </el-table-column>
        <el-table-column prop="principal" label="负责人" width="100" />
        <el-table-column prop="phone" label="电话" width="130" />
        <el-table-column prop="address" label="地址" show-overflow-tooltip />
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
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { orgApi } from '@/api'

const loading = ref(false)
const tableData = ref<any[]>([])
const query = reactive({ keyword: '', page: 1, size: 10 })

async function loadData() {
  loading.value = true
  try {
    const res: any = await orgApi.list(query)
    tableData.value = res.data?.rows || []
  } finally {
    loading.value = false
  }
}

function handleAdd() {
  ElMessage.info('机构新增弹窗开发中')
}

function handleEdit(row: any) {
  ElMessage.info('机构编辑功能开发中')
}

async function handleDelete(row: any) {
  await ElMessageBox.confirm('确定删除该机构？', '提示', { type: 'warning' })
  await orgApi.remove(row.id)
  ElMessage.success('删除成功')
  loadData()
}

onMounted(() => loadData())
</script>
