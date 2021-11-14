<template>
  <div class="login-container">
    <div class="login-main">
      <header>眼肌识别管理系统</header>
      <p>Loin-Eye Recognition management system</p>

      <el-form  class="login-form" :rules="rules" ref="LoginForm" status-icon :model="form"  label-width="80px">
        <el-form-item label="用户名" prop="username">
          <el-input prefix-icon="el-icon-user"  v-model="form.username" placeholder="用户名" clearable></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input prefix-icon="iconfont icon-password" type="password" v-model="form.password" placeholder="密码" clearable></el-input>
        </el-form-item>
        <el-form-item class="btns">
          <el-button type="primary" @click="submitForm('LoginForm')">提交</el-button>
          <el-button @click="resetForm('LoginForm')">重置</el-button>
        </el-form-item>
      </el-form>

    </div>
  </div>


</template>

<script>


export default {
  name: "LoginPage",
  components: {

  },
  data() {

    return {
      form: {
        username: 'saber',
        password: 'saber',
      } ,
      // 表单的验证规则对象
      rules:{
        // 验证用户名是否合法
        username: [{required: true , message: "请输入登入名称" , trigger: "blur"} ,
          {min: 1 , max: 10 , message: "长度在1到10之间" , trigger: "blur"}
        ],
        password: [{required: true , message: "请输入密码" , trigger: "blur"},
          {min: 1 , max: 15 , message: "长度在1到15之间" , trigger: "blur"}
        ]
      }
    }
  },
  mounted() {

  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate(async (valid) => {
        if (valid) {
          let formData = new FormData();
          formData.append("username" , this.form.username);
          formData.append("password" , this.form.password);
          formData.append("grant_type" , "password");
          formData.append("scope" , "webclient");

          let result = await this.$http.post("/auth/oauth/token" , formData ,{
            headers: {
              'Authorization': 'Basic ZWFnbGVleWU6dGhpc2lzc2VjcmV0',
            }
          }).catch(e => {
            this.$message.error("用户名或密码错误！");
          });
          if(result !== undefined) {
            if(result.status === 200) {
              this.$message.success("登入成功！")
              let token = "Bearer " + result.data.access_token ;
              window.sessionStorage.setItem("token" ,token);
              this.$router.push("/home");
            }
          }
        } else {

          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    }
  }
}
</script>

<style scoped>


.login-main {
  background-color: white;
  position: absolute;
  width: 500px;
  height: 450px;
  border-radius: 3px;
  padding: 0;
  left: 50%;
  top: 50%;
  transform: translate(-50% , -50%);
}

.login-main header {
  color: #33ccba;
  margin-top: 50px;
  height: 35px;
  line-height: 25px;
  font-size: 30px;
  font-weight: 100;
  text-align: center;
}
.login-main p {
  padding-bottom: 40px;
  color: #33ccba;

}
.login-form{
  position: absolute;
  width: 100%;
  padding: 0 10px;
  box-sizing: border-box;
}

.btns{
  display: flex;
  justify-content: right;
}


.login-container{
  height: 100%;
  background-color: #2b4b6b;

}
</style>
