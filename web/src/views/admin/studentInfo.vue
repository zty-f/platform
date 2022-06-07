<template>
  <el-button type="primary" @click="changeStatusOfTableOfSelect">打开/关闭 查询单元</el-button>
  <el-form
    :label-position="right"
    label-width="150px"
    :model="studentQuery"
    style="max-width: 460px"
    v-show="otherVars.showTableOfSelect===true"
    ref="formRef"
  >

    <el-form-item label="student id" prop="id">
      <el-input v-model="studentQuery.id" placeholder="student id" />
    </el-form-item>
    <el-form-item label="student username" prop="username">
      <el-input v-model="studentQuery.username" placeholder="student name" />
    </el-form-item>
    <el-form-item label="student password" prop="password">
      <el-input v-model="studentQuery.password" placeholder="student password" />
    </el-form-item>
    <el-form-item label="student realName" prop="realName">
      <el-input v-model="studentQuery.realName" placeholder="student realName" />
    </el-form-item>
    <el-form-item label="student tel" prop="tel">
      <el-input v-model="studentQuery.tel" placeholder="student tel" />
    </el-form-item>
    <el-form-item label="schoolName" prop="schoolName">
      <el-input v-model="studentQuery.schoolName" placeholder="schoolName" />
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="getStudents(1)">查询学生信息</el-button>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="resetForm(formRef)">重置</el-button>
    </el-form-item>

  </el-form>

  <el-table :data="tableData.studentArray" border style="width: 100%">
    <el-table-column prop="id" label="id" width="180" />
    <el-table-column prop="username" label="username" width="180" />
    <el-table-column prop="password" label="password" width="180" />
    <el-table-column prop="realName" label="realName" width="180" />
    <el-table-column prop="schoolName" label="schoolName" width="180" />
    <el-table-column prop="teamIds" label="teamIds" width="180" />
    <el-table-column prop="tel" label="tel" width="180" />
  </el-table>
  <el-pagination background layout="prev, pager, next" :total="1000" @current-change="getStudents" />
</template>

<script lang="ts" setup>
import axios from "axios";
import { ElMessage, FormInstance } from 'element-plus'
import { reactive, ref } from "vue";

interface Student {
  id: string,
  username: string,
  password: string,
  realName: string,
  schoolName: string,
  teamIds: string,
  tel: string,
  avatarPath: string
}

const formRef = ref<FormInstance>()

const tableData = reactive<{
  studentArray: Student[]
}>({
  studentArray: []
})

let studentQuery = reactive({
  id: null,
  username: null,
  password: null,
  realName: null,
  schoolName: null,
  tel: null,
  startIndex: 0,
  pageSize: 3
})

const selectQuery = (pageNum: number) => {
  studentQuery.startIndex = (pageNum - 1) * studentQuery.pageSize;

  axios.post("/api/admin/getStudentsQuery", studentQuery).then((response) => {
    if (response.data.code === 200) {
      if (response.data.responseBody.length > 0) {
        ElMessage.success("成功")
        writeDataIntoTable(response.data.responseBody);
      } else {
        ElMessage.error("暂无数据")
      }
    } else {
      ElMessage.error("查询失败");
    }
  })
}

const selectFreely = (pageNum: number) => {
  axios.get("/api/admin/getStudents/" + (pageNum - 1) + "/" + 3).then((response) => {
    if (response.data.code === 200) {
      if (response.data.responseBody.length > 0) {
        ElMessage.success("成功")
        writeDataIntoTable(response.data.responseBody);
      } else {
        ElMessage.error("暂无数据")
      }
    } else {
      ElMessage.error("请求错误");
    }
  })
}

const getStudents = (pageNum: number) => {

  if (studentQuery.id != null ||
    studentQuery.username != null ||
    studentQuery.password != null ||
    studentQuery.realName != null ||
    studentQuery.tel != null ||
    studentQuery.schoolName != null) {
    selectQuery(pageNum);
  } else {
    selectFreely(pageNum);
  }

}


const created = () => {
  getStudents(1);
}
created();

const writeDataIntoTable = (data: Student[]) => {
  while (tableData.studentArray.length > 0) {
    tableData.studentArray.pop();
  }
  for (let i = 0; i < data.length; i++) {
    tableData.studentArray.push({
      id: data[i].id,
      username: data[i].username,
      password: data[i].password,
      realName: data[i].realName,
      schoolName: data[i].schoolName,
      teamIds: data[i].teamIds,
      tel: data[i].tel,
      avatarPath: ""
    });
  }
}

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
