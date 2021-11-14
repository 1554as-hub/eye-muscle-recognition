<template>
  <div>
    <el-container>
      <el-aside  width="450px" class="single"><single-upload-img></single-upload-img></el-aside>
      <el-main class="elmain"><result-img></result-img></el-main>
    </el-container>
  </div>
</template>

<script>
import SingleUploadImg from "./SingleUploadImg";
import ResultImg from "./ResultImg";
export default {
  name: "SingleShowView",
  components: { ResultImg, SingleUploadImg}
}
</script>

<style scoped>

.head-main{
  padding: 0px;
}


.single {

  height: 650px;
}
.elmain{
  padding: 0px;
}

</style>
