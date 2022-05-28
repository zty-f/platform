<template>

</template>

<script>
import axios from "_axios@0.27.2@axios";
import {ElMessage} from "_element-plus@2.2.2@element-plus";

export default {
  name: "teamInfo",
  created() {
    axios.post("/api/teacher/teamsInfo").then((response) => {
      const data = response.data;
      if (data.code === 200) {
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
