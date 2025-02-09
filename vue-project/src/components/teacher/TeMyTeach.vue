<template>
  <div>
    <el-input
        v-model="searchQuery"
        placeholder="搜索课程"
        clearable
        style="margin-bottom: 20px;"
    ></el-input>

    <!-- 课程表格 -->
    <el-table :data="paginatedCourses" style="width: 100%">
      <el-table-column label="课程id" prop="id" />
      <el-table-column label="课程标题" prop="title" />
      <el-table-column label="开始时间" prop="startTime" />
      <el-table-column label="结束时间" prop="endTime" />
      <el-table-column label="操作">
        <template #default="{ row }">
          <el-button @click="showNotifyDialog(row)" type="primary" size="small">发布通知</el-button>
          <el-button @click="delCourse(row)" type="primary" size="small">取消课程</el-button>
          <!-- 新增发布作业按钮 -->
          <el-button @click="showPublishAssignmentDialog(row)" type="primary" size="small">发布作业</el-button>
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

    <!-- 开设课程按钮 -->
    <el-button type="primary" @click="showAddCourseDialog" style="margin-top: 20px;">
      开设课程
    </el-button>

    <!-- 添加课程对话框 -->
    <el-dialog
        v-model="addCourseDialogVisible"
        title="添加课程"
        @close="resetForm"
    >
      <el-form :model="newCourse" ref="courseForm">
        <el-form-item label="课程标题" prop="title" :rules="[{ required: true, message: '请输入课程标题', trigger: 'blur' }]">
          <el-input v-model="newCourse.title" />
        </el-form-item>
        <el-form-item label="授课老师ID" prop="teacherId">
          <el-input v-model="newCourse.teacherId" disabled />
        </el-form-item>
        <el-form-item label="开始时间" prop="startTime" :rules="[{ required: true, message: '请输入开始时间', trigger: 'blur' }]">
          <el-input v-model="newCourse.startTime" />
        </el-form-item>
        <el-form-item label="结束时间" prop="endTime" :rules="[{ required: true, message: '请输入结束时间', trigger: 'blur' }]">
          <el-input v-model="newCourse.endTime" />
        </el-form-item>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="addCourseDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitNewCourse">确认</el-button>
      </div>
    </el-dialog>

    <!-- 发布通知对话框 -->
    <el-dialog
        v-model="notifyDialogVisible"
        title="发布通知"
        @close="resetNotifyForm"
    >
      <el-form :model="notifyData" ref="notifyForm">
        <el-form-item label="通知标题" prop="title" :rules="[{ required: true, message: '请输入通知标题', trigger: 'blur' }]">
          <el-input v-model="notifyData.title" />
        </el-form-item>
        <el-form-item label="通知内容" prop="content" :rules="[{ required: true, message: '请输入通知内容', trigger: 'blur' }]">
          <el-input v-model="notifyData.content" />
        </el-form-item>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="notifyDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitNotify">确认</el-button>
      </div>
    </el-dialog>

    <!-- 发布作业对话框 -->
    <el-dialog
        v-model="publishAssignmentDialogVisible"
        title="发布作业"
        @close="resetPublishAssignmentForm"
    >
      <el-form :model="assignmentData" ref="assignmentForm">
        <el-form-item label="作业标题" prop="title" :rules="[{ required: true, message: '请输入作业标题', trigger: 'blur' }]">
          <el-input v-model="assignmentData.title" />
        </el-form-item>
        <el-form-item label="作业描述" prop="description" :rules="[{ required: true, message: '请输入作业描述', trigger: 'blur' }]">
          <el-input v-model="assignmentData.description" />
        </el-form-item>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="publishAssignmentDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitPublishAssignment">确认</el-button>
      </div>
    </el-dialog>
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
  ElDialog,
  ElForm,
  ElFormItem,
} from "element-plus";

export default {
  components: {
    ElInput,
    ElTable,
    ElTableColumn,
    ElPagination,
    ElButton,
    ElDialog,
    ElForm,
    ElFormItem,
  },
  data() {
    return {
      courses: [], // 存储课程数据
      searchQuery: "", // 搜索框的输入值
      currentPage: 1, // 当前页码
      pageSize: 5, // 每页显示的课程数量
      addCourseDialogVisible: false, // 控制添加课程对话框的显示
      notifyDialogVisible: false, // 控制发布通知对话框的显示
      publishAssignmentDialogVisible: false, // 控制发布作业对话框的显示
      newCourse: { // 新课程的数据模型
        title: "",
        teacherId: this.$store.state.teInform.id,
        startTime: "",
        endTime: "",
      },
      notifyData: { // 发布通知的数据模型
        title: "",
        content: "",
        courseId: null,
      },
      assignmentData: { // 发布作业的数据模型
        title: "",
        description: "",
        courseId: null,
      },
    };
  },
  computed: {
    filteredCourses() {
      return this.courses.filter((course) =>
          course.title.toLowerCase().includes(this.searchQuery.toLowerCase())
      );
    },
    paginatedCourses() {
      const start = (this.currentPage - 1) * this.pageSize;
      const end = start + this.pageSize;
      return this.filteredCourses.slice(start, end);
    },
  },
  methods: {
    async fetchCourses() {
      try {
        const response = await axios.get(
            `http://localhost:8080/te/myTeach/${this.$store.state.teInform.id}`
        );
        this.courses = response.data;
      } catch (error) {
        console.error("请求数据失败:", error);
      }
    },
    handlePageChange(page) {
      this.currentPage = page;
    },
    showAddCourseDialog() {
      this.addCourseDialogVisible = true;
      this.newCourse.teacherId = this.$store.state.teInform.id;
    },
    async submitNewCourse() {
      try {
        await axios.post("http://localhost:8080/te/addCourse", this.newCourse);
        this.addCourseDialogVisible = false;
        this.resetForm();
        await this.fetchCourses();
      } catch (error) {
        console.error("添加课程失败:", error);
      }
    },
    resetForm() {
      this.newCourse = {
        title: "",
        startTime: "",
        endTime: "",
      };
    },
    delCourse(row) {
      axios
          .delete("http://localhost:8080/te/delCourse", {
            params: { title: row.title, teacherId: this.$store.state.teInform.id },
          })
          .then(() => {
            this.$message.success("课程已取消");
            this.fetchCourses();
          })
          .catch((error) => {
            console.error("取消课程失败:", error);
          });
    },
    showNotifyDialog(row) {
      this.notifyData.courseId = row.id;
      this.notifyDialogVisible = true;
    },
    async submitNotify() {
      try {
        await axios.post("http://localhost:8080/te/addNotify", this.notifyData);
        this.$message.success("通知发布成功");
        this.notifyDialogVisible = false;
        this.resetNotifyForm();
      } catch (error) {
        console.error("发布通知失败:", error);
      }
    },
    resetNotifyForm() {
      this.notifyData = {
        title: "",
        content: "",
        courseId: null,
      };
    },
    showPublishAssignmentDialog(row) {
      this.assignmentData.courseId = row.id;
      this.publishAssignmentDialogVisible = true;
    },
    async submitPublishAssignment() {
      try {
        await axios.post("http://localhost:8080/te/addAssign", this.assignmentData);
        this.$message.success("作业发布成功");
        this.publishAssignmentDialogVisible = false;
        this.resetPublishAssignmentForm();
      } catch (error) {
        console.error("发布作业失败:", error);
      }
    },
    resetPublishAssignmentForm() {
      this.assignmentData = {
        title: "",
        description: "",
        courseId: null,
      };
    },
  },
  mounted() {
    this.fetchCourses();
  },
};
</script>

<style scoped>
.el-table {
  margin-bottom: 20px;
}
</style>
