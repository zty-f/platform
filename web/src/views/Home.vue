<template>
  {{name}}
</template>

<script>
import axios from "axios";
import {ElMessage} from "element-plus";

export default {
  name: "home",
  data() {
    return{
      name:"index",
    }
  },
  created() {
    axios.get("/getCurrentUser",{withCredentials:true}).then((response) => {
      const data = response.data;
      console.log(data);
      if (data.code === 200) {
        ElMessage.success("您当前的身份为"+data.responseBody[0]+"，用户名"+data.responseBody[1]);
      } else {
        ElMessage.error(data.message);
      }
    });
  }
}
</script>

<style scoped>

</style>
