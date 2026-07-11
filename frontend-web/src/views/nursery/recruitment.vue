<template>
  <el-card shadow="never">
    <el-form inline>
      <el-form-item label="关键词">
        <el-input v-model="query.keyword" placeholder="儿童/家长/电话" clearable @keyup.enter="loadData" style="width: 200px" />
      </el-form-item>
      <el-form-item label="状态">
        <el-select v-model="query.followStatus" clearable placeholder="全部" style="width: 120px">
          <el-option label="新线索" value="0" />
          <el-option label="跟进中" value="1" />
          <el-option label="已到访" value="2" />
          <el-option label="已转化" value="3" />
          <el-option label="已流失" value="4" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="loadData">查询</el-button>
        <el-button type="primary" @click="handleAdd">新增线索</el-button>
      </el-form-item>
    </el-form>
    <el-table :data="tableData" v-loading="loading" border stripe>
      <el-table-column prop="childName" label="儿童姓名" width="100" />
      <el-table-column prop="parentName" label="家长姓名" width="100" />
      <el-table-column prop="phone" label="电话" width="130" />
      <el-table-column prop="intendedClassType" label="意向班型" width="100" />
      <el-table-column prop="intentionLevel" label="意向" width="80">
        <template #default="{ row }">
          <el-tag :type="row.intentionLevel === 3 ? 'danger' : row.intentionLevel === 2 ? 'warning' : 'info'" size="small">
            {{ { 1: '低', 2: '中', 3: '高' }[row.intentionLevel] }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="channel" label="渠道" width="100" />
      <el-table-column prop="followStatus" label="状态" width="90">
        <template #default="{ row }">
          <el-tag size="small">{{ { '0': '新线索', '1': '跟进中', '2': '已到访', '3': '已转化', '4': '已流失' }[row.followStatus] }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="lastFollowTime" label="最后跟进" width="170" />
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
import { recruitmentApi } from '@/api'

const loading = ref(false)
const tableData = ref<any[]>([])
const query = reactive({ keyword: '', followStatus: '', page: 1, size: 10 })

async function loadData() {
  loading.value = true
  try {
    const res: any = await recruitmentApi.list(query)
    tableData.value = res.data?.rows || []
  } finally {
    loading.value = false
  }
}

function handleAdd() { ElMessage.info('新增线索功能开发中') }
function handleEdit(row: any) { ElMessage.info('编辑线索功能开发中') }

async function handleDelete(row: any) {
  await ElMessageBox.confirm('确定删除该线索？', '提示', { type: 'warning' })
  await recruitmentApi.remove(row.id)
  ElMessage.success('删除成功')
  loadData()
}

onMounted(() => loadData())
</script>
