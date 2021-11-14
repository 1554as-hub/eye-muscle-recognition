<template>

  <div class="demo-type">
    <div>
      <table class="table-fontsize">
        <tr>
          <td>
            <el-avatar :size="30" icon="el-icon-user-solid" ></el-avatar>
          </td>
          <td>
            <div>当前用户：{{user.realname}}</div>
          </td>
          <td><img src="../../assets/imgs/exit.png" style="height: 30px; width: 30px;" alt="not found"></td>
          <td>
            <el-button type="info" @click="logout">退出登入</el-button>
          </td>
        </tr>

      </table>

    </div>

  </div>
</template>

<script>
export default {
  created() {
    this.getUserInfo();
  },
  name: "HeadPortrait",
  methods: {
    logout() {
      window.sessionStorage.clear();
      this.$router.push("/login");
      this.$message.success("退出登入成功！");
    },
    async getUserInfo() {
      this.$http.get("/auth/userInfo" , {
        headers: {
          'Authorization': window.sessionStorage.getItem('token')
        }
      }).then(res => {
        console.log(res.data)
        if(res.status === 200){
          this.user = res.data;
          console.log(this.user);
        }else {
          this.$message.error("数据出现问题请进行检查");
          window.sessionStorage.clear();
          this.$router.push("/login");
        }

      }).catch(exce => {
          this.$message.error("用户信息已过期请重新进行登入");
          window.sessionStorage.clear();
          this.$router.push("/login")
      })
    }
  },
  data() {
    return {
      user: {
        username: String ,
        realname: String ,
        phone: String,
        avatar: String,
        birthday: String,
        id: String,
        sex: String,
        createTime: String
      }
    }
  }
}
</script>

<style scoped>
.demo-type {
  background: gray;
  padding-top: 0px;
  text-align: center;
  height: 60px;
}

.table-fontsize {
  padding: 14px;
  font-size: 13px;
  height: 20px;

}
</style>
