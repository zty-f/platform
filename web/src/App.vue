<template>
  <div id="app">
    <el-container>
      <el-header>
        <!-- 导航条 -->
        <el-menu
          :default-active="activeIndex2"
          class="el-menu-demo"
          mode="horizontal"
          background-color="#545c64"
          text-color="#fff"
          active-text-color="#ffd04b"
          @select="x"
          :router='true'
        >
          <el-menu-item index="0" disabled>操作中心</el-menu-item>
          <el-menu-item index="/">首页</el-menu-item>
          <!--          <el-menu-item index="/2">用户管理</el-menu-item>-->
          <!--          <el-menu-item index="/"></el-menu-item>-->
          <el-menu-item index="/teamInfo"
                        v-show="identification.identification!=='' && identification.identification!=='admin'">队伍信息
          </el-menu-item>
          <el-menu-item index="/createTeam"
                        v-show="identification.identification!==''&&identification.identification==='student'">创建队伍
          </el-menu-item>
          <el-sub-menu index="" v-show="identification.identification==='admin'">
            <template #title>admin</template>
            <el-menu-item index="/admin/teamInfo">队伍信息</el-menu-item>
            <el-menu-item index="2-2">item two</el-menu-item>
            <el-menu-item index="2-3">item three</el-menu-item>
          </el-sub-menu>


          <el-menu-item index="/register" :style="{marginLeft:'900px'}" v-show="isShowLoginAndRegister">注册
          </el-menu-item>
          <el-menu-item index="/" @click="isShowLoginForm = true" v-show="isShowLoginAndRegister">登录</el-menu-item>
          <el-menu-item index="/" :style="{marginLeft:'900px'}" v-show="isShowLogOut" @click="logout()">退出登录
          </el-menu-item>
        </el-menu>
      </el-header>

      <el-dialog title="登录" v-model="isShowLoginForm" :before-close="handleClose">
        <el-card class="box-card" :style="{margin:'auto'}">


          <el-form :model="user" label-width="80px" ref="loginFormRef">
            <el-form-item label="角色">
              <el-select v-model="identification.identification" placeholder="请选择角色">
                <el-option label="admin" value="admin" />
                <el-option label="teacher" value="teacher" />
                <el-option label="student" value="student" />
              </el-select>
            </el-form-item>
            <el-form-item label="用户名" prop="username">
              <el-input v-model="user.username" prefix-icon="el-icon-user" placeholder="请输入用户名"></el-input>
            </el-form-item>
            <el-form-item label="密码" prop="password">
              <el-input v-model="user.password" prefix-icon="el-icon-lock" type="password"
                        placeholder="请输入密码"></el-input>
            </el-form-item>


            <el-form-item>
              <el-button type="primary" @click="login()">登录</el-button>
              <el-button type="info" @click="resetForm(loginFormRef)">重置</el-button>
            </el-form-item>
          </el-form>
        </el-card>
      </el-dialog>


      <el-main>
        <router-view></router-view>
      </el-main>

      <el-footer>
        版权所有ZTY 2022 SWPU
      </el-footer>

    </el-container>
  </div>
</template>

<script lang="ts" setup>

import { reactive, ref } from 'vue';
import axios from "axios";
import { ElMessage, ElMessageBox } from 'element-plus';
import type { FormInstance } from 'element-plus'
import router from "@/router";
import { Component, Vue } from 'vue-property-decorator'

const loginFormRef = ref<FormInstance>()
const isShowLoginForm = ref(false);
const isShowLoginAndRegister = ref(true);
const isShowLogOut = ref(false);

const identification = reactive({
  identification: '',

})

const user = reactive({
  username: '',
  password: '',
})


const created = () => {
  axios.get("/getCurrentUser", {withCredentials: true}).then((response) => {
    const data = response.data;
    console.log(data);
    console.log("app.vue created:" + data.responseBody[0]);
    if (data.code === 200) {
      isShowLoginAndRegister.value = false;
      isShowLogOut.value = true;
      identification.identification = data.responseBody[0];
      console.log("created:" + identification.identification);
    } else {
      isShowLoginAndRegister.value = true;
      isShowLogOut.value = false;
      ElMessage.error(data.message);
    }
  });
}

created();

const login = () => {
  console.log('登录中...');
  let urlPrefix = "/api/" + identification.identification + "/login"
  axios.post(urlPrefix, user).then((response) => {
    const data = response.data;

    if (data.code == 200) {

      ElMessage.success("登录成功！");
      // window.location.href = 'Home.vue'
      isShowLoginForm.value = false;
      isShowLoginAndRegister.value = false;
      isShowLogOut.value = true;
      router.push({path: '/'})

    } else {
      identification.identification = "";
      ElMessage.error(data.message);
    }
  });
  return
}

const logout = () => {
  console.log('退出登录中...');


  let urlPrefix = "/api/" + identification.identification + "/logout"
  axios.post(urlPrefix, user).then((response) => {
    const data = response.data;
    isShowLoginAndRegister.value = true;
    isShowLogOut.value = false;
    router.push({path: '/'})
    identification.identification = "";
    if (data.code == 200) {
      ElMessage.success("退出登录！");
      // window.location.href = 'Home.vue'

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

const handleClose = (done: () => void) => {
  ElMessageBox.confirm('确定关闭登录窗口?')
    .then(() => {
      done()
    })
    .catch(() => {
      // catch error
    })
}
</script>

<style>
.el-header, .el-footer {
  background-color: #545c64;
  color: #333;
  text-align: center;
  line-height: 60px;
  padding: 0;
}

.box-card {
  width: 480px;
}
</style>
