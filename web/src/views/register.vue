<template>
  <el-card class="box-card" :style="{margin:'auto'}">
    <el-form :model="user" label-width="80px" ref="loginFormRef">
      <el-form-item label="角色">
        <el-select v-model="identification.identification" placeholder="请选择角色">
          <el-option label="teacher" value="teacher"/>
          <el-option label="student" value="student"/>
        </el-select>
      </el-form-item>

      <el-form-item label="用户名" prop="username">
        <el-input v-model="user.username" prefix-icon="el-icon-user" placeholder="请输入用户名"></el-input>
      </el-form-item>
      <el-form-item label="密码" prop="password">
        <el-input v-model="user.password" prefix-icon="el-icon-lock" type="password" placeholder="请输入密码"></el-input>
      </el-form-item>

      <el-form-item label="真实姓名" prop="realName">
        <el-input v-model="user.realName" prefix-icon="el-icon-lock" placeholder="请输入真实姓名"></el-input>
      </el-form-item>

      <el-form-item label="学校名称">
        <el-input v-model="school.name" prefix-icon="el-icon-lock" placeholder="请输入学校名称"></el-input>
        <el-button type="primary" @click="getSchoolNameById(school.schoolName)">查询学校</el-button>
      </el-form-item>

      <el-form-item label="学校Id" prop="schoolId">
        <el-input v-model="user.schoolId" prefix-icon="el-icon-lock" placeholder="学校Id" disabled></el-input>
      </el-form-item>


      <el-form-item label="电话" prop="tel">
        <el-input v-model="user.tel" prefix-icon="el-icon-lock" placeholder="请输入电话"></el-input>
      </el-form-item>


      <el-form-item>
        <el-button type="primary" @click="register()">注册</el-button>
        <el-button type="info" @click="resetForm(loginFormRef)">重置</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>


<script lang="ts" setup>
import {reactive, ref} from 'vue';
import axios from "axios";
import {ElMessage} from 'element-plus';
import type {FormInstance} from 'element-plus'
import router from "@/router";


const loginFormRef = ref<FormInstance>()

const school = reactive({
  name: '',
});

const identification = reactive({
  identification: '',
})

const user = reactive({
  username: '',
  password: '',
  realName: '',
  schoolId: '',
  tel: ''
})

const register = () => {
  console.log('注册中...');
  axios.post("/api/" + identification.identification + "/register", user).then((response) => {
    const data = response.data;
    if (data.code == 200) {
      ElMessage.success("注册成功！");
      router.push({path: '/'})
    } else {
      ElMessage.error(data.message);
    }
  });
  return
}

const resetForm = (formEl: FormInstance | undefined) => {
  if (!formEl) return
  formEl.resetFields()
}

const getSchoolNameById = () => {
  axios.post("/api/school/getIdByName", school).then((response) => {
    const data = response.data;
    if (data.code != 200) {
      ElMessage.error(data.message);
      user.schoolId = "";
    } else {
      ElMessage.success("学校id为：" + data.responseBody);
      user.schoolId = data.responseBody;
    }
  });
  return
}


</script>

<style>
.box-card {
  width: 480px;
}
</style>
