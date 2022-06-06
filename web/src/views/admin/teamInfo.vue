<template>
  <el-button type="primary" @click="changeStatusOfTableOfSelect">打开/关闭 查询单元</el-button>
  <el-form
    :label-position="left"
    label-width="150px"
    :model="selectTeam"
    style="max-width: 460px"
    v-show="otherVars.showTableOfSelect===true"
  >
    <el-form-item label="team id">
      <el-input v-model="selectTeam.id" placeholder="team id" />
    </el-form-item>
    <el-form-item label="team name">
      <el-input v-model="selectTeam.name" placeholder="team name" />
    </el-form-item>
    <el-form-item label="project name">
      <el-select v-model="selectTeam.projectName" placeholder="project name">
        <el-option label="基于深度学习的人脸识别项目" value="基于深度学习的人脸识别项目" />
        <el-option label="新闻文本分类项目" value="新闻文本分类项目" />
        <el-option label="烟头智能监测" value="烟头智能监测" />
      </el-select>
    </el-form-item>
    <el-form-item label="leader id">
      <el-input v-model="selectTeam.leaderId" placeholder="leader id" />
    </el-form-item>
    <el-form-item label="leader username">
      <el-input v-model="selectTeam.leaderUsername" placeholder="leader username" />
    </el-form-item>
    <el-form-item label="leader realname">
      <el-input v-model="selectTeam.realName" placeholder="leader realname" />
    </el-form-item>
    <el-form-item label="committed">
      <el-input v-model="selectTeam.committed" placeholder="committed" />
    </el-form-item>
    <el-form-item label="teacher id">
      <el-input v-model="selectTeam.teacherId" placeholder="teacher id" />
    </el-form-item>
    <el-form-item label="teacher realName">
      <el-input v-model="selectTeam.teacherName" placeholder="teacher realName" />
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="selectQuery">查询队伍信息</el-button>
    </el-form-item>
  </el-form>

  <el-table :data="tableData" style="width: 100%">
    <el-table-column prop="id" label="队伍id" width="180" />
    <el-table-column prop="name" label="队伍name" width="180" />
    <el-table-column prop="memberList" label="成员" />
    <el-table-column prop="projectName" label="所选项目" />
    <el-table-column prop="leaderId" label="队长id" />
    <el-table-column prop="leaderUsername" label="队长username" />
    <el-table-column prop="leaderRealName" label="队长realname" />
    <el-table-column prop="committed" label="提交状态" />
    <el-table-column prop="teacherId" label="辅导老师id" />
    <el-table-column prop="teacherName" label="辅导老师name" />
    <el-table-column prop="projectPath" label="项目地址" />
  </el-table>
</template>

<script lang="ts" setup>
import axios from "axios";
import { ElMessage } from "element-plus";
import router from "@/router";
import { reactive, ref } from "vue";

let tableData = reactive([
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
  while (tableData.length>0){
    tableData.pop();
  }
  for (let i = 0; i < data.length; i++) {
    let memberList = '';
    for (let j = 0; j < data[i].memberList.length; j++) {
      memberList += data[i].memberList[j][0];
      memberList += "  "
      memberList += data[i].memberList[j][1];
      memberList += "，"
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
  console.log("tableData");
  console.log(tableData.length);
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

let otherVars = reactive({
  showTableOfSelect: false,
});

const changeStatusOfTableOfSelect = () => {
  otherVars.showTableOfSelect = !otherVars.showTableOfSelect;
}

const selectTeam = reactive({
  id: null,
  name: null,
  projectName: null,
  leaderId: null,
  leaderUsername: null,
  leaderRealName: null,
  committed: null,
  teacherId: null,
  teacherName: null

})

const onSubmit = () => {
  console.log('submit!')
}

created();

const selectQuery = () => {
  console.log(selectTeam);
  axios.post("/api/admin/getTeamsQuery", selectTeam, {withCredentials: true}).then((response) => {
    if (response.data.code === 200) {
      ElMessage.success("查询成功");
      writeDataIntoTable(response.data.responseBody);
    } else {
      ElMessage.error("出错了");
    }
  })
}


</script>
