<template>
  <div>
    <div class="demclass">
      <el-upload
          class="upload-demo"
          ref="upload"
          name="files"
          accept=".png,.jpg,.zip,.rar"
          :on-remove="handleRemove"
          :headers="headers"
          action=""
          :multiple="true"
          :before-remove="beforeRemove"
          :on-change="onChangeFile"
          :show-file-list="true"
          :auto-upload="false">
        <el-button slot="trigger" type="primary">选取文件</el-button>
        <el-button style="margin-left: 10px;" type="success" @click="submitUpload">上传到服务器</el-button>
        <el-button type="danger" @click="clearAll">清除全部</el-button>
        <p>只能上传.zip/.rar/.jpg/.png 文件 且总文件大小不超过5G </p>

      </el-upload>

    </div>

  </div>
</template>

<script>
export default {
  name: "FileUploadView",
  data() {
    return {
      fileList: [],
      tableFile: [],
      headers: {
        'Authorization': window.sessionStorage.getItem('token'),
        'Content-Type': 'multipart/form-data'
      }
    };
  },
  methods: {
    submitUpload() {
      let data = new FormData();
      if(this.tableFile.length <=0 ){
        console.log("tableFile 小于0")
        return ;
      }
      for (let i = 0; i < this.tableFile.length; i++) {
        data.append("files" , this.tableFile[i]);

      }
      this.$http.post('/fileupload/fileUpload' , data ,{
        headers: {
          'Authorization': window.sessionStorage.getItem('token'),
          'Content-Type': 'multipart/form-data'
        }
      })
          .then(res =>{
            console.log("上传成功！");
          }).error(res => {
        console.log("上传失败");
      });
    },
    handleRemove(file, fileList) {
      this.upFileList = []
      for (let x of fileList) {
        if (x.raw) {
          this.upFileList.push(x.raw)
        }
      }
    },
    beforeRemove(file, fileList) {
      return this.$msgbox.alert(`确定移除 ${file.name}？`)
    },
    onChangeFile(file , fileList){
      for (let x of fileList) {
        if(x.raw) {
          this.tableFile.push(x.raw)
        }
      }
    },
    onchange(file, fileList) {
      this.checkAllFile()
    },
    clearAll() {
      const h = this.$createElement;
      this.$msgbox({
        title: '消息',
        message: h('p', null, [
          h('i', {style: 'color: red'}, '确认清除全部吗？')
        ]),
        showCancelButton: true,
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        beforeClose: (action, instance, done) => {
          if (action === 'confirm') {
            instance.confirmButtonLoading = true;
            instance.confirmButtonText = '清除中';
            this.$refs.upload.clearFiles();
            done();
            instance.confirmButtonLoading = false;
          } else {
            done();
          }
        }
      }).then(action => {
        this.$message({
          type: 'info',
          message: '清除完成 '
        });
      }).catch(action => {

      })

    },
    checkAllFile() {
      this.tableFile = this.$refs.upload.uploadFiles;
    }
  },
}
</script>

<style scoped>

.demclass {
  background-color: #0fffc5;
}

</style>
