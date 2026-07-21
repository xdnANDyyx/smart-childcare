<template>
  <div>
    <el-card shadow="never">
      <el-form inline>
        <el-form-item label="关键词">
          <el-input v-model="keyword" placeholder="角色名称/编码" clearable @keyup.enter="loadData" style="width: 200px" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="loadData">查询</el-button>
          <el-button type="primary" @click="handleAdd">新增角色</el-button>
        </el-form-item>
      </el-form>
      <el-table :data="tableData" v-loading="loading" border stripe>
        <el-table-column prop="roleName" label="角色名称" width="150" />
        <el-table-column prop="roleKey" label="角色编码" width="150" />
        <el-table-column prop="roleSort" label="排序" width="80" />
        <el-table-column prop="status" label="状态" width="80">
          <template #default="{ row }">
            <el-tag :type="row.status === '0' ? 'success' : 'danger'" size="small">{{ row.status === '0' ? '正常' : '停用' }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="170" />
        <el-table-column label="操作" width="150" fixed="right">
          <template #default="{ row }">
            <el-button link type="primary" @click="handleEdit(row)">编辑</el-button>
            <el-button link type="danger" @click="handleDelete(row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <el-dialog v-model="dialogVisible" :title="isEdit ? '编辑角色' : '新增角色'" width="600px">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="角色名称" prop="roleName"><el-input v-model="form.roleName" /></el-form-item>
        <el-form-item label="角色编码" prop="roleKey"><el-input v-model="form.roleKey" /></el-form-item>
        <el-form-item label="排序"><el-input-number v-model="form.roleSort" :min="0" /></el-form-item>
        <el-form-item label="状态">
          <el-radio-group v-model="form.status">
            <el-radio value="0">正常</el-radio>
            <el-radio value="1">停用</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="菜单权限">
          <div style="border: 1px solid #dcdfe6; border-radius: 4px; padding: 8px; max-height: 300px; overflow-y: auto; width: 100%">
            <el-tree
              ref="menuTreeRef"
              :data="menuTree"
              show-checkbox
              node-key="menuId"
              :props="{ label: 'menuName', children: 'children' }"
              default-expand-all
            />
          </div>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSave">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted, nextTick } from 'vue'
import { ElMessage, ElMessageBox, type FormInstance } from 'element-plus'
import type { ElTree } from 'element-plus'
import { roleApi, menuApi } from '@/api'

const loading = ref(false)
const tableData = ref<any[]>([])
const keyword = ref('')
const dialogVisible = ref(false)
const isEdit = ref(false)
const formRef = ref<FormInstance>()
const menuTreeRef = ref<InstanceType<typeof ElTree>>()
const menuTree = ref<any[]>([])
const form = reactive<any>({ roleName: '', roleKey: '', roleSort: 0, status: '0' })
const rules = {
  roleName: [{ required: true, message: '请输入角色名称', trigger: 'blur' }],
  roleKey: [{ required: true, message: '请输入角色编码', trigger: 'blur' }],
}

async function loadData() {
  loading.value = true
  try {
    const res: any = await roleApi.list(keyword.value)
    tableData.value = res.data || []
  } finally {
    loading.value = false
  }
}

async function loadMenus() {
  try {
    const res: any = await menuApi.list()
    menuTree.value = res.data || []
  } catch (e) {
    menuTree.value = []
  }
}

function handleAdd() {
  isEdit.value = false
  Object.assign(form, { roleId: null, roleName: '', roleKey: '', roleSort: 0, status: '0' })
  dialogVisible.value = true
  nextTick(() => {
    menuTreeRef.value?.setCheckedKeys([])
  })
}

async function handleEdit(row: any) {
  isEdit.value = true
  Object.assign(form, row)
  dialogVisible.value = true
  try {
    const res: any = await roleApi.getMenuIds(row.roleId)
    const allKeys: number[] = res.data || []
    // 只勾选叶子节点，避免父节点被 setCheckedKeys 后级联勾选所有子节点
    const leafIds = getLeafMenuIds(menuTree.value, allKeys)
    nextTick(() => {
      menuTreeRef.value?.setCheckedKeys(leafIds)
    })
  } catch (e) {
    nextTick(() => {
      menuTreeRef.value?.setCheckedKeys([])
    })
  }
}

/** 从菜单树中提取叶子节点ID（在 allKeys 范围内的） */
function getLeafMenuIds(tree: any[], allKeys: number[]): number[] {
  const keySet = new Set(allKeys)
  const leaves: number[] = []
  function walk(nodes: any[]) {
    for (const node of nodes) {
      if (!node.children || node.children.length === 0) {
        if (keySet.has(node.menuId)) leaves.push(node.menuId)
      } else {
        walk(node.children)
      }
    }
  }
  walk(tree)
  return leaves
}

async function handleSave() {
  if (!formRef.value) return
  await formRef.value.validate(async (valid) => {
    if (!valid) return
    // 获取选中的叶子节点 + 半选状态的父节点
    const checkedKeys = menuTreeRef.value?.getCheckedKeys() as number[] || []
    const halfCheckedKeys = menuTreeRef.value?.getHalfCheckedKeys() as number[] || []
    const menuIds = [...checkedKeys, ...halfCheckedKeys]
    if (isEdit.value) await roleApi.edit({ role: form, menuIds })
    else await roleApi.add({ role: form, menuIds })
    ElMessage.success('操作成功')
    dialogVisible.value = false
    loadData()
  })
}

async function handleDelete(row: any) {
  await ElMessageBox.confirm('确定删除该角色？', '提示', { type: 'warning' })
  await roleApi.remove(row.roleId)
  ElMessage.success('删除成功')
  loadData()
}

onMounted(() => {
  loadData()
  loadMenus()
})
</script>
