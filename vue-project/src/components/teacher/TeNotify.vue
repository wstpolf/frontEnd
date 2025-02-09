<template>
  <div>
    <!-- 搜索框 -->
    <el-input
        v-model="searchQuery"
        placeholder="搜索通知标题或内容"
        clearable
        style="margin-bottom: 20px;"
    ></el-input>

    <!-- 通知表格 -->
    <el-table :data="paginatedData" style="width: 100%">
      <el-table-column label="通知ID" prop="id" />
      <el-table-column label="标题" prop="title" />
      <el-table-column label="内容" prop="content" />
      <el-table-column label="创建时间" prop="createdAt" />
      <el-table-column label="课程名称" prop="courseName" />
      <el-table-column label="操作">
        <template #default="{ row }">
          <el-button
              @click="deleteNotification(row.id)"
              type="danger"
              size="small"
          >
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页器 -->
    <el-pagination
        v-if="filteredData.length > pageSize"
        :page-size="pageSize"
        :current-page="currentPage"
        :total="filteredData.length"
        @current-change="handlePageChange"
        layout="total, prev, pager, next, jumper"
    ></el-pagination>
  </div>
</template>

<script>
import axios from "axios";
import {
  ElInput,
  ElTable,
  ElTableColumn,
  ElPagination,
  ElButton,
  ElMessageBox,
  ElMessage,
} from "element-plus";

export default {
  components: {
    ElInput,
    ElTable,
    ElTableColumn,
    ElPagination,
    ElButton,
    ElMessageBox,
    ElMessage,
  },
  data() {
    return {
      notifications: [], // 存储从后端获取的通知数据
      searchQuery: "", // 搜索框的输入内容
      currentPage: 1, // 当前页码
      pageSize: 5, // 每页显示的记录数
    };
  },
  computed: {
    // 搜索过滤后的数据
    filteredData() {
      return this.notifications.filter(
          (item) =>
              item.title.toLowerCase().includes(this.searchQuery.toLowerCase()) ||
              item.content.toLowerCase().includes(this.searchQuery.toLowerCase())
      );
    },
    // 分页后的数据
    paginatedData() {
      const start = (this.currentPage - 1) * this.pageSize;
      const end = start + this.pageSize;
      return this.filteredData.slice(start, end);
    },
  },
  methods: {
    // 获取数据列表
    async fetchNotifications() {
      try {
        const response = await axios.get(
            `http://localhost:8080/te/getNotify/${this.$store.state.teInform.id}`
        );
        this.notifications = response.data;
      } catch (error) {
        console.error("获取通知数据失败:", error);
      }
    },
    // 删除通知
    async deleteNotification(id) {
      try {
        await ElMessageBox.confirm(
            "此操作将永久删除该通知, 是否继续?",
            "提示",
            {
              confirmButtonText: "确定",
              cancelButtonText: "取消",
              type: "warning",
            }
        );
        // 发送删除请求
        await axios.delete(`http://localhost:8080/te/delNotify/${id}`
        );
        ElMessage.success("删除成功！");
        // 删除本地通知列表中的对应项
        this.notifications = this.notifications.filter(
            (notification) => notification.id !== id
        );
      } catch (error) {
        if (error !== "cancel") {
          console.error("删除通知失败:", error);
          ElMessage.error("删除失败，请稍后重试");
        }
      }
    },
    // 处理分页变化
    handlePageChange(page) {
      this.currentPage = page;
    },
  },
  mounted() {
    this.fetchNotifications(); // 组件加载时获取数据
  },
};
</script>

<style scoped>
.el-table {
  margin-bottom: 20px;
}
</style>
