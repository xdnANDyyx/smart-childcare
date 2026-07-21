<template>
  <div>
    <el-card shadow="never">
      <!-- 搜索栏 -->
      <el-form inline>
        <el-form-item label="关键词">
          <el-input v-model="query.keyword" placeholder="用户名/昵称/手机号" clearable @keyup.enter="loadData" style="width: 200px" />
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="query.status" placeholder="全部" clearable style="width: 100px">
            <el-option label="正常" value="0" />
            <el-option label="停用" value="1" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="loadData">查询</el-button>
          <el-button @click="resetQuery">重置</el-button>
        </el-form-item>
      </el-form>

      <!-- 操作栏 -->
      <div style="margin-bottom: 12px">
        <el-button type="primary" @click="handleAdd">新增用户</el-button>
      </div>

      <!-- 表格 -->
      <el-table :data="tableData" v-loading="loading" border stripe>
        <el-table-column prop="username" label="用户名" width="120" />
        <el-table-column prop="nickname" label="昵称" width="120" />
        <el-table-column prop="phone" label="手机号" width="130" />
        <el-table-column prop="email" label="邮箱" width="180" />
        <el-table-column prop="sex" label="性别" width="80">
          <template #default="{ row }">{{ row.sex === '0' ? '男' : row.sex === '1' ? '女' : '未知' }}</template>
        </el-table-column>
        <el-table-column prop="status" label="状态" width="80">
          <template #default="{ row }">
            <el-tag :type="row.status === '0' ? 'success' : 'danger'" size="small">{{ row.status === '0' ? '正常' : '停用' }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="170" />
        <el-table-column label="操作" width="200" fixed="right">
          <template #default="{ row }">
            <el-button link type="primary" @click="handleEdit(row)">编辑</el-button>
            <el-button link type="warning" @click="handleResetPwd(row)">重置密码</el-button>
            <el-button link type="danger" @click="handleDelete(row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <!-- 新增/编辑弹窗 -->
    <el-dialog v-model="dialogVisible" :title="isEdit ? '编辑用户' : '新增用户'" width="500px">
      <el-form ref="formRef" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="用户名" prop="username">
          <el-input v-model="form.username" :disabled="isEdit" />
        </el-form-item>
        <el-form-item label="昵称" prop="nickname">
          <el-input v-model="form.nickname" />
        </el-form-item>
        <el-form-item v-if="!isEdit" label="密码" prop="password">
          <el-input v-model="form.password" type="password" placeholder="默认密码: 123456" />
        </el-form-item>
        <el-form-item label="手机号">
          <el-input v-model="form.phone" />
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input v-model="form.email" />
        </el-form-item>
        <el-form-item label="角色">
          <el-select v-model="selectedRoleIds" multiple placeholder="请选择角色" style="width: 100%">
            <el-option
              v-for="role in roleList"
              :key="role.roleId"
              :label="role.roleName"
              :value="role.roleId"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="性别">
          <el-radio-group v-model="form.sex">
            <el-radio value="0">男</el-radio>
            <el-radio value="1">女</el-radio>
            <el-radio value="2">未知</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="状态">
          <el-radio-group v-model="form.status">
            <el-radio value="0">正常</el-radio>
            <el-radio value="1">停用</el-radio>
          </el-radio-group>
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
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox, type FormInstance } from 'element-plus'
import { userApi, roleApi } from '@/api'

const loading = ref(false)
const tableData = ref<any[]>([])
const dialogVisible = ref(false)
const isEdit = ref(false)
const formRef = ref<FormInstance>()

const query = reactive({ keyword: '', status: '' })
const form = reactive<any>({ username: '', nickname: '', password: '', phone: '', email: '', sex: '2', status: '0' })

// 角色列表和选中项
const roleList = ref<any[]>([])
const selectedRoleIds = ref<number[]>([])

const rules = {
  username: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
  nickname: [{ required: true, message: '请输入昵称', trigger: 'blur' }],
}

async function loadData() {
  loading.value = true
  try {
    const res: any = await userApi.list(query)
    tableData.value = res.data || []
  } finally {
    loading.value = false
  }
}

async function loadRoles() {
  const res: any = await roleApi.list()
  roleList.value = res.data || []
}

function resetQuery() {
  query.keyword = ''
  query.status = ''
  loadData()
}

function handleAdd() {
  isEdit.value = false
  Object.assign(form, { userId: null, username: '', nickname: '', password: '', phone: '', email: '', sex: '2', status: '0' })
  selectedRoleIds.value = []
  dialogVisible.value = true
}

async function handleEdit(row: any) {
  isEdit.value = true
  Object.assign(form, row)
  selectedRoleIds.value = []
  dialogVisible.value = true
  try {
    const res: any = await userApi.getRoleIds(row.userId)
    selectedRoleIds.value = res.data || []
  } catch (e) {
    selectedRoleIds.value = []
  }
}

async function handleSave() {
  if (!formRef.value) return
  await formRef.value.validate(async (valid) => {
    if (!valid) return
    if (isEdit.value) {
      await userApi.edit({ user: form, roleIds: selectedRoleIds.value })
    } else {
      await userApi.add({ user: form, roleIds: selectedRoleIds.value })
    }
    ElMessage.success('操作成功')
    dialogVisible.value = false
    loadData()
  })
}

async function handleDelete(row: any) {
  await ElMessageBox.confirm('确定删除该用户？', '提示', { type: 'warning' })
  await userApi.remove(row.userId)
  ElMessage.success('删除成功')
  loadData()
}

async function handleResetPwd(row: any) {
  await ElMessageBox.confirm(`确定重置用户 [${row.username}] 的密码为 123456？`, '提示', { type: 'warning' })
  await userApi.resetPwd(row.userId, '123456')
  ElMessage.success('密码已重置')
}

onMounted(() => {
  loadData()
  loadRoles()
})
</script>
