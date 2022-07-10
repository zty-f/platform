<template>
  <el-button type="primary" @click="changeStatusOfTableOfSelect">打开/关闭 查询单元</el-button>
  <el-button type="primary" @click="distributeJudge">分配评委</el-button>
  <el-form
    :label-position="left"
    label-width="150px"
    :model="selectTeam"
    style="max-width: 460px"
    v-show="otherVars.showTableOfSelect===true"
    ref="formRef"
  >
    <el-form-item label="team id" prop="id">
      <el-input v-model="selectTeam.id" placeholder="team id" />
    </el-form-item>
    <el-form-item label="team name" prop="name">
      <el-input v-model="selectTeam.name" placeholder="team name" />
    </el-form-item>
    <el-form-item label="project name" prop="projectName">
      <el-input v-model="selectTeam.projectName" placeholder="projectName" />
    </el-form-item>
    <el-form-item label="leader id" prop="leaderId">
      <el-input v-model="selectTeam.leaderId" placeholder="leader id" />
    </el-form-item>
    <el-form-item label="leader username" prop="leaderUsername">
      <el-input v-model="selectTeam.leaderUsername" placeholder="leader username" />
    </el-form-item>
    <el-form-item label="leader realname" prop="realName">
      <el-input v-model="selectTeam.realName" placeholder="leader realname" />
    </el-form-item>
    <el-form-item label="committed" prop="committed">
      <el-input v-model="selectTeam.committed" placeholder="committed" />
    </el-form-item>
    <el-form-item label="teacher id" prop="teacherId">
      <el-input v-model="selectTeam.teacherId" placeholder="teacher id" />
    </el-form-item>
    <el-form-item label="teacher realName" prop="teacherName">
      <el-input v-model="selectTeam.teacherName" placeholder="teacher realName" />
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="selectQuery">查询队伍信息</el-button>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="resetForm(formRef)">重置</el-button>
    </el-form-item>
  </el-form>

  <el-table :data="tableData" style="width: 100%">
    <el-table-column prop="id" label="队伍id" width="100" />
    <el-table-column prop="name" label="队伍name" width="100" />
    <el-table-column prop="memberList" label="成员" width="180" />
    <el-table-column prop="projectName" label="所选项目" width="180" />
    <el-table-column prop="leaderId" label="队长id" />
    <el-table-column prop="leaderUsername" label="队长username" width="180" />
    <el-table-column prop="leaderRealName" label="队长realname" width="180" />
    <el-table-column prop="committed" label="提交状态" />
    <el-table-column prop="teacherId" label="辅导老师id" />
    <el-table-column prop="teacherName" label="辅导老师name" />
    <el-table-column prop="projectPath" label="项目地址" />
    <el-table-column fixed="right" label="Operations" width="">
      <template #default="scope">
        <el-button v-show="scope.row.committed===true" type="primary" @click.preven="downFile(scope.$index)">下载文件
        </el-button>
      </template>
    </el-table-column>
  </el-table>
</template>

<script lang="ts" setup>
import axios from "axios";
import { ElMessage, FormInstance } from "element-plus";
import router from "@/router";
import { reactive, ref } from "vue";

const formRef = ref<FormInstance>()

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

const writeDataIntoTable = (data: any) => {
  while (tableData.length > 0) {
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
      if (response.data.responseBody.length > 0) {
        ElMessage.success("成功")
        writeDataIntoTable(response.data.responseBody);
      } else {
        ElMessage.error("暂无数据")
      }
    } else {
      ElMessage.error("出错了");
    }
  })
}

const distributeJudge = () => {
  axios.get("/api/admin/distribute").then((res) => {
    if (res.data.code == 200) {
      ElMessage.success("分配成功");
    } else {
      ElMessage.error("分配失败");
    }
  })
}

const resetForm = (formEl: FormInstance | undefined) => {
  if (!formEl) return
  formEl.resetFields()
}

const downFile = (index: number) => {
  window.location.href = "http://localhost:8500/platform/" + tableData[index].projectPath;
}

</script>
