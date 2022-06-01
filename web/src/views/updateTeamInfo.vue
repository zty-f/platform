<template>
  <div>
    <el-table v-bind:data="tableData.list" style="width: 100%" @row-dblclick="ToUpdateTeamInfo">
      <el-table-column prop="id" label="id" width="150"/>
      <el-table-column prop="name" label="name" width="120"/>
      <el-table-column prop="isLeader" label="isLeader" width="120"/>
      <el-table-column prop="memberListStr" label="memberList" width="120"/>
      <el-table-column prop="projectName" label="projectName" width="120"/>
      <el-table-column prop="leaderRealName" label="leaderRealName" width="120"/>
      <el-table-column prop="committed" label="committed" width="120"/>
      <el-table-column prop="teacherId" label="teacherId" width="120"/>
      <el-table-column prop="teacherName" label="teacherName" width="120"/>
      <el-table-column prop="projectPath" label="projectPath" width="120"/>
    </el-table>

    <div>
      <span>将学生</span>
      <el-select v-model="idDelete" placeholder="Select">
        <el-option
          v-for="item in optionsDelete"
          :key="item.value"
          :label="item.label"
          :value="item.value"
        />
      </el-select>
      <span>从队伍中删了。</span>
      <el-button type="danger" @click="deleteStudentFromTeam()">删除学生</el-button>
    </div>

    <div>
      <span>将学生</span>
      <el-input
        v-model="studentUsernameAdd"
        class="w-50 m-2"
        placeholder="请输入学生username（非realName）"
        :prefix-icon="Search"
        @blur="selectStudentByUsername"
      />
      <span>加到队伍中</span>
      <el-button type="primary" @click="selectStudentByUsername">查找学生</el-button>
      <el-button type="primary" @click="addStudentToTeam">增加学生</el-button>
    </div>

    <div>
      <span>将老师改为为</span>
      <el-input
        v-model="teacherUsernameUpdate"
        class="w-50 m-2"
        placeholder="请输入老师username（非realName）"
        :prefix-icon="Search"
        @blur="selectTeacherByUsername"
      />
      <el-button type="primary" @click="selectTeacherByUsername">查找老师</el-button>
      <el-button type="primary" @click="updateTeacherOfTeam">更改老师</el-button>
    </div>
    <el-button @click="test">test</el-button>
  </div>
</template>

<script lang="ts" setup>
import axios from "axios";
import {ElMessage} from "element-plus";
import {ref} from 'vue'
import {reactive} from "vue";
import {useRouter} from 'vue-router'

const router = useRouter()
const idDelete = ref('')

const tableData = reactive<{
  list: Item[]
}>({
  list: []
})

let optionsDelete = reactive<{}>([])
let studentUsernameAdd = ref('')
let student = reactive({
  id: '',
  info: ''
})

let teacherUsernameUpdate = ref('')
let teacher = reactive({
  id: '',
  info: ''
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
  memberList: Array<number>
  memberListStr: string
  projectName: string
  leaderRealName: string
  committed: string
  teacherId: string
  teacherName: string
  projectPath: string
}

const flushAll = () => {
  tableData.list=[]
  optionsDelete=[]
  studentUsernameAdd.value=''
  student.id=''
  student.info=''
  teacherUsernameUpdate.value=''
  teacher.id=''
  teacher.info=''
  user.identification=''
  user.username=''
  user.id=''
}

const writeDataIntoTable = (data: string | any[]) => {
  for (let i = 0; i < data.length; i++) {
    let memberListStr = '';

    for (let j = 0; j < data[i].memberList.length; j++) {
      memberListStr += data[i].memberList[j][0];
      memberListStr += '  ';
      memberListStr += data[i].memberList[j][1];
      memberListStr += '，';
    }
    tableData.list.push({
      id: data[i].id,
      name: data[i].name,
      isLeader: data[i].isLeader,
      memberList: data[i].memberList,
      memberListStr: memberListStr,
      projectName: data[i].projectName,
      leaderRealName: data[i].leaderRealName,
      committed: data[i].committed,
      teacherId: data[i].teacherId,
      teacherName: data[i].teacherName,
      projectPath: data[i].projectPath
    })
  }
  console.log(tableData);
}


const writeOptions = (memberList: any[]) => {
  for (let i = 0; i < memberList.length; i++) {
    if (memberList[i][0] !== user.id) {
      optionsDelete.push({
        value: memberList[i][0],
        label: memberList[i][1],
      })
      console.log(memberList[i][0]);
      console.log(memberList[i][1]);
    }
  }
  console.log("optionsDelete");
  console.log(optionsDelete);
}

const created = () => {
  //flushAll()
  axios.get("/getCurrentUser", {withCredentials: true}).then((response) => {
    const data = response.data;
    console.log(data);
    if (data.code === 200) {
      ElMessage.success("您当前的身份为" + data.responseBody[0] + "，用户名" + data.responseBody[1]);
      user.identification = data.responseBody[0];
      user.username = data.responseBody[1];
      user.id = data.responseBody[2];
      axios.get("/api/student/teamInfoById/" + 12, {withCredentials: true}).then((response) => {
        const data = response.data;

        if (data.code === 200) {
          console.log(data);
          writeDataIntoTable(data.responseBody);
          console.log("data.list[0].memberList");
          console.log(tableData.list[0].memberList);
          writeOptions(tableData.list[0].memberList);
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

const deleteStudentFromTeam = () => {
  axios.get("/api/team/deleteStudent/" + tableData.list[0].id + "/" + idDelete.value, {withCredentials: true}).then(response => {
    const data = response.data;
    if (data.code === 200) {
      ElMessage.success("删除成功");
      optionsDelete = []
      tableData.list = []
      created();
    } else {
      ElMessage.error("删除失败")
    }
  })
}

const addStudentToTeam = () => {
  selectStudentByUsername()
  axios.get("/api/team/addStudent/" + tableData.list[0].id + "/" + student.id, {withCredentials: true}).then(response => {
    const data = response.data;
    if (data.code === 200) {
      ElMessage.success("新增成功");
      optionsDelete = []
      tableData.list = []
      studentUsernameAdd.value = ''
      created();
    } else {
      ElMessage.error("新增失败")
    }
  })
}

const selectStudentByUsername = () => {
  if (studentUsernameAdd.value !== '') {
    axios.get("/api/student/getByUsername/" + studentUsernameAdd.value, {withCredentials: true}).then((response) => {
      const data = response.data;
      if (data.code === 200) {
        let info = data.responseBody.realName + " " + data.responseBody.schoolName;
        ElMessage.success(info);
        student.info = info;
        student.id = data.responseBody.id;
      } else {
        ElMessage.error(data.message);
        studentUsernameAdd.value = ''
        student.info = '';
        student.id = '';
      }
    });
  } else {
    student.info = '';
    student.id = '';
  }
  return
}

const selectTeacherByUsername = () => {
  if (teacherUsernameUpdate.value !== '') {
    axios.get("/api/teacher/getByUsername/" + teacherUsernameUpdate.value, {withCredentials: true}).then((response) => {
      const data = response.data;
      if (data.code === 200) {
        let info = data.responseBody.realName + " " + data.responseBody.schoolName;
        ElMessage.success(info);
        teacher.info = info;
        teacher.id = data.responseBody.id;
      } else {
        ElMessage.error(data.message);
        teacherUsernameUpdate.value = ''
        teacher.info = '';
        teacher.id = '';
      }
    });
  } else {
    teacher.info = '';
    teacher.id = '';
  }
  return
}

const updateTeacherOfTeam = () => {
  selectTeacherByUsername()
  if (teacherUsernameUpdate.value !== '') {
    axios.get("/api/team/updateTeacher/" + tableData.list[0].id + "/" + teacher.id + "/" + tableData.list[0].teacherId, {withCredentials: true}).then((response) => {
      const data = response.data;
      if (data.code === 200) {
        ElMessage.success("更新成功");
        tableData.list = []
        created();
      } else {
        ElMessage.error("更新失败");
      }
    });
  }
  teacherUsernameUpdate.value = ''
  teacher.info = '';
  teacher.id = '';
  return
}

const test = () => {
  console.log(idDelete.value);
}
created();

</script>
