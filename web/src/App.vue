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
          :router = 'true'
        >
          <el-menu-item index="0" disabled>操作中心</el-menu-item>
          <el-menu-item index="/">首页</el-menu-item>
          <el-menu-item index="2">用户管理</el-menu-item>
          <el-menu-item index="3">报名</el-menu-item>
          <el-menu-item index="/student/teamInfo">Student-Team-Info</el-menu-item>
          <el-menu-item index="/register" :style="{marginLeft:'900px'}">注册</el-menu-item>
          <el-menu-item index="/login" >登录</el-menu-item>
        </el-menu>
      </el-header>

      <el-main>
        <router-view></router-view>
      </el-main>

      <el-footer>
        版权所有ZTY 2022 SWPU
      </el-footer>

    </el-container>
  </div>
</template>

<script>
import axios from "axios";
import {ElMessage} from "_element-plus@2.2.2@element-plus";

export default {
  data() {
    return {
      activeIndex2: '1',
      user:{
        role:"",
        username:""
      }
    };
  },
  methods: {
    handleSelect(key, keyPath) {
      console.log(key, keyPath);
    }
  },

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
</style>
