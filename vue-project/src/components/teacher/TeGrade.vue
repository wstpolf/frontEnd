<template>
  <div>
    <!-- 搜索框 -->
    <el-input
        v-model="searchQuery"
        placeholder="搜索课程标题"
        clearable
        style="margin-bottom: 20px;"
    ></el-input>

    <!-- 数据表格 -->
    <el-table :data="paginatedData" style="width: 100%">
      <el-table-column prop="stuName" label="学生姓名" />
      <el-table-column prop="title" label="课程标题" />
      <el-table-column prop="stuId" label="学生ID" />
      <el-table-column prop="cid" label="课程ID" />
      <el-table-column prop="grade" label="成绩">
        <template #default="{ row }">
          {{ row.grade === null ? "未设置" : row.grade }}
        </template>
      </el-table-column>
      <el-table-column label="操作">
        <template #default="{ row }">
          <el-button
              @click="openEditDialog(row)"
              type="primary"
              size="small"
          >
            修改成绩
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

    <!-- 编辑成绩对话框 -->
    <el-dialog
        title="修改成绩"
        v-model="editDialogVisible"
        width="30%"
    >
      <el-form :model="currentRow">
        <el-form-item label="成绩">
          <el-input
              v-model="currentRow.grade"
              type="number"
              placeholder="输入成绩"
          ></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="editDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="saveGrade">保存</el-button>
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
      dataList: [], // 存储从后端获取的数据
      searchQuery: "", // 搜索框内容
      currentPage: 1, // 当前页码
      pageSize: 5, // 每页显示的记录数
      editDialogVisible: false, // 控制编辑对话框的显示与隐藏
      currentRow: null, // 当前编辑的行
    };
  },
  computed: {
    // 搜索过滤后的数据
    filteredData() {
      return this.dataList.filter((item) =>
          item.title.toLowerCase().includes(this.searchQuery.toLowerCase())
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
    async fetchDataList() {
      try {
        const response = await axios.get(`http://localhost:8080/te/getGrade/${this.$store.state.teInform.id}`);
        this.dataList = response.data;
      } catch (error) {
        console.error("获取数据失败:", error);
      }
    },
    // 处理分页变化
    handlePageChange(page) {
      this.currentPage = page;
    },
    // 打开编辑对话框
    openEditDialog(row) {
      this.currentRow = { ...row }; // 创建行的副本，避免直接修改数据
      this.editDialogVisible = true;
    },
    // 保存修改后的成绩
    async saveGrade() {
      try {
        await axios.post("http://localhost:8080/te/opGrade", {
          studentId: this.currentRow.stuId,
          courseId: this.currentRow.cid,
          grade: this.currentRow.grade,
        });
        // 更新本地数据
        const index = this.dataList.findIndex(
            (item) => item.stuId === this.currentRow.stuId && item.cid === this.currentRow.cid
        );
        if (index !== -1) {
          this.dataList[index].grade = this.currentRow.grade;
        }
        this.editDialogVisible = false; // 关闭对话框
        this.$message.success("成绩修改成功！");
      } catch (error) {
        console.error("修改成绩失败:", error);
        this.$message.error("修改失败，请稍后重试！");
      }
    },
  },
  mounted() {
    this.fetchDataList(); // 组件加载时获取数据
  },
};
</script>

<style scoped>
.el-table {
  margin-bottom: 20px;
}
</style>
