<template>
  <div>
    <el-card shadow="never">
      <div style="margin-bottom: 12px">
        <el-button type="primary" @click="handleAdd()">新增菜单</el-button>
      </div>
      <el-table :data="tableData" v-loading="loading" row-key="menuId" border :tree-props="{ children: 'children' }" default-expand-all>
        <el-table-column prop="menuName" label="菜单名称" width="200" />
        <el-table-column prop="icon" label="图标" width="80" align="center">
          <template #default="{ row }">
            <el-icon v-if="row.icon"><component :is="row.icon" /></el-icon>
          </template>
        </el-table-column>
        <el-table-column prop="menuType" label="类型" width="80">
          <template #default="{ row }">{{ row.menuType === 'M' ? '目录' : row.menuType === 'C' ? '菜单' : '按钮' }}</template>
        </el-table-column>
        <el-table-column prop="path" label="路由地址" width="150" />
        <el-table-column prop="perms" label="权限标识" width="180" />
        <el-table-column prop="orderNum" label="排序" width="80" />
        <el-table-column prop="status" label="状态" width="80">
          <template #default="{ row }">
            <el-tag :type="row.status === '0' ? 'success' : 'danger'" size="small">{{ row.status === '0' ? '正常' : '停用' }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="200" fixed="right">
          <template #default="{ row }">
            <el-button link type="primary" @click="handleAdd(row)">新增子菜单</el-button>
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
import { menuApi } from '@/api'

const loading = ref(false)
const tableData = ref<any[]>([])

async function loadData() {
  loading.value = true
  try {
    const res: any = await menuApi.list()
    tableData.value = res.data || []
  } finally {
    loading.value = false
  }
}

function handleAdd(row?: any) {
  ElMessage.info('菜单编辑功能开发中，当前为框架骨架')
}

function handleEdit(row: any) {
  ElMessage.info('菜单编辑功能开发中')
}

async function handleDelete(row: any) {
  await ElMessageBox.confirm('确定删除该菜单？', '提示', { type: 'warning' })
  await menuApi.remove(row.menuId)
  ElMessage.success('删除成功')
  loadData()
}

onMounted(() => loadData())
</script>
