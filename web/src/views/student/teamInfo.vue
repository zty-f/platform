<template>
  <el-button type="primary" >get-student-teaminfo</el-button>
</template>

<script>
import axios from "axios";
import {ElMessage} from "element-plus";

export default {
  name: "teamInfo",
  created() {
    axios.post("/api/student/teamsInfo").then((response) => {
      const data = response.data;
      if (data.code === 200) {
        ElMessage.success(data.responseBody.toString());
        console.log(data);
      } else {
        ElMessage.error(data.message);
      }
    });
  }
}
</script>

<style scoped>

</style>
