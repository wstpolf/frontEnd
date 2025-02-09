<template>
  <div>
    <!-- 搜索框 -->
    <el-input
        v-model="searchQuery"
        placeholder="搜索作业或课程"
        clearable
        style="margin-bottom: 20px;"
    ></el-input>

    <!-- 作业表格 -->
    <el-table :data="paginatedData" style="width: 100%">
      <el-table-column label="作业ID" prop="assignId" />
      <el-table-column label="学生姓名" prop="stuName" />
      <el-table-column label="作业名称" prop="assignName" />
      <el-table-column label="课程名称" prop="cname" />
      <el-table-column label="作业描述" prop="description" />
      <el-table-column label="作业内容" prop="content" />
      <el-table-column label="提交状态">
        <template #default="{ row }">
          <span>{{ row.stuId2 === null ? '未提交' : '已提交' }}</span>
        </template>
      </el-table-column>
      <el-table-column label="成绩" prop="grade">
        <template #default="{ row }">
          <el-input
              v-model="row.grade"
              type="number"
              placeholder="请输入成绩"
              style="width: 80px"
          />
        </template>
      </el-table-column>
      <el-table-column label="操作">
        <template #default="{ row }">
          <el-button
              @click="updateGrade(row)"
              type="primary"
              size="small"
          >批改成绩</el-button>
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
} from "element-plus";

export default {
  components: {
    ElInput,
    ElTable,
    ElTableColumn,
    ElPagination,
    ElButton,
  },
  data() {
    return {
      assignments: [], // 存储从后端获取的作业数据
      searchQuery: "", // 搜索框的输入内容
      currentPage: 1, // 当前页码
      pageSize: 5, // 每页显示的记录数
    };
  },
  computed: {
    // 搜索过滤后的数据
    filteredData() {
      return this.assignments.filter(
          (item) =>
              item.assignName.toLowerCase().includes(this.searchQuery.toLowerCase()) ||
              item.cname.toLowerCase().includes(this.searchQuery.toLowerCase()) ||
              item.description.toLowerCase().includes(this.searchQuery.toLowerCase()) ||
              (item.content && item.content.toLowerCase().includes(this.searchQuery.toLowerCase()))
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
    // 获取作业数据
    async fetchAssignments() {
      try {
        const response = await axios.get(
            `http://localhost:8080/te/getAssign/${this.$store.state.teInform.id}` // 根据实际后端接口修改 URL
        );
        this.assignments = response.data;
      } catch (error) {
        console.error("获取作业数据失败:", error);
      }
    },
    // 更新成绩
    async updateGrade(row) {
      if (row.grade === null || row.grade === "") {
        this.$message.error("请输入有效成绩！");
        return;
      }

      // 创建要发送的实体类对象
      const gradeData = {
        studentId: row.stuId,      // 学生ID
        content: row.content,      // 作业内容
        grade: row.grade,          // 成绩
        assignmentId: row.assignId, // 作业ID
      };

      try {
        const response = await axios.post(
            `http://localhost:8080/te/opAssign/${(row.stuId2===null)?0:1}`,  // 后端接口 URL
            gradeData
        );
        this.$message.success("成绩更新成功！");
        await this.fetchAssignments();
      } catch (error) {
        console.error("成绩更新失败:", error);
        this.$message.error("成绩更新失败！");
      }
    },
    // 处理分页变化
    handlePageChange(page) {
      this.currentPage = page;
    },
  },
  mounted() {
    this.fetchAssignments(); // 组件加载时获取数据
  },
};
</script>

<style scoped>
.el-table {
  margin-bottom: 20px;
}
</style>
