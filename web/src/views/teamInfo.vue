<template>
  <el-table v-bind:data="tableData.list" style="width: 100%" @row-dblclick="">
    <el-table-column prop="id" label="id" width="150" />
    <el-table-column prop="name" label="name" width="120" />
    <el-table-column prop="isLeader" label="isLeader" width="120" />
    <el-table-column prop="memberList" label="memberList" width="120" />
    <el-table-column prop="projectName" label="projectName" width="120" />
    <el-table-column prop="leaderRealName" label="leaderRealName" width="120" />
    <el-table-column prop="committed" label="committed" width="120" />
    <el-table-column prop="teacherName" label="teacherName" width="120" />
    <el-table-column prop="projectPath" label="projectPath" width="120" />
    <el-table-column fixed="right" label="Operations" width="500px">
      <template #default="scope">
        <span v-show="scope.row.isLeader===true">
          <el-button type="success" size="medium" @click.prevent="ToUpdateTeamInfo(scope.$index)">编辑</el-button>
          <el-button type="primary" size="medium" @click.prevent="ToUploadFile(scope.$index)">上传文件</el-button>
        </span>
        <el-button v-show="scope.row.committed===true" type="primary" @click.preven="downFile(scope.$index)">下载文件
        </el-button>
      </template>
    </el-table-column>
  </el-table>
</template>

<script lang="ts" setup>
import axios from "axios";
import { ElMessage } from "element-plus";

import { reactive } from "vue";
import { useRouter } from 'vue-router'

//没这句还跳不过去
const router = useRouter()


const tableData = reactive<{
  list: Item[]
}>({
  list: []
})


const user = reactive({
  identification: '',
  username: '',
  id: '',
})

interface Item {
  id: string
  name: string
  isLeader: boolean
  memberList: string
  projectName: string
  leaderRealName: string
  committed: string
  teacherName: string
  projectPath: string
}

const writeDataIntoTable = (data: string | any[]) => {
  for (let i = 0; i < data.length; i++) {
    let memberList = '';
    for (let j = 0; j < data[i].memberList.length; j++) {
      memberList += data[i].memberList[j][0];
      memberList += '  ';
      memberList += data[i].memberList[j][1];
      memberList += '，';
    }
    tableData.list.push({
      id: data[i].id,
      name: data[i].name,
      isLeader: data[i].isLeader,
      memberList: memberList,
      projectName: data[i].projectName,
      leaderRealName: data[i].leaderRealName,
      committed: data[i].committed,
      teacherName: data[i].teacherName,
      projectPath: data[i].projectPath
    })
  }
  console.log(tableData);
}

const created = () => {
  axios.get("/getCurrentUser", {withCredentials: true}).then((response) => {
    const data = response.data;
    console.log(data);
    if (data.code === 200) {
      ElMessage.success("您当前的身份为" + data.responseBody[0] + "，用户名" + data.responseBody[1]);
      user.identification = data.responseBody[0];
      user.username = data.responseBody[1];
      axios.post("/api/" + user.identification + "/teamsInfo", {withCredentials: true}).then((response) => {
        const data = response.data;

        if (data.code === 200) {
          console.log(data);
          writeDataIntoTable(data.responseBody);
        } else {
          ElMessage.error(data.message);
        }
      });

    } else {
      ElMessage.error(data.message);
    }
  });
  return
}

const ToUpdateTeamInfo = (index: number) => {
  if (tableData.list[index].isLeader) {
    router.push({
      name: "updateTeamInfo",
      query: {
        teamId: tableData.list[index].id,
      },
    });
  } else {
    alert("无权限");
  }
}
created();

const ToUploadFile = (index: number) => {
  if (tableData.list[index].isLeader) {
    router.push({
      name: "uploadFile",
      query: {
        teamId: tableData.list[index].id,
      },
    });
  } else {
    alert("无权限");
  }
}

const downFile = (index: number) => {

  window.location.href = "http://localhost:8500/platform/" + tableData.list[index].projectPath;
}

</script>
