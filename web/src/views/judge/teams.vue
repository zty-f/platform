<template>
  <el-table :data="teamList" stripe style="width: 100%">
    <el-table-column prop="teamId" label="队伍id" width="180" />
    <el-table-column prop="judgeId" label="评委" width="180" />
    <el-table-column prop="judged" label="已打分？" width="180" />
    <el-table-column prop="projectPath" label="路径" width="180" />
    <el-table-column prop="score" label="分数" width="180" />
    <el-table-column fixed="right" label="Operations" width="400">
      <template #default="scope">
        <el-button v-show="true" type="primary" @click.preven="downFile(scope.$index)">
          下载文件
        </el-button>
        <el-input-number v-model="teamUpdateForm.score" max="100" min="0"
                         style="width: 150px;margin: 0px 20px"></el-input-number>
        <el-button v-show="true" type="primary" @click.preven="updateScore(scope.$index)">
          更新分数
        </el-button>
      </template>
    </el-table-column>


  </el-table>
</template>

<script lang="ts">
import { Vue, Options } from "vue-class-component";
import { Team, TeamUpdateForm } from "@/interfaces/judge"
import axios from "axios";
import { ElMessage } from "element-plus";


@Options({
  created() {
    this.getData();
  }
})

export default class teams extends Vue {
  teamList: Team[] = [];
  teamUpdateForm: TeamUpdateForm = {
    id: -1,
    score: -1,
  }

  getData() {
    axios.get("/api/judge/getTeams").then((res) => {
      if (res.data.code === 200) {
        ElMessage.success("查询成功");
        for (let i = 0; i < res.data.responseBody.length; i++) {
          this.teamList.push({
            id: res.data.responseBody[i].id,
            teamId: res.data.responseBody[i].teamId,
            judgeId: res.data.responseBody[i].judgeId,
            judged: res.data.responseBody[i].score != null,
            projectPath: res.data.responseBody[i].projectPath,
            score: res.data.responseBody[i].score,
          })
        }
      } else {
        ElMessage.error("暂无数据");
      }
    })
  }

  downFile(index: number) {
    window.location.href = "http://localhost:8500/platform/" + this.teamList[index].projectPath;
  }

  updateScore(index: number) {
    this.teamUpdateForm.id = this.teamList[index].id;
    axios.post("/api/judge/updateScore", this.teamUpdateForm).then((res) => {
      if (res.data.code == 200) {
        ElMessage.success("修改成功");
        this.teamList[index].score = this.teamUpdateForm.score;
      } else {
        ElMessage.error("修改失败");
      }
    })
  }
}
</script>

<style scoped>

</style>
