<template>

</template>

<script>
import axios from "axios";
import {ElMessage} from "element-plus";

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
