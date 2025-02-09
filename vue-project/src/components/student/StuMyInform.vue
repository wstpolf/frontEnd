<template>
  <el-container>
    <el-header>
      <h2>个人信息</h2>
    </el-header>
    <el-main>
      <el-form :model="editableUserInform" label-width="120px" label-position="left">
        <h3>用户信息</h3>
        <el-form-item label="用户 ID">
          <el-input v-model="editableUserInform.id" disabled />
        </el-form-item>
        <el-form-item label="用户名">
          <el-input v-model="editableUserInform.username" disabled />
        </el-form-item>
        <el-form-item label="电话">
          <el-input v-model="editableUserInform.phone" disabled />
        </el-form-item>
        <el-form-item label="角色">
          <el-input v-model="editableUserInform.role" disabled />
        </el-form-item>
      </el-form>

      <el-divider></el-divider>

      <el-form :model="getdata" label-width="120px" label-position="left">
        <h3>学生信息</h3>
        <el-form-item label="学生 ID">
          <el-input v-model="editableStuInform.id" disabled />
        </el-form-item>
        <el-form-item label="姓名">
          <el-input v-model="editableStuInform.name" />
        </el-form-item>
        <el-form-item label="年级">
          <el-input v-model="editableStuInform.grade" />
        </el-form-item>
        <el-form-item label="家长电话">
          <el-input v-model="editableStuInform.parentPhone" />
        </el-form-item>
      </el-form>

      <el-button type="primary" @click="submit">保存修改</el-button>
      <el-button type="primary" @click="exit">退出登录</el-button>
    </el-main>
  </el-container>
</template>

<script>
import axios from "axios";
import  reload from "../logAndSign/Login.vue"
export default {
  data() {
    return {
      editableUserInform: {
        id: this.$store.state.userInform.id,
        username: this.$store.state.userInform.username,
        phone: this.$store.state.userInform.phone,
        role: this.$store.state.userInform.role,
      },
      editableStuInform: {
        id: this.$store.state.stuInform.id,
        name: this.$store.state.stuInform.name,
        grade: this.$store.state.stuInform.grade,
        parentPhone: this.$store.state.stuInform.parentPhone,
      },
    };
  },
  methods: {
    exit(){
      this.$store.state.isLoggedIn = false
      this.$router.push('/')
    },
    submit() {
      // 提交修改后的学生信息
      const updatedStuInform = { ...this.editableStuInform };
      console.log("提交的数据:", updatedStuInform);
      try {
        const response = axios.put('http://localhost:8080/stu/updateStu',updatedStuInform)
        this.$store.state.stuInform = updatedStuInform
      }catch (error){
        console.log(error)}
    }

  },
  computed: {
    getdata(){
      return this.editableStuInform
    }
  }
};
</script>

<style scoped>
.el-header {
  text-align: center;
  font-size: 20px;
  font-weight: bold;
  margin-bottom: 20px;
}
.el-main {
  margin: 0 auto;
  max-width: 600px;
}
</style>
