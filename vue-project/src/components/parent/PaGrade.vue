<template>
  <el-container>
    <el-header>
      <h2>学生成绩</h2>
      <!-- 搜索框 -->
      <el-input
          v-model="searchQuery"
          placeholder="请输入课程标题进行搜索"
          @input="handleSearch"
          style="width: 300px; margin-right: 20px;"
      ></el-input>
    </el-header>

    <el-main>
      <!-- 成绩表格 -->
      <el-table :data="paginatedGrades" style="width: 100%">
        <el-table-column label="课程标题" prop="title"></el-table-column>
        <el-table-column label="课程ID" prop="courseId"></el-table-column>
        <el-table-column label="成绩" prop="grade"></el-table-column>
      </el-table>

      <!-- 分页器 -->
      <el-pagination
          v-if="filteredGrades.length > pageSize"
          :page-size="pageSize"
          :current-page="currentPage"
          :total="filteredGrades.length"
          @current-change="handlePageChange"
          layout="total, prev, pager, next, jumper"
      ></el-pagination>
    </el-main>
  </el-container>
</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      studentId: this.$store.state.stuInform.id, // 从store中获取学生ID
      grades: [], // 存储所有成绩数据
      searchQuery: '', // 存储搜索框的内容
      pageSize: 8, // 每页显示的成绩数量
      currentPage: 1, // 当前页码
    };
  },
  computed: {
    // 过滤后的成绩数据
    filteredGrades() {
      return this.grades.filter(course => {
        return course.title.toLowerCase().includes(this.searchQuery.toLowerCase());
      });
    },
    // 分页后的成绩数据
    paginatedGrades() {
      const start = (this.currentPage - 1) * this.pageSize;
      const end = this.currentPage * this.pageSize;
      return this.filteredGrades.slice(start, end);
    }
  },
  methods: {
    // 获取成绩数据
    async fetchGrades() {
      try {
        const response = await axios.get(`http://localhost:8080/stu/myGrade/${this.$store.state.childInform.id}`);
        this.grades = response.data; // 获取所有成绩数据
      } catch (error) {
        console.error('获取成绩失败:', error);
      }
    },
    // 处理搜索框输入变化
    handleSearch() {
      this.currentPage = 1; // 搜索时重置为第一页
    },
    // 处理分页变化
    handlePageChange(page) {
      this.currentPage = page; // 更新当前页码
    }
  },
  created() {
    this.fetchGrades(); // 初始化时获取成绩数据
  }
};
</script>

<style scoped>
.el-header {
  background-color: #f2f2f2;
  text-align: center;
  padding: 20px;
  display: flex;
  justify-content: center;
  align-items: center;
}

.el-main {
  margin-top: 20px;
}
</style>
