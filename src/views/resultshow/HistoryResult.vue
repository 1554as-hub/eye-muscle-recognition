<template>
  <div>
    <div v-if="isNull">
      <el-tag type="info" style="color: #288000">历史的上传结果记录</el-tag>
      <el-table :data="historyData" border stripe>
        <el-table-column type="index" :index="indexMethod"></el-table-column>
        <el-table-column prop="name" label="文件编号"></el-table-column>
        <el-table-column  label="上传日期">
          <template slot-scope="scope">
            <i class="el-icon-time"></i>
            <span style="margin-left: 10px">{{scope.row.date}}</span>
          </template>
        </el-table-column>
        <el-table-column prop="totalSize" label="文件大小"></el-table-column>
        <el-table-column  label="操作" >
          <template slot-scope="scope">
            <div v-if="scope.row.status === 1">
              <el-button type="primary" size="small" icon="el-icon-zoom-in"
                         @click="handlePreviewImage(scope.row.id , scope.row.date)"></el-button>
              <el-button type="primary" size="small" icon="el-icon-download"
              @click="handleDownload(scope.row.zipPath)" ></el-button>
            </div>
           <div v-else>
             <el-tag type="info">文件正在识别中</el-tag>
           </div>
          </template>
        </el-table-column>
      </el-table>
      <div class="block">
        <el-pagination
            @current-change="handleCurrentChange"
            layout="prev, pager, next"
            :total="totalPage * 10">
        </el-pagination>
      </div>
    </div>
    <div v-else>
      <el-tag type="info" style="color: red">用户还未有上传的记录</el-tag>
    </div>
    <el-dialog title="图片预览" :visible.sync="dialogTableVisible">
      <div>
        <el-image v-for="url in previewImageList" :key="url" :src="url" lazy>
          <div slot="error" class="image-slot">
            <i class="el-icon-picture-outline"></i>
          </div>
          <div slot="placeholder" class="image-slot">
            加载中<span class="dot">...</span>
          </div>
        </el-image>
      </div>
    </el-dialog>

  </div>
</template>

<script>


export default {
  name: "HistoryResult",
  created() {
    this.getUserHistory()
  },
  data() {
    return {
      historyData: [
        {
          name: String,
          date: String,
          filePath: String,
          id: String,
          status: Number,
          totalSize: Number,
          zipPath: String
        }
      ],
      pageNum: 1,
      totalPage: '',
      size: Number,
      dialogTableVisible: false,
      info: [],
      isNull: false ,
      previewImageList: Array
    }
  },
  methods: {
    async getUserHistory() {
      this.$http.get('/fileupload/gethistoryfile', {
            headers: {
              'Authorization': window.sessionStorage.getItem('token')
            }
          }
      ).then(res => {
        this.info = res.data;
        if (this.info.list.length > 0) {
          this.isNull = !this.isNull;
          this.historyData = this.info.list;
          this.totalPage = this.info.pages;
          console.log(this.totalPage)
        }
      }).catch(err => {
        this.$message({
          message: err
        })
      });
    },
    indexMethod(index) {
      return index + 1
    },
    handleCurrentChange(val) {
      this.pageNum = val
      this.$http.get('/fileupload/gethistoryfile?pageNum=' + this.pageNum , {
        headers: {
          'Authorization' : window.sessionStorage.getItem('token')
        }
      }).then(res => {
        this.info = res.data;
        if (this.info.list.length > 0) {
          this.historyData = this.info.list;
          this.totalPage = this.info.pages;
        }
      }).catch(err => {
        this.$message({
          message: err
        })
      });
    },
    handlePreviewImage(fileId , fileDate) {
      let date = new Date(fileDate);
      let month = date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1;
      let day = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
      let formatDate = date.getFullYear() + "-" + month + "-" + day;
      let url = "/common/resultPath?fileId=" + fileId + "&fileDate=" + formatDate
      this.$http.get(url).then(res => {
          this.previewImageList = res.data;
          console.log(this.previewImageList)
        this.dialogTableVisible = true
      }).catch(err => {
        this.$message({
          message: err
        })
      })
    },
    handleDownload(zipFileName) {
      if(zipFileName === null || zipFileName === '') {
        return;
      }
      this.$http.get("/common/pre/zip/" + zipFileName , {
        headers: {
          'Content-Type' : 'application/x-www-form-urlencoded'
        },
        responseType: 'blob'
      }).then(res => {
        let url = window.URL.createObjectURL(new Blob([res.data] , {
          type: 'application/zip'
        }))
        let link = document.createElement("a");
        link.style.display = "none";
        link.href = url;
        let timestamp = new Date().getTime();
        link.download = timestamp + ".zip";
        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link)
        window.URL.revokeObjectURL(url)
      }).catch(err => {
        this.$message({
          message: err
        })
      })
    }
  }
}
</script>

<style scoped>

</style>
