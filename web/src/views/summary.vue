<template>
  <el-table :data="summaryList" stripe style="width: 100%">
    <el-table-column prop="id" label="id" width="180" />
    <el-table-column prop="teamId" label="teamId" width="180" />
    <el-table-column prop="teamName" label="teamName" width="180" />
    <el-table-column prop="score" label="score" width="180" />
    <el-table-column prop="lv" label="lv" width="180" />
  </el-table>
</template>

<script lang="ts">
import { Vue, Options } from "vue-class-component";
import { SummaryVO } from "@/interfaces/summary"
import axios from "axios";
import { ElMessage } from "element-plus";

@Options({
  created() {
    this.getData();
  }
})
export default class summary extends Vue {
  summaryList: SummaryVO[] = [];

  getData() {
    axios.get("/api/summary/getAll").then((res) => {
      if (res.data.code == 200) {
        ElMessage.success("成功");
        for (let i = 0; i < res.data.responseBody.length; i++) {
          let lv = "";
          if (res.data.responseBody[i].score >= 95) {
            lv = "一等";
          } else if (85 <= res.data.responseBody[i].score && res.data.responseBody[i].score < 95) {
            lv = "二等";
          } else if (70 <= res.data.responseBody[i].score && res.data.responseBody[i].score < 85) {
            lv = "三等";
          } else {
            lv = "没等";
          }
          this.summaryList.push({
            id: res.data.responseBody[i].id,
            teamId: res.data.responseBody[i].teamId,
            teamName: res.data.responseBody[i].teamName,
            score: res.data.responseBody[i].score,
            lv: lv,
          })
        }
      } else {
        ElMessage.error("暂无数据或获取失败");
      }
    })
  }
}
</script>

<style scoped>

</style>
