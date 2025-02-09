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

<!--      生成孩子表单，孩子数据内容格式为：    "id": 1, "userId": 1, "name": "张三", "grade": "高一",-->
      <el-form :model="stuData" label-width="120px" label-position="left">
        <h3>孩子信息</h3>
        <el-form-item label="孩子 ID">
          <el-input v-model="stuData.id" disabled />
        </el-form-item>
        <el-form-item label="孩子姓名">
          <el-input v-model="stuData.name" disabled />
        </el-form-item>
        <el-form-item label="孩子年级">
          <el-input v-model="stuData.grade" disabled />
        </el-form-item>
      </el-form>

<!--      <el-form :model="stuData" label-width="120px" label-position="left">-->
<!--        <h3>教师信息</h3>-->
<!--        <el-form-item label="教师 ID">-->
<!--          <el-input v-model="editableTeInform.id" disabled />-->
<!--        </el-form-item>-->
<!--        <el-form-item label="姓名">-->
<!--          <el-input v-model="editableTeInform.name" />-->
<!--        </el-form-item>-->
<!--        <el-form-item label="科目">-->
<!--          <el-input v-model="editableTeInform.subject" />-->
<!--        </el-form-item>-->
<!--        <el-form-item label="教学经验">-->
<!--          <el-input v-model="editableTeInform.experience" />-->
<!--        </el-form-item>-->
<!--      </el-form>-->
<!--      <el-button type="primary" @click="submit">保存修改</el-button>-->
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
      stuData:{}
      // editableTeInform: {
      //   id: this.$store.state.teInform.id,
      //   name: this.$store.state.teInform.name,
      //   grade: this.$store.state.teInform.subject,
      //   parentPhone: this.$store.state.teInform.experience,
      // },
    };
  },
  methods: {
    exit(){
      this.$store.state.isLoggedIn = false
      this.$router.push('/')
    },
    // submit() {
    //   // 提交修改后的学生信息
    //   const updatedTeInform = { ...this.editableTeInform };
    //   console.log("提交的数据:", updatedTeInform);
    //   try {
    //     const response = axios.put('http://localhost:8080/te/updateTe',updatedTeInform)
    //     this.$store.state.teInform = updatedTeInform
    //   }catch (error){
    //     console.log(error)}
    // }

  },
  computed: {

  },
  mounted() {
    console.log("this is store.teinform.data")
    console.log(this.$store.state.teInform)
    // this.editableTeInform = this.$store.state.teInform
    //向后端地址    @GetMapping("/getChild/{phone}")发送异步请求
    axios.get(`http://localhost:8080/pa/getChild/${this.$store.state.userInform.phone}`).then(response=>{
      console.log("this is response.data")
      console.log(response.data)
      this.stuData = response.data
      this.$store.state.childInform = response.data
    })

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
