<template>

</template>

<script lang="ts" setup>
import {reactive, ref} from 'vue'
import type {FormInstance} from 'element-plus'
import axios from "axios";
import {ElMessage} from "element-plus";




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




</script>
