<template>
  <div class="student-dashboard">
    <!-- 左侧导航栏 -->
<!--    添加了一行注释用于测试git提交-->
    <div class="sidebar">
      <el-menu
          :default-active="activeMenu"
          class="menu"
          @select="handleMenuSelect"
          background-color="#ffffff"
          text-color="#333"
          active-text-color="#20a0ff"
          mode="vertical"
          unique-opened>
        <el-menu-item index="1">我的课程</el-menu-item>
        <el-menu-item index="2">课程报名</el-menu-item>
        <el-menu-item index="3">成绩查询</el-menu-item>
        <el-menu-item index="4">我的作业</el-menu-item>
        <el-menu-item index="5">课程通知</el-menu-item>
        <el-menu-item index="6">个人主页</el-menu-item>
      </el-menu>
    </div>
    <!-- 右侧内容区域 -->
    <div class="content">
      <router-view></router-view>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      activeMenu: '1', // 默认选中的菜单项
    };
  },
  methods: {
    handleMenuSelect(index) {
      switch (index){
        case '1':
          this.$router.push('/studentContainer/myClass');
          break;
        case '2':
          this.$router.push('/studentContainer/allClass');
          break;
        case '3':
          this.$router.push('/studentContainer/myGrade');
          break;
        case '4':
          this.$router.push('/studentContainer/myAssign');
          break;
        case '5':
          this.$router.push('/studentContainer/myNotify');
          break;
        case '6':
          this.$router.push('/studentContainer/myInform');
      }
    }
  },
  mounted() {
    //弹出“同学，欢迎你”的消息提示
    this.$message({
      message: '同学，欢迎你',
      type: 'success'
    });
  }
};
</script>

<style scoped>
.student-dashboard {
  display: flex;
  height: 100vh; /* 保证页面占满整个高度 */
}

.sidebar {
  width: 20%; /* 左侧导航栏占屏幕宽度的20% */
  background-color: #f4f4f4;
  padding: 10px;
  border-right: 1px solid #ddd;
  position: fixed; /* 左侧固定 */
  top: 0;
  bottom: 0;
  left: 0;
}

.content {
  width: 80%;
  border: red solid;
  position: fixed;
  top: 0;
  bottom: 0;
  right: 0;
}

.el-menu-item {
  padding: 10px;
}
</style>
