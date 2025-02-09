<template>
  <div class="user-management">
    <el-input
        v-model="searchQuery"
        placeholder="搜索用户名"
        class="search-input"
        @input="handleSearch"
    />
    <el-button type="primary" @click="openAddDialog">添加用户</el-button>

    <el-table :data="paginatedUsers" border style="width: 100%">
      <el-table-column prop="id" label="ID" width="50" />
      <el-table-column prop="username" label="用户名" />
      <el-table-column prop="phone" label="电话" />
      <el-table-column prop="role" label="角色" />
      <el-table-column prop="createdAt" label="创建时间" />
      <el-table-column prop="updatedAt" label="更新时间" />
      <el-table-column label="操作" width="180">
        <template #default="{ row }">
          <el-button type="danger" @click="deleteUser(row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination
        v-model:current-page="currentPage"
        :page-size="pageSize"
        :total="totalUsers"
        layout="prev, pager, next"
        @current-change="handlePageChange"
    />

    <el-dialog v-model="addDialogVisible" title="添加用户">
      <el-form ref="addUserForm" :model="newUser" label-width="80px">
        <el-form-item label="用户名">
          <el-input v-model="newUser.username" />
        </el-form-item>
        <el-form-item label="密码">
          <el-input v-model="newUser.password" type="password" show-password />
        </el-form-item>
        <el-form-item label="电话">
          <el-input v-model="newUser.phone" />
        </el-form-item>
        <el-form-item label="角色">
          <el-select v-model="newUser.role" placeholder="选择角色">
            <el-option label="学生" value="学生" />
            <el-option label="教师" value="教师" />
            <el-option label="家长" value="家长" />
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="addDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="addUser">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      users: [], // 原始用户数据
      filteredUsers: [], // 经过搜索筛选的用户数据
      searchQuery: "",
      currentPage: 1,
      pageSize: 5,
      totalUsers: 0,
      addDialogVisible: false,
      newUser: { username: "", password: "", phone: "", role: "" },
    };
  },
  computed: {
    // 计算当前页要显示的数据
    paginatedUsers() {
      const start = (this.currentPage - 1) * this.pageSize;
      return this.filteredUsers.slice(start, start + this.pageSize);
    },
  },
  methods: {
    async fetchUsers() {
      try {
        const response = await axios.get("http://localhost:8080/ad/AllUser");
        this.users = response.data;
        this.filteredUsers = this.users; // 初始化筛选数据
        this.totalUsers = this.filteredUsers.length; // 更新总数
      } catch (error) {
        console.error("获取用户数据失败:", error);
      }
    },
    handlePageChange(page) {
      this.currentPage = page;
    },
    handleSearch() {
      // 过滤用户数据
      this.filteredUsers = this.users.filter((user) =>
          user.username.includes(this.searchQuery)
      );
      // 更新总数并重置页码
      this.totalUsers = this.filteredUsers.length;
      this.currentPage = 1;
    },
    async deleteUser(id) {
      try {
        await axios.delete(`http://localhost:8080/ad/delUser/${id}`);
        await this.fetchUsers();
      } catch (error) {
        console.error("删除用户失败:", error);
      }
    },
    openAddDialog() {
      this.newUser = { username: "", password: "", phone: "", role: "" };
      this.addDialogVisible = true;
    },
    async addUser() {
      try {
        await axios.post("http://localhost:8080/stu/signUp", this.newUser);
        this.addDialogVisible = false;
        await this.fetchUsers();
      } catch (error) {
        console.error("添加用户失败:", error);
      }
    },
  },
  mounted() {
    this.fetchUsers();
  },
};
</script>

<style scoped>
.search-input {
  width: 200px;
  margin-bottom: 10px;
}
</style>
