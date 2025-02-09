<template>
  <div>
    <!-- 搜索框 -->
    <el-input
        v-model="searchQuery"
        placeholder="搜索通知标题"
        clearable
        style="margin-bottom: 20px; width: 300px;"
    ></el-input>

    <!-- 表格 -->
    <el-table :data="paginatedNotifications" style="width: 100%">
      <el-table-column label="通知标题" prop="title"></el-table-column>
      <el-table-column label="课程名称" prop="courseName"></el-table-column> <!-- 显示课程名称 -->
      <el-table-column label="通知内容" prop="content"></el-table-column>
      <el-table-column label="发布时间" prop="createdAt"></el-table-column>
      <el-table-column label="课程ID" prop="courseId"></el-table-column>
    </el-table>

    <!-- 分页器 -->
    <el-pagination
        :current-page="currentPage"
        :page-size="pageSize"
        :total="filteredNotifications.length"
        @current-change="handlePageChange"
        layout="total, prev, pager, next, jumper"
    ></el-pagination>
  </div>
</template>

<script>
import axios from 'axios';
import { ref, computed, onMounted } from 'vue';
import { ElInput, ElTable, ElTableColumn, ElPagination } from 'element-plus';

export default {
  name: 'NotificationComponent',
  components: {
    ElInput,
    ElTable,
    ElTableColumn,
    ElPagination,
  },
  data() {
    return {
      notifications: [], // 用来存储从后端获取的通知数据
      searchQuery: '',   // 搜索框的输入值
      currentPage: 1,    // 当前页
      pageSize: 5,       // 每页显示的条数
    };
  },
  computed: {
    // 根据搜索框内容过滤通知列表
    filteredNotifications() {
      return this.notifications.filter((notification) => {
        return notification.title.toLowerCase().includes(this.searchQuery.toLowerCase());
      });
    },
    // 分页后的通知列表
    paginatedNotifications() {
      const start = (this.currentPage - 1) * this.pageSize;
      const end = start + this.pageSize;
      return this.filteredNotifications.slice(start, end);
    },
  },
  methods: {
    // 请求通知数据
    async fetchNotifications() {
      try {
        const response = await axios.get(`http://localhost:8080/stu/notify/${this.$store.state.stuInform.id}`); // 根据实际后端接口修改 URL
        this.notifications = response.data; // 将返回的数据赋值给 notifications
      } catch (error) {
        console.error('请求数据失败:', error);
      }
    },
    // 处理分页变化
    handlePageChange(page) {
      this.currentPage = page; // 更新当前页
    },
  },
  mounted() {
    this.fetchNotifications(); // 组件加载时请求通知数据
  },
};
</script>

<style scoped>
/* 可以根据需要自定义样式 */
</style>
