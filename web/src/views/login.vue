<template>
  <el-card class="box-card" :style="{margin:'auto'}">

    <el-form :model="user" label-width="80px" ref="loginFormRef">
      <el-form-item label="用户名" prop="username">
        <el-input v-model="user.username" prefix-icon="el-icon-user" placeholder="请输入用户名"></el-input>
      </el-form-item>
      <el-form-item label="密码" prop="password">
        <el-input v-model="user.password" prefix-icon="el-icon-lock" type="password" placeholder="请输入密码"></el-input>
      </el-form-item>


      <el-form-item>
        <el-button type="primary" @click="login()">登录</el-button>
        <el-button type="info" @click="resetForm(loginFormRef)">重置</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>


<script lang="ts" setup>
import {reactive, ref} from 'vue';
import axios from "axios";
import {ElMessage} from 'element-plus';
import type { FormInstance } from 'element-plus'


const loginFormRef = ref<FormInstance>()


const user = reactive({
  username: '',
  password: '',
})

const login = ()=>{
  console.log('登录中...');
  axios.post("/api/student/login", user).then((response) => {
    const data = response.data;
    if (data.code == 200) {
      ElMessage.success("登录成功！");
      window.location.href = 'Home.vue'
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

</script>

<style>
.box-card {
  width: 480px;
}
</style>
