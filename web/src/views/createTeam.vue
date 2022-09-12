<template>
  <el-form
    ref="formRef"
    :model="dynamicValidateForm"
    label-width="120px"
    class="demo-dynamic"
  >
    <el-form-item
      prop="name"
      label="队伍name"
      :rules="[
        {
          required: true,
          message: '请输入队伍name',
          trigger: 'blur',
        }
      ]"
    >
      <el-input v-model="dynamicValidateForm.name"/>
    </el-form-item>

    <el-form-item
      v-for="(domain, index) in dynamicValidateForm.domains"
      :key="domain.key"
      :label="'成员' + (index+1)+'的username'"
      :prop="'domains.' + index + '.value'"
      :rules="{
        required: true,
        message: '成员username不为null',
        trigger: 'blur',
      }"
    >
      <el-input v-model="domain.value" @blur="selectStudentByUsername(domain)"/>
      <el-button class="mt-2" @click.prevent="removeDomain(domain)">Delete</el-button>
      {{ domain.info }}
    </el-form-item>

    <el-form-item
      prop="projectId"
      label="项目id"
      :rules="[
        {
          required: true,
          message: '请输入项目id',
          trigger: 'blur',
        }
      ]"
    >
      <el-input v-model="dynamicValidateForm.projectId" @blur="selectProjectTitleById(dynamicValidateForm.projectId)"/>
      {{ dynamicValidateForm.projectInfo }}
    </el-form-item>


    <el-form-item
      prop="teacherUsername"
      label="老师username"
      :rules="[
        {
          required: true,
          message: '请输入老师老师username',
          trigger: 'blur',
        }
      ]"
    >
      <el-input v-model="dynamicValidateForm.teacherUsername"
                @blur="selectTeacherByUsername(dynamicValidateForm.teacherUsername)"/>
      {{ dynamicValidateForm.teacherInfo }}
    </el-form-item>

    <el-form-item>
      <el-button type="primary" @click="submitForm(formRef)">创建队伍</el-button>
      <el-button @click="addDomain">新增队员</el-button>
      <el-button @click="resetForm(formRef)">Reset</el-button>
      <el-button @click="test()">test</el-button>
    </el-form-item>
  </el-form>
</template>

<script lang="ts" setup>
import {reactive, ref} from 'vue'
import type {FormInstance} from 'element-plus'
import axios from "axios";
import {ElMessage} from "element-plus";
// name: '',
//   memberList: '',
//   projectId: '',
//   leaderId: '',
//   teacherId: '',
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
      ElMessage.success("您当前的身份为" + data.responseBody[0] + "，用户名" + data.responseBody[1] + "，id" + data.responseBody[2]);
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
const formRef = ref<FormInstance>()
const dynamicValidateForm = reactive<{
  domains: DomainItem[]
  name: string,
  projectId: string,
  projectInfo: string,
  leaderId: string,
  teacherId: string,
  teacherUsername: string,
  teacherInfo: string,
}>({
  domains: [
    {
      key: 1,
      value: '',
      info: '',
      id: '',
    },
  ],
  name: '',
  projectId: '',
  projectInfo: '',
  leaderId: '',
  teacherId: '',
  teacherUsername: '',
  teacherInfo: '',
})
const teamInfoToCommit = reactive<{
  name: string,
  memberList: string[],
  projectId: string,
  leaderId: string,
  teacherId: string,
}>({
  name: '',
  memberList: [],
  projectId: '',
  leaderId: '',
  teacherId: '',
})

interface DomainItem {
  key: number
  value: string
  info: string
  id: string;
}

const removeDomain = (item: DomainItem) => {
  const index = dynamicValidateForm.domains.indexOf(item)
  if (index !== -1) {
    dynamicValidateForm.domains.splice(index, 1)
  }
}
const addDomain = () => {
  dynamicValidateForm.domains.push({
    key: Date.now(),
    value: '',
    info: '',
    id: ''
  })
}
const test = () => {
  teamInfoToCommit.name = dynamicValidateForm.name;
  console.log("length:" + dynamicValidateForm.domains.length);
  teamInfoToCommit.memberList = [];
  for (let i = 0; i < dynamicValidateForm.domains.length; i++) {
    if (dynamicValidateForm.domains[i].id !== '') {
      teamInfoToCommit.memberList.push(dynamicValidateForm.domains[i].id);
    }
  }
  teamInfoToCommit.projectId = dynamicValidateForm.projectId;
  teamInfoToCommit.leaderId = dynamicValidateForm.leaderId;
  teamInfoToCommit.teacherId = dynamicValidateForm.teacherId;
  console.log(teamInfoToCommit);
}
const submitForm = (formEl: FormInstance | undefined) => {
  teamInfoToCommit.name = dynamicValidateForm.name;
  console.log("length:" + dynamicValidateForm.domains.length);
  teamInfoToCommit.memberList = [];
  for (let i = 0; i < dynamicValidateForm.domains.length; i++) {
    if (dynamicValidateForm.domains[i].id !== '') {
      teamInfoToCommit.memberList.push(dynamicValidateForm.domains[i].id);
    }
  }
  teamInfoToCommit.projectId = dynamicValidateForm.projectId;
  teamInfoToCommit.leaderId = dynamicValidateForm.leaderId;
  teamInfoToCommit.teacherId = dynamicValidateForm.teacherId;
  axios.post("/api/team/add", teamInfoToCommit, {withCredentials: true}).then((response) => {
    const data = response.data;
    if (data.code === 200) {
      ElMessage.success(data.message);
    } else {
      ElMessage.error(data.message);
    }
  });
}
const resetForm = (formEl: FormInstance | undefined) => {
  if (!formEl) return
  formEl.resetFields()
}
const selectStudentByUsername = (item: DomainItem) => {
  if (item.value !== '') {
    axios.get("/api/student/getByUsername/" + item.value, {withCredentials: true}).then((response) => {
      const data = response.data;
      if (data.code === 200) {
        let info = data.responseBody.realName + " " + data.responseBody.schoolName;
        ElMessage.success(info);
        item.info = info;
        item.id = data.responseBody.id;
      } else {
        ElMessage.error(data.message);
        // removeDomain(item);
        item.id = '';
        item.info = '';
        item.value = '';
      }
    });
  }
  return
}
const selectTeacherByUsername = (username: string) => {
  if (dynamicValidateForm.teacherUsername !== '') {
    axios.get("/api/teacher/getByUsername/" + username, {withCredentials: true}).then((response) => {
      const data = response.data;
      if (data.code === 200) {
        let info = data.responseBody.realName + " " + data.responseBody.schoolName;
        dynamicValidateForm.teacherId = data.responseBody.id;
        dynamicValidateForm.teacherInfo = info;
      } else {
        ElMessage.error(data.message);
        dynamicValidateForm.teacherUsername = '';
        dynamicValidateForm.teacherInfo = '';
      }
    });
  } else {
    dynamicValidateForm.teacherInfo = '';
  }
  return
}

const selectProjectTitleById = (id: string) => {
  if (dynamicValidateForm.projectId !== '') {
    axios.get("/api/project/selectTitleById/" + id).then((res) => {
      if (res.data.code === 200) {
        dynamicValidateForm.projectInfo = res.data.responseBody;
      } else {
        dynamicValidateForm.projectInfo = "";
        dynamicValidateForm.projectId = "";
        ElMessage.error(res.data.message);
      }
    })
  }
}

</script>
