<template>

  <el-container class="home-container">
    <!--  页面的头部部分  -->
    <el-header class="header">
      <div class="headerTitle">
        眼肌识别系统
      </div>
      <div style="float: right">
        <head-portrait></head-portrait>
      </div>
    </el-header>
    <el-container>
      <!--   页面的侧边栏   -->
      <el-aside class="aside" width="210px">

        <side-navigation-bar ></side-navigation-bar>
      </el-aside>
      <el-container>
        <!--  页面的主体部分  -->
        <el-main class="main">
          <router-view></router-view>
        </el-main>
      </el-container>
    </el-container>
  </el-container>


</template>

<script>

import SingleShowView from "../SingleShow/SingleShowView";
import ResultShowView from "../resultshow/ResultShowView";
import FileUploadView from "../fileupload/FileUploadView";
import HeadPortrait from "./HeadPortrait";
import SideNavigationBar from "./SideNavigationBar";

export default {
  name: "Content",
  components: {
    SideNavigationBar,
    HeadPortrait,
    FileUploadView,
    ResultShowView,
    SingleShowView,
  }
}
</script>

<style scoped>
.aside {

  background: #545c64;
}

.headerTitle {

  font-size: 30px;
  float: left;
  text-align: left;
  color: #37a0ce;
  padding-top: 12px;
}

.header {
  padding-right: 0px;
  background: #382f2f;
}

.main {
  padding: 0px;
}

.home-container {
  height: 100%;
}

.toggle-button {
  background-color: #4a5064;
  font-size: 10px;
  line-height: 24px;
  color: #fff;
  letter-spacing: 0.2em;
  cursor: pointer;
}
</style>
