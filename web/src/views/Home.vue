<template>
  <div class="common-layout">
    <el-container>
      <el-aside width="200px"></el-aside>
      <el-main>
        <div class="demo-collapse">
          <el-collapse accordion>
            <el-collapse-item name="1" v-for="item in project.list">
              <template #title>{{ item.title }}</template>
              <div>项目id：{{ item.id }}</div>
              <br>
              <div>
                {{ item.description }}
              </div>

            </el-collapse-item>
          </el-collapse>
        </div>
      </el-main>
    </el-container>
  </div>


</template>

<script lang="ts" setup>
import {reactive, ref} from 'vue'
import type {FormInstance} from 'element-plus'
import axios from "axios";
import {ElMessage} from "element-plus";

import {InfoFilled} from '@element-plus/icons-vue'

const user = reactive({
  identification: '',
  username: '',
  id: '',
})

const created = () => {
  axios.get("/getCurrentUser", {withCredentials: true}).then((response) => {
    const data = response.data;
    console.log(data);
    if (data.code === 200) {
      ElMessage.success("您当前的身份为" + data.responseBody[0] + "，用户名" + data.responseBody[1] + "，id:" + data.responseBody[2]);
      user.identification = data.responseBody[0];
      user.username = data.responseBody[1];
      user.id = data.responseBody[2];
      console.log(user.username);
    } else {
      ElMessage.error(data.message);
    }
  });
  return
}

created();


const project = reactive({
  list: []
})


const selectAllProjects = () => {
  axios.get("/api/project/selectAll", {withCredentials: true}).then((response) => {
    const data = response.data;
    console.log(data);
    if (data.code === 200) {
      for (let i = 0; i < data.responseBody.length; i++) {
        project.list.push({
          id: data.responseBody[i].id,
          title: data.responseBody[i].title,
          description: data.responseBody[i].description,
        })
      }
    } else {
      ElMessage.error(data.message);
    }
  });
  return
}

selectAllProjects();

</script>

<style scoped>
.el-carousel__item h3 {
  display: flex;
  color: #475669;
  opacity: 0.75;
  line-height: 300px;
  margin: 0;
}

.el-carousel__item:nth-child(2n) {
  background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n + 1) {
  background-color: #d3dce6;
}
</style>
