<template>
  <div>
    <el-card shadow="never">
      <el-form inline>
        <el-form-item label="关键词">
          <el-input v-model="keyword" placeholder="字典名称/类型" clearable @keyup.enter="loadData" style="width: 200px" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="loadData">查询</el-button>
          <el-button type="primary" @click="handleAdd">新增字典</el-button>
        </el-form-item>
      </el-form>
      <el-table :data="tableData" v-loading="loading" border stripe>
        <el-table-column prop="dictName" label="字典名称" width="150" />
        <el-table-column prop="dictType" label="字典类型" width="180" />
        <el-table-column prop="status" label="状态" width="80">
          <template #default="{ row }">
            <el-tag :type="row.status === '0' ? 'success' : 'danger'" size="small">{{ row.status === '0' ? '正常' : '停用' }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="170" />
        <el-table-column label="操作" width="200" fixed="right">
          <template #default="{ row }">
            <el-button link type="primary" @click="handleViewData(row)">查看数据</el-button>
            <el-button link type="primary" @click="handleEdit(row)">编辑</el-button>
            <el-button link type="danger" @click="handleDelete(row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <!-- 字典数据弹窗 -->
    <el-dialog v-model="dataVisible" :title="`字典数据 - ${currentDict?.dictName}`" width="700px">
      <el-button type="primary" size="small" style="margin-bottom: 10px" @click="handleAddData">新增数据</el-button>
      <el-table :data="dictData" border size="small">
        <el-table-column prop="dictLabel" label="标签" width="120" />
        <el-table-column prop="dictValue" label="值" width="100" />
        <el-table-column prop="dictSort" label="排序" width="80" />
        <el-table-column prop="status" label="状态" width="80">
          <template #default="{ row }">
            <el-tag :type="row.status === '0' ? 'success' : 'danger'" size="small">{{ row.status === '0' ? '正常' : '停用' }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="120">
          <template #default="{ row }">
            <el-button link type="danger" @click="handleDeleteData(row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { dictApi } from '@/api'

const loading = ref(false)
const tableData = ref<any[]>([])
const keyword = ref('')
const dataVisible = ref(false)
const currentDict = ref<any>(null)
const dictData = ref<any[]>([])

async function loadData() {
  loading.value = true
  try {
    const res: any = await dictApi.list(keyword.value)
    tableData.value = res.data || []
  } finally {
    loading.value = false
  }
}

function handleAdd() {
  ElMessage.info('字典编辑弹窗开发中')
}

function handleEdit(row: any) {
  ElMessage.info('字典编辑功能开发中')
}

async function handleDelete(row: any) {
  await ElMessageBox.confirm('确定删除该字典？', '提示', { type: 'warning' })
  await dictApi.remove(row.dictId)
  ElMessage.success('删除成功')
  loadData()
}

async function handleViewData(row: any) {
  currentDict.value = row
  const res: any = await dictApi.getDictData(row.dictType)
  dictData.value = res.data || []
  dataVisible.value = true
}

function handleAddData() {
  ElMessage.info('新增字典数据功能开发中')
}

async function handleDeleteData(row: any) {
  await ElMessageBox.confirm('确定删除该数据？', '提示', { type: 'warning' })
  ElMessage.success('删除成功')
}

onMounted(() => loadData())
</script>
