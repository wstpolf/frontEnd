<template>
  <div class="student-dashboard">
    <!-- 左侧导航栏 -->
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
        <el-menu-item index="1">我的授课</el-menu-item>
        <el-menu-item index="2">发布通知</el-menu-item>
        <el-menu-item index="3">学生成绩</el-menu-item>
        <el-menu-item index="4">作业批改</el-menu-item>
        <el-menu-item index="5">个人主页</el-menu-item>
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
          this.$router.push('/teContainer/myTeach');
          break;
        case '2':
          this.$router.push('/teContainer/myNotify');
          break;
        case '3':
          this.$router.push('/teContainer/grade');
          break;
        case '4':
          this.$router.push('/teContainer/assign');
          break;
        case '5':
          this.$router.push('/teContainer/myInform');
      }
    }
  },
  mounted() {
    this.$message.success("老师，欢迎您")
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
