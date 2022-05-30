<template>
  <el-table :data="tableData" style="width: 100%">
    <el-table-column prop="id" label="队伍id" width="180"/>
    <el-table-column prop="name" label="队伍name" width="180"/>
    <el-table-column prop="memberList" label="成员"/>
    <el-table-column prop="projectName" label="所选项目"/>
    <el-table-column prop="leaderId" label="队长id"/>
    <el-table-column prop="leaderUsername" label="队长username"/>
    <el-table-column prop="leaderRealName" label="队长realname"/>
    <el-table-column prop="committed" label="提交状态"/>
    <el-table-column prop="teacherId" label="辅导老师id"/>
    <el-table-column prop="teacherName" label="辅导老师name"/>
    <el-table-column prop="projectPath" label="项目地址"/>
  </el-table>
</template>

<script lang="ts" setup>
import axios from "axios";
import {ElMessage} from "element-plus";
import router from "@/router";
import {reactive} from "vue";

const tableData = reactive([
  {
    id: '',
    name: '',
    isLeader: '',
    memberList: '',
    projectName: '',
    leaderId: '',
    leaderUsername: '',
    leaderRealName: '',
    committed: '',
    teacherId: '',
    teacherName: '',
    projectPath: '',
  }
])

const user = reactive({
  identification: '',
  username: '',
  id: '',
})

const writeDataIntoTable = (data) => {
  for (let i = 0; i < data.length; i++) {
    let memberList = '';
    for (let j = 0; j < data[i].memberList.length; j++) {
      memberList += data[i].memberList[j][0];
      memberList +="  "
      memberList += data[i].memberList[j][1];
      memberList +="，"
    }
    tableData.push({
      id: data[i].id,
      name: data[i].name,
      isLeader: data[i].isLeader,
      memberList: memberList,
      projectName: data[i].projectName,
      leaderId: data[i].leaderId,
      leaderUsername: data[i].leaderUsername,
      leaderRealName: data[i].leaderRealName,
      committed: data[i].committed,
      teacherId: data[i].teacherId,
      teacherName: data[i].teacherName,
      projectPath: data[i].projectPath
    })
  }
  console.log(tableData);
}

const created = () => {
  axios.get("/api/admin/getTeams/" + 0 + "/" + 10, {withCredentials: true}).then((response) => {
    const data = response.data;
    if (data.code === 200) {
      console.log(data);
      writeDataIntoTable(data.responseBody);
    } else {
      ElMessage.error(data.message);
    }
  });
  return
}

created();

</script>
