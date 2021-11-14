<template>
  <div>
    <el-row>
      <el-col>
        <el-menu
            default-active="2"
            @open="handleOpen"
            @close="handleClose"
            background-color="#545c64"
            text-color="#fff"
            active-text-color="#409bff" :unique-opened="true"
            :collapse="isCollapse"
            :router="true">
          <el-submenu index="1">
            <template slot="title">
              <i class="el-icon-location"></i>
              <span>眼肌识别</span>
            </template>
            <el-menu-item index="/fileupload"><i class="el-icon-menu"></i> 文件上传</el-menu-item>
            <el-menu-item index="/singleshow"><i class="el-icon-menu"></i> 单张识别</el-menu-item>
            <el-menu-item index="/historyshow"><i class="el-icon-menu"></i>结果展示</el-menu-item>
          </el-submenu>
          <el-submenu index="2">
            <template slot="title">
              <i class="el-icon-location"></i>
              <span>眼肌数据管理</span>
            </template>
          </el-submenu>
        </el-menu>
      </el-col>
    </el-row>
  </div>
</template>

<script>

export default {
  name: "SideNavigationBar",
  data() {
    return {
      isCollapse: false
    }
  },
  methods: {
    handleOpen(key, keyPath) {
    },
    handleClose(key, keyPath) {
    },

  }

}
</script>

<style scoped>

</style>
