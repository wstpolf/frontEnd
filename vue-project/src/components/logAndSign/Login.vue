<template>
  <div class="login-container">
    <el-card class="login-card" shadow="always">
      <h2 class="login-title">Login</h2>

      <!-- 登录表单 -->
      <el-form :model="form" ref="formRef" label-width="100px" class="login-form">
        <el-form-item label="Username" :rules="[{ required: true, message: 'Please input your username', trigger: 'blur' }]">
          <el-input v-model="form.username" placeholder="Enter your username"></el-input>
        </el-form-item>

        <el-form-item label="Password" :rules="[{ required: true, message: 'Please input your password', trigger: 'blur' }]">
          <el-input v-model="form.password" type="password" placeholder="Enter your password"></el-input>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="handleLogin" style="width: 100%">Login</el-button>
        </el-form-item>
      </el-form>

      <!-- 跳转到注册的链接 -->
      <div class="register-link">
        <span>Don't have an account? </span>
        <el-link type="primary" @click="goToRegister">Register</el-link>
      </div>
    </el-card>
  </div>
</template>

<script>
import axios from "axios";
import mapGetters from "vuex/dist/vuex.mjs";
import {reactive} from "vue";
export default {
  data() {
    return {
      // 定义表单数据
      form: {
        username: '',
        password: ''
      }
    };
  },
  methods: {
    //登录处理函数
    async handleLogin() {
      try {
        // 发送登录请求到后端，注意这里使用的是 GET 请求，参数通过 URL 传递
        const response = await axios.get('http://localhost:8080/stu/login', {
          params: {
            username: this.form.username,
            password: this.form.password
          }
        });

        // 检查后端返回的 response 数据（用户数据）
        if (response.data && response.data.id) {
          //存入用户数据，包括userid
          this.$store.state.userInform = response.data;
          console.log("this is userInform:---------------------------");
          console.log(this.$store.state.userInform)
          console.log("this is response.data")
          console.log(response.data)
          // 登录成功，跳转到 studentContainer 页面
          if(response.data.role === '学生'){
            this.$store.state.isLoggedIn=true
            while (true){
              //根据userid得到学生数据存入
              //通过userid在学生表中查找学生数据，包括学生id
              try {
                const response1 = await axios.get(`http://localhost:8080/stu/stuInform/${this.$store.state.userInform.id}`)
                this.$store.dispatch("setStuData",response1.data)
                console.log('this is stuInformresponse----------------')
                console.log(response1.data)
                console.log('这是studata。。。。。。。。。。。。。。。。')
                console.log(this.$store.state.stuInform)
                console.log('this is stuinfrom,s  userid')
                console.log(this.$store.state.stuInform.name,this.$store.state.stuInform.parentPhone)
                break
              }catch (error){
                if(this.$store.state.stuInform.id === null){
                  try {//如果学生第一次登录，数据添加学生表
                    const response2 = await axios.post(`http://localhost:8080/stu/addStu/${this.$store.state.userInform.id}`)
                  }catch (error){
                    console.log(error)
                  }
                }
              }
              console.error(error)
            }
            this.$router.push('/studentContainer');
          }
          // else if 老师。
          else if(response.data.role === '教师'){
            this.$store.state.isLoggedIn = true
            while(true){
              try {
                //根据userId请求后端得到teacher表教师数据，有则跳出循环
                const response3 = await axios.get(`http://localhost:8080/te/myInform/${this.$store.state.userInform.id}`)
                this.$store.dispatch("setTeData",response3.data)
                console.log('this.teacherResponse')
                console.log(response3.data)
                //存在老师信息则跳出循环
                if (response3.data){
                  break
                }else{
                  //不存在则添加老师信息
                  const response4 = await axios.post(`http://localhost:8080/te/addTe/${this.$store.state.userInform.id}`)
                  console.log("this is response4")
                  console.log(response4.data)
                }
                break;
              }catch (error){
                console.error(error)
              }
            }
            this.$router.push('/teContainer')
          }else if(response.data.role === '家长'){
            this.$store.state.isLoggedIn = true
            await axios.get(`http://localhost:8080/pa/getChild/${this.$store.state.userInform.phone}`).then(response=>{
              console.log("this is response.data")
              console.log(response.data)
              this.$store.state.childInform = response.data
              console.log(this.$store.state.childInform)
            })
            this.$router.push('/paContainer')
          }else if(response.data.role === '管理员'){
            this.$store.state.isLoggedIn = true
            this.$router.push('/adContainer')
          }
          // else if 家长
        } else {
          // 登录失败，返回内容为空，显示错误提示
          this.$message.error('登录失败，请检查用户名和密码');
        }
      } catch (error) {
        // 捕获请求失败时的错误
        console.error('登录请求失败', error);
        this.$message.error('网络错误，请稍后再试');
      }
    },
    // 跳转到注册页面
    goToRegister() {
      this.$router.push('/signUp');  // 跳转到注册页面
    }
  }
};
</script>

<style scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #f4f6f8;
}

.login-card {
  width: 400px;
  padding: 20px;
}

.login-title {
  text-align: center;
  margin-bottom: 20px;
  font-size: 24px;
}

.login-form {
  margin-bottom: 20px;
}

.register-link {
  text-align: center;
}
</style>
