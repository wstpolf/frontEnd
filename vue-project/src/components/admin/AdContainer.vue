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
        <el-menu-item index="1">账号管理</el-menu-item>
        <el-button @click="logOut">退出登录</el-button>
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
          this.$router.push('/AdContainer/account');
          break;
        case '2':
          this.$router.push('/AdContainer/log');
          break;
      }
    },
    logOut(){
      this.$store.state.isLoggedIn = "false"
      this.$router.push('/')
    }
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
