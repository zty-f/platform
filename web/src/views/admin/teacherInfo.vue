<template>
  <el-button type="primary" @click="changeStatusOfTableOfSelect">打开/关闭 查询单元</el-button>
  <el-form
    :label-position="right"
    label-width="150px"
    :model="teacherQuery"
    style="max-width: 460px"
    v-show="otherVars.showTableOfSelect===true"
    ref="formRef"
  >
    <el-form-item label="teacher id" prop="id">
      <el-input v-model="teacherQuery.id" placeholder="teacher id" />
    </el-form-item>
    <el-form-item label="teacher username" prop="username">
      <el-input v-model="teacherQuery.username" placeholder="teacher name" />
    </el-form-item>
    <el-form-item label="teacher password" prop="password">
      <el-input v-model="teacherQuery.password" placeholder="teacher password" />
    </el-form-item>
    <el-form-item label="teacher realName" prop="realName">
      <el-input v-model="teacherQuery.realName" placeholder="teacher realName" />
    </el-form-item>
    <el-form-item label="tel" prop="tel">
      <el-input v-model="teacherQuery.tel" placeholder="tel" />
    </el-form-item>
    <el-form-item label="schoolName" prop="schoolName">
      <el-input v-model="teacherQuery.schoolName" placeholder="schoolName" />
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="getTeachers(1)">查询老师信息</el-button>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="resetForm(formRef)">重置</el-button>
    </el-form-item>
  </el-form>

  <el-table :data="tableData.list" stripe style="width: 100%">
    <el-table-column prop="id" label="id" width="180" />
    <el-table-column prop="username" label="username" width="180" />
    <el-table-column prop="password" label="password" width="180px" />
    <el-table-column prop="realName" label="realName" width="180" />
    <el-table-column prop="teamIds" label="teamIds" width="180" />
    <el-table-column prop="schoolName" label="schoolName" width="180px" />
    <el-table-column prop="tel" label="tel" width="180" />

  </el-table>
</template>

<script lang="ts" setup>

import { reactive, ref } from "vue";
import axios from "axios";
import { ElMessage, FormInstance } from "element-plus";

const formRef = ref<FormInstance>()

interface Teacher {
  id: number,
  username: string,
  password: string,
  realName: string,
  teamIds: string,
  tel: string,
  avatarPath: string
  schoolName: string
}

const tableData = reactive<{
  list: Teacher[],
}>({
  list: []
})

let teacherQuery = reactive({
  id: null,
  username: null,
  password: null,
  realName: null,
  tel: null,
  schoolName: null
})

const writeDataIntoTable = (data: Teacher[]) => {
  while (tableData.list.length > 0) {
    tableData.list.pop();
  }
  for (let i = 0; i < data.length; i++) {
    tableData.list.push({
      id: data[i].id,
      username: data[i].username,
      password: data[i].password,
      realName: data[i].realName,
      teamIds: data[i].teamIds,
      schoolName: data[i].schoolName,
      tel: data[i].tel,
      avatarPath: "",

    })
  }
}

const selectFreely = (pageNum: number) => {
  axios.get("http://localhost:8080/api/admin/getTeachers/" + (pageNum - 1) + "/" + 3).then((response) => {
    if (response.data.code === 200) {
      ElMessage.success("成功");
      writeDataIntoTable(response.data.responseBody);
    } else {
      ElMessage.error("失败");
    }
  })
}

const selectQuery = (pageNum: number) => {
  axios.get("http://localhost:8080/api/admin/getTeachers/" + (pageNum - 1) + "/" + 3).then((response) => {
    if (response.data.code === 200) {
      ElMessage.success("成功");
      writeDataIntoTable(response.data.responseBody);
    } else {
      ElMessage.error("失败");
    }
  })
}


const created = () => {
  selectFreely(1);
}
created();

let otherVars = reactive({
  showTableOfSelect: false,
});

const changeStatusOfTableOfSelect = () => {
  otherVars.showTableOfSelect = !otherVars.showTableOfSelect;
}


const resetForm = (formEl: FormInstance | undefined) => {
  if (!formEl) return
  formEl.resetFields()
}

</script>
