<template>
  <el-upload
    ref="upload"
    class="upload-demo"
    action="http://localhost:8080/api/team/uploadFile"
    :limit="1"
    :on-exceed="handleExceed"
    :auto-upload="false"
    with-credentials="true"
    :data="params"
    accept="zip"
    :on-success="uploadSuccess"
  >
    <template #trigger>
      <el-button type="primary">select file</el-button>
    </template>
    <el-button class="ml-3" type="success" @click="submitUpload">
      upload to server
    </el-button>
    <template #tip>
      <div class="el-upload__tip text-red">
        limit 1 file, new file will cover the old file
      </div>
    </template>
  </el-upload>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import { ElMessage, genFileId } from 'element-plus'
import type { UploadInstance, UploadProps, UploadRawFile } from 'element-plus'
import router from "@/router";


const upload = ref<UploadInstance>()

const handleExceed: UploadProps['onExceed'] = (files) => {
  upload.value!.clearFiles()
  const file = files[0] as UploadRawFile
  file.uid = genFileId()
  upload.value!.handleStart(file)

}

const submitUpload = () => {
  upload.value!.submit();

}


let params: Record<string, string> = {
  "teamId": router.currentRoute.value.query.teamId as string,
}

const uploadSuccess = () => {
  ElMessage.success("上传成功");
  router.push({
    name: "teamInfo",
  })
}

</script>
