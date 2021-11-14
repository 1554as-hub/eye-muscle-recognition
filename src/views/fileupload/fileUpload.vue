<template>
  <el-upload
    class="upload-demo"
    ref="upload"
    name="files"
    accept=".png,.jpg,.zip,.rar"
    action="#"
    :on-preview="handlePreview"
    :on-remove="handleRemove"
    :multiple="true"
    :headers="heards"
    :file-list="fileList"
    :show-file-list="true"
    :auto-upload="false">
    <el-button slot="trigger"  type="primary" >选取文件</el-button>
    <el-button style="margin-left: 10px;"  type="success" @click="submitUpload">上传到服务器</el-button>
    <el-button type="danger" @click="clearAll">清除全部</el-button>
    <p>只能上传.zip/.rar/.jpg/.png 文件 且总文件大小不超过5G </p>

  </el-upload>

</template>

<script>

export default {
  name: "fileUpload",
  data() {
    return {
      fileList: [] ,
      tableFile: [] ,
      heards: {
        'Authorization' : 'bearer  26dd64bf-2a5d-44c9-ac69-23766a810ad9' ,
        'Content-Type': 'multipart/form-data',
        'Access-Control-Allow-Origin': '*',
        'Access-Control-Allow-Credentials': 'true'
     }
    };
  },
  methods: {
    submitUpload() {

      // this.$refs.upload.submit();

      let data = new FormData();
      if(this.fileList.length <=0 ){
        console.log("fileList 小于0")
        return ;
      }
      this.fileList.forEach(i => {
        data.append("files" ,i );
      });
      this.$http.post('http://localhost:7171/fileupload/fileUpload' , data ,{
        'Authorization' : window.sessionStorage.getItem('token'),
        'Content-Type': 'multipart/form-data'
      })
      .then(res =>{
        console.log("上传成功！");
      }).error(res => {
        console.log("上传失败");
      });
    },
    handleRemove(file, fileList) {

    },
    handlePreview(file) {
      console.log(file);

      file.date = new Date().toLocaleString();
      this.tableFile.push(file)
    },
    onchange(file, fileList) {
      this.checkAllFile()
    },
    open() {
      const h = this.$createElement;
      this.$msgbox({
        title: '消息',
        message: h('p', null, [
          h('span', null, '内容可以是 '),
          h('i', { style: 'color: teal' }, 'VNode')
        ]),
        showCancelButton: true,
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        beforeClose: (action, instance, done) => {
          if (action === 'confirm') {
            instance.confirmButtonLoading = true;
            instance.confirmButtonText = '执行中...';
            setTimeout(() => {
              done();
              setTimeout(() => {
                instance.confirmButtonLoading = false;
              }, 300);
            }, 3000);
          } else {
            done();
          }
        }
      }).then(action => {
        this.$message({
          type: 'info',
          message: 'action: ' + action
        });
      });
    } ,
    clearAll() {  const h = this.$createElement;
      this.$msgbox({
        title: '消息',
        message: h('p', null, [
          h('i', { style: 'color: teal' }, '确认清除全部吗？')
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

      }).catch(action => {

      })

    },
    checkAllFile() {
      this.tableFile = this.$refs.upload.uploadFiles;
    }
  },
  computed: {

  }
}
</script>

<style scoped>
  .el-upload{
    margin-left: 30%;
    margin-right: 30%;
  }
</style>
