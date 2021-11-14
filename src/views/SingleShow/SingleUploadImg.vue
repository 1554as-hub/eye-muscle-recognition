<template>
  <div>

      <div style="text-align: left" class="resule">
        <el-tag type="success">上传区域</el-tag>
      </div>

    <div>
      <el-upload
        class="upload-demo"
        action="#"
        name="files"
        :multiple="false"
        accept=".png,.jpg"
        :limit="1"
        :on-preview="handlePreview"
        :show-file-list="true"
        list-type="picture"
        :auto-upload="false"
        :file-list="fileList">
        <el-button slot="trigger"  type="primary" >上传识别图片</el-button>

      </el-upload>
    </div>
    <el-dialog
      title="图片预览"
      :visible.sync="previewVisible"
      width="60%"
      >
      <img :src="previewPath" alt="" class="previewClass">
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "SingleUploadImg",
  data() {
    return {
      fileList: [],
      dialogImageUrl: '',
      dialogVisible: true,
      disabled: false ,
      previewPath: '',
      previewVisible: false
    };
  },
  methods: {
    handlePreview(file) {
      console.log(file)
      this.previewPath = file.url;
      this.previewVisible = true;
    },
    handleRemove(file) {
      console.log(file)
    }

  }
}
</script>

<style scoped>

.previewClass{
  width: 100%;

}

</style>
