<template>
  <el-container>
    <el-header>
      <h2>课程信息</h2>
      <!-- 搜索框和搜索按钮 -->
      <el-input
          v-model="searchQuery"
          placeholder="请输入课程标题进行搜索"
          @input="handleSearch"
          style="width: 300px; margin-right: 20px;"
      ></el-input>
    </el-header>

    <el-main>
      <!-- 课程表格 -->
      <el-table :data="paginatedCourses" style="width: 100%;height: 80vh">
        <el-table-column label="课程ID" prop="id"></el-table-column>
        <el-table-column label="课程标题" prop="title"></el-table-column>
        <el-table-column label="教师ID" prop="teacherId"></el-table-column>
        <el-table-column label="教师姓名" prop="name"></el-table-column> <!-- 新增教师姓名 -->
        <el-table-column label="教师科目" prop="subject"></el-table-column> <!-- 新增教师科目 -->
        <el-table-column label="教师教学时间" prop="experience"></el-table-column> <!-- 新增教师教学时间 -->
        <el-table-column label="开始时间" prop="startTime">
          <template #default="{ row }">
            <span>{{ formatDate(row.startTime) }}</span>
          </template>
        </el-table-column>
        <el-table-column label="结束时间" prop="endTime">
          <template #default="{ row }">
            <span>{{ formatDate(row.endTime) }}</span>
          </template>
        </el-table-column>

        <el-table-column label="操作">
          <template #default="{ row }">
            <el-button @click="selectCourse(row.id)" type="primary" size="small">选课</el-button>
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页器 -->
      <el-pagination
          v-if="filteredCourses.length > pageSize"
          :page-size="pageSize"
          :current-page="currentPage"
          :total="filteredCourses.length"
          @current-change="handlePageChange"
          layout="total, prev, pager, next, jumper"
      ></el-pagination>
    </el-main>



  </el-container>
</template>

<script>
import axios from 'axios';

export default {
  name: "CourseTable",
  data() {
    return {
      courses: [], // 存储所有课程数据
      searchQuery: "", // 存储搜索框的内容
      currentPage: 1, // 当前页
      pageSize: 7 , // 每页显示的课程数量
      addCourseDialogVisible: false, // 控制新增课程弹窗的显示与隐藏
      editCourseDialogVisible: false, // 控制编辑课程弹窗的显示与隐藏
      newCourse: { // 新增课程的表单数据
        title: '',
        teacherId: '',
        name: '', // 新增教师姓名
        subject: '', // 新增教师科目
        experience: '', // 新增教师教学时间
        startTime: '',
        endTime: ''
      },
      editCourse: { // 编辑课程的表单数据
        id: '',
        title: '',
        teacherId: '',
        name: '', // 新增教师姓名
        subject: '', // 新增教师科目
        experience: '', // 新增教师教学时间
        startTime: '',
        endTime: ''
      }
    };
  },
  computed: {
    // 根据搜索框内容过滤课程
    filteredCourses() {
      return this.courses.filter(course => {
        return course.title.toLowerCase().includes(this.searchQuery.toLowerCase());
      });
    },
    // 分页后的课程列表
    paginatedCourses() {
      const start = (this.currentPage - 1) * this.pageSize;
      const end = this.currentPage * this.pageSize;
      return this.filteredCourses.slice(start, end);
    }
  },
  methods: {
    // 获取课程数据
    async fetchCourses() {
      try {
        const response = await axios.get(`http://localhost:8080/stu/allCourse/${this.$store.state.childInform.id}`);
        console.log('课程数据：', response);
        this.courses = response.data; // 将返回的数据赋值给 courses
      } catch (error) {
        console.error("获取课程失败:", error);
      }
    },
    // 格式化日期
    formatDate(dateString) {
      if (!dateString) return "";
      const date = new Date(dateString);
      return date.toLocaleString(); // 格式化为本地时间格式
    },
    // 处理搜索
    handleSearch() {
      this.currentPage = 1; // 搜索时重置为第一页
    },

    // 选课
    async selectCourse(courseId) {
      try {
        const response = await axios.post('http://localhost:8080/stu/selectCourse', {courseId:courseId,studentId:this.$store.state.childInform.id});
        if (response.status === 200) {
          this.$message.success('课程编辑成功');
          await this.fetchCourses(); // 重新获取课程数据
          this.editCourseDialogVisible = false; // 关闭弹窗
        }
      } catch (error) {
        console.error("编辑课程失败:", error);
        this.$message.error('编辑课程失败');
      }
    },
    // 删除课程
    async deleteCourse(courseId) {
      try {
        const response = await axios.delete(`http://localhost:8080/stu/delCourseById/${courseId}`);
        if (response.status === 200) {
          this.courses = this.courses.filter(course => course.id !== courseId); // 更新课程列表
          this.$message.success('删除成功');
        }
      } catch (error) {
        console.error("删除课程失败:", error);
      }
    },
    // 显示新增课程弹窗
    showAddCourseDialog() {
      this.addCourseDialogVisible = true;
    },
    // 提交新增课程
    async submitAddCourse() {
      try {
        const response = await axios.post('http://localhost:8080/stu/addCourse', this.newCourse);
        if (response.status === 200) {
          this.$message.success('新增课程成功');
          this.addCourseDialogVisible = false; // 关闭弹窗
          await this.fetchCourses(); // 重新获取课程数据
          this.newCourse = { // 重置表单
            title: '',
            teacherId: '',
            name: '',
            subject: '',
            experience: '',
            startTime: '',
            endTime: ''
          };
        }
      } catch (error) {
        console.error("新增课程失败:", error);
        this.$message.error('新增课程失败');
      }
    },
    // 分页变更处理
    handlePageChange(page) {
      this.currentPage = page;
    }
  },
  mounted() {
    this.fetchCourses();  // 组件挂载后自动加载课程数据
  }
};
</script>

<style scoped>
.el-header {
  background-color: #f2f2f2;
  text-align: center;
  padding: 10px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.el-main {
  margin-top: 20px;
}
</style>
