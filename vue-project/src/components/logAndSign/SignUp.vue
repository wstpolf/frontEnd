<template>
  <div class="register-container">
    <el-card class="register-card" shadow="always">
      <h2 class="register-title">Register</h2>

      <el-form :model="form" ref="formRef" label-width="100px" class="register-form" @submit.native.prevent="handleRegister">
        <!-- 用户名输入框 -->
        <el-form-item
            label="Username"
            :rules="[{ required: true, message: 'Please input your username', trigger: 'blur' }]">
          <el-input v-model="form.username" placeholder="Enter your username"></el-input>
        </el-form-item>

        <!-- 密码输入框 -->
        <el-form-item
            label="Password"
            :rules="[{ required: true, message: 'Please input your password', trigger: 'blur' }]">
          <el-input v-model="form.password" type="password" placeholder="Enter your password"></el-input>
        </el-form-item>

        <!-- 确认密码输入框 -->
        <el-form-item
            label="ConfirmPass"
            :rules="[
            { required: true, message: 'Please confirm your password', trigger: 'blur' },
            { validator: validatePassword, trigger: 'blur' }
          ]">
          <el-input v-model="form.confirmPassword" type="password" placeholder="Confirm your password"></el-input>
        </el-form-item>

        <!-- 角色输入框 -->
        <el-form-item
            label="role"
            :rules="[{ required: true, message: '学生/教师/家长', trigger: 'blur' }]">
          <el-input v-model="form.role" type="text" placeholder="学生/教师/家长"></el-input>
        </el-form-item>

        <!-- 手机号输入框 -->
        <el-form-item
            label="phone"
            :rules="[{ required: true, message: '手机号', trigger: 'blur' }]">
          <el-input v-model="form.phone" type="text" placeholder="手机号" ></el-input>
        </el-form-item>
        <!-- 注册按钮 -->
        <el-form-item>
          <el-button type="primary" @click="handleRegister" style="width: 100%">Register</el-button>
        </el-form-item>
      </el-form>

      <!-- 跳转到登录的链接 -->
      <div class="login-link">
        <span>Already have an account? </span>
        <el-link type="primary" @click="goToLogin">Login</el-link>
      </div>
    </el-card>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      // 定义表单数据
      form: {
        username: '',
        password: '',
        confirmPassword: '',
        role:'',
        phone:''
      }
    };
  },
  methods: {

    // 注册处理函数
    async handleRegister() {
      if(this.form.password===''||this.form.role===''||this.form.confirmPassword===''||this.form.username===''||this.form.phone===''){
        this.$message.error("有字段为空")
      }else {
        if((this.form.password===this.form.confirmPassword)&&(this.form.role==='学生'||this.form.role==='教师'||this.form.role==='家长')){
          try{
            const response = await axios.post('http://localhost:8080/stu/signUp',{
              username:this.form.username,
              password:this.form.password,
              role:this.form.role,
              phone:this.form.phone
            })
            if (response.status === 200){
              this.$message.success("注册成功")
              this.form.username=''
              this.form.role=''
              this.form.password=''
              this.form.confirmPassword=''
              this.form.phone=''
              this.$router.push('/')
            }
          }catch (error){
            this.$message.error("注册失败，试试换个名字吧~")
            console.log(error)
          }
        }else{
          if((this.form.role!=='学生'&&this.form.role!=='老师'&&this.form.role !== '家长')){
            this.$message.error("请填写正确的角色")
          }else {
            this.$message.error("两次密码不一致")
          }
        }
      }

    },
    // 跳转到登录页面
    goToLogin() {
      this.$router.push('/');  // 跳转到登录页面
    }
  }
};
</script>

<style scoped>
.register-container {
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #f4f6f8;
}

.register-card {
  width: 400px;
  padding: 20px;
}

.register-title {
  text-align: center;
  margin-bottom: 20px;
  font-size: 24px;
}

.register-form {
  margin-bottom: 20px;
}

.login-link {
  text-align: center;
}
</style>
