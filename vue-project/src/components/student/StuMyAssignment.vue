<template>
  <div>
    <!-- 搜索框 -->
    <el-input
        v-model="searchQuery"
        placeholder="搜索作业"
        clearable
        style="margin-bottom: 20px;"
    ></el-input>

    <!-- 作业表格 -->
    <el-table :data="paginatedAssignments" style="width: 100%">
      <el-table-column prop="assignmentId"/>
      <el-table-column label="作业标题" prop="title" />
      <el-table-column label="课程标题" prop="courseTitle" />
      <el-table-column label="描述" prop="description" />
      <el-table-column label="内容" prop="content" />
      <el-table-column label="成绩" prop="grade" />
      <el-table-column label="状态" prop="status" />

      <el-table-column label="操作">
        <template #default="{ row }">
          <el-button @click="openEditModal(row)" :disabled="row.status === '已提交'" type="primary" size="small">
            提交
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页器 -->
    <el-pagination
        :current-page="currentPage"
        :page-size="pageSize"
        :total="filteredAssignments.length"
        @current-change="handlePageChange"
        layout="total, prev, pager, next, jumper"
    ></el-pagination>

    <!-- 编辑内容的弹出框 -->
    <el-dialog
        v-model="editDialogVisible"
        title="编辑作业内容"
        @close="resetEditContent"
    >
      <el-input
          v-model="editContent"
          type="textarea"
          placeholder="请输入新的作业内容"
          rows="4"
      ></el-input>
      <span slot="footer" class="dialog-footer">
        <el-button @click="editDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitAssignment">确认提交</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { ref, computed } from "vue";
import axios from "axios";
import { ElInput, ElTable, ElTableColumn, ElPagination, ElButton, ElDialog } from "element-plus";

export default {
  name: "AssignmentComponent",
  components: {
    ElInput,
    ElTable,
    ElTableColumn,
    ElPagination,
    ElButton,
    ElDialog,
  },
  data() {
    return {
      assignments: [], // 用来存储从后端获取的数据
      searchQuery: "",  // 搜索框的输入值
      currentPage: 1,   // 当前页码
      pageSize: 8,      // 每页显示的数据条数
      editDialogVisible: false, // 控制弹出框显示与隐藏
      editContent: "",  // 编辑框中的内容
      currentRow: null, // 当前正在编辑的作业
    };
  },
  computed: {
    filteredAssignments() {
      // 根据搜索条件过滤作业数据
      return this.assignments.filter((assignment) => {
        return assignment.title.toLowerCase().includes(this.searchQuery.toLowerCase());
      });
    },
    paginatedAssignments() {
      // 实现分页
      const start = (this.currentPage - 1) * this.pageSize;
      const end = start + this.pageSize;
      return this.filteredAssignments.slice(start, end);
    },
  },
  methods: {
    // 处理页码变化
    handlePageChange(page) {
      this.currentPage = page;
    },
    // 请求后端数据
    async fetchAssignments() {
      try {
        const response = await axios.get(
            `http://localhost:8080/stu/myAssign/${this.$store.state.stuInform.id}` // 后端接口
        );
        this.assignments = response.data;
      } catch (error) {
        console.error("请求数据失败:", error);
      }
    },
    // 打开编辑弹出框
    openEditModal(row) {
      this.currentRow = row; // 设置当前正在编辑的作业
      this.editContent = row.content; // 设置编辑框的初始内容
      this.editDialogVisible = true; // 显示弹出框
    },
    // 提交作业内容
    async submitAssignment() {
      if (!this.editContent) {
        this.$message.error("作业内容不能为空");
        return;
      }

      try {
        // 发送 POST 请求
        await axios.post("http://localhost:8080/stu/submitAssign", {
          assignmentId: this.currentRow.assignmentId,
          studentId:this.$store.state.stuInform.id,
          content: this.editContent  // 更新后的作业内容
        });

        // 更新当前作业的内容并关闭弹出框
        this.fetchAssignments(); // 组件加载时请求数据
        // this.currentRow.content = this.editContent;
        this.editDialogVisible = false;
        this.$message.success("作业提交成功");
      } catch (error) {
        console.error("提交失败:", error);
        this.$message.error("提交失败，请重试");
      }
    },
    // 重置编辑内容
    resetEditContent() {
      this.editContent = "";
      this.currentRow = null;
    },
  },
  mounted() {
    this.fetchAssignments(); // 组件加载时请求数据
  },
};
</script>

<style scoped>
/* 自定义样式 */
</style>
