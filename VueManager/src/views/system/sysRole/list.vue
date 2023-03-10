<template>
  <div class="app-container">

    <!--查询表单-->
    <div class="search-div">
      <el-form label-width="70px" size="small">
        <el-row>
          <el-col :span="24">
            <el-form-item label="角色名称">
              <el-input style="width: 20%" v-model="searchObj.roleName" placeholder="角色名称"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row style="display:flex">
          <el-button type="primary" icon="el-icon-search" size="mini" @click="fetchData()">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetData">重置</el-button>
          <!-- 添加修改工具条 -->
          <el-button type="success" icon="el-icon-plus" size="mini" @click="add">添 加</el-button>
          <el-button type="danger" class="btn-add" size="mini" @click="batchRemove()">批量删除</el-button>
        </el-row>
      </el-form>
    </div>
    <!-- 表格 -->
    <el-table
      :data="list"
      stripe
      border
      style="width: 100%;margin-top: 10px;"
      @selection-change="handleSelectionChange">

      <el-table-column type="selection"/>

      <el-table-column
        label="序号"
        width="70"
        align="center">
        <template slot-scope="scope">
          {{ (page - 1) * limit + scope.$index + 1 }}
        </template>
      </el-table-column>

      <el-table-column prop="roleName" label="角色名称"/>
      <el-table-column prop="roleCode" label="角色编码"/>
      <el-table-column prop="createTime" label="创建时间" width="160"/>
      <el-table-column label="操作" width="200" align="center">
        <template slot-scope="scope">
          <el-button type="primary" icon="el-icon-edit" size="mini" @click="edit(scope.row.id)" title="修改"/>
          <el-button type="danger" icon="el-icon-delete" size="mini" @click="removeDataById(scope.row.id)"
                     title="删除"/>
          <el-button type="warning" icon="el-icon-baseball" size="mini" @click="showAssignAuth(scope.row)"
                     title="分配权限"/>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页组件 -->
    <el-pagination
      :current-page="page"
      :total="total"
      :page-size="limit"
      style="padding: 30px 0; text-align: center;"
      layout="total, prev, pager, next, jumper"
      @current-change="fetchData"/>

    <el-dialog title="添加/修改" :visible.sync="dialogVisible" width="40%">
      <el-form ref="dataForm" :model="sysRole" label-width="150px" size="small" style="padding-right: 40px;">
        <el-form-item label="角色名称">
          <el-input v-model="sysRole.roleName"/>
        </el-form-item>
        <el-form-item label="角色编码">
          <el-input v-model="sysRole.roleCode"/>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
            <el-button @click="dialogVisible = false" size="small" icon="el-icon-refresh-right">取 消</el-button>
            <el-button type="primary" icon="el-icon-check" @click="saveOrUpdate()" size="small">确 定</el-button>
          </span>
    </el-dialog>
  </div>
</template>

<script>
import api from '@/api/system/role'

export default {
  name: 'List',
  data() {
    return {
      list: [],
      total: 0,
      page: 1,
      limit: 8,
      searchObj: {},

      dialogVisible: false, // 弹出框
      sysRole: {}, // 封装添加表单数据
      selectValue: [] // 复选框选择内容封装数组
    }
  },

  created() {
    this.fetchData()
  },

  methods: {
    // 跳转分配菜单权限
    showAssignAuth(row) {
      this.$router.push('/system/assignAuth?id=' + row.id + '&roleName=' + row.roleName);
    },
    // 重置
    resetData() {
      // 清空表单
      this.searchObj = {}
      // 查询所有数据
      this.fetchData()
    },
    // 分页查询
    fetchData(pageNum = 1) {
      this.page = pageNum
      api.getPageList(this.page, this.limit, this.searchObj)
        .then(response => {
          // 页数赋值
          this.page = pageNum
          // ajax
          api.getPageList(this.page, this.limit, this.searchObj)
            .then(response => {
              // 每页数据列表
              this.list = response.data.records
              // 总记录数
              this.total = response.data.total
            })
        })
    },
    // 根据ID删除
    removeDataById(id) {
      this.$confirm('此操作将永久删除该角色, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // 调用方法删除
        api.removeId(id)
          .then(response => {
            // 提示
            this.$message({
              type: 'success',
              message: '删除成功!'
            })
            // 刷新
            this.fetchData()
          })
      })
    },
    // 复选框发生变化执行方法
    handleSelectionChange(selection) {
      this.selectValue = selection
      // console.log(this.selectValue)
    },
    // 批量删除
    batchRemove() {
      // 判断
      if (this.selectValue.length === 0) {
        this.$message.warning('请选择要删除的记录！')
        return
      }
      this.$confirm('此操作将永久删除该角色, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // 数组
        var idList = []
        // 获取多个复选框对应id，封装到数组里面
        // [1,2,3]
        for (var i = 0; i < this.selectValue.length; i++) {
          var obj = this.selectValue[i]
          // id值
          var id = obj.id
          // 放到数组里面
          idList.push(id)
        }
        api.batchRemove(idList).then(response => {
          // 提示
          this.$message({
            type: 'success',
            message: '删除成功!'
          })
          // 刷新
          this.fetchData()
        })
      })
    },

    // 修改-数据回显
    edit(id) {
      // 弹出框
      this.dialogVisible = true
      api.getRoleId(id).then(response => {
        this.sysRole = response.data
      })
    },
    // 点击确定
    saveOrUpdate() {
      // 判断添加还是修改
      if (!this.sysRole.id) {
        // 添加
        this.saveRole()
      } else {
        this.updateRole()
      }
    },
    // 修改的方法
    updateRole() {
      api.update(this.sysRole)
        .then(response => {
          // 提示
          this.$message({
            type: 'success',
            message: '修改成功!'
          })
          // 关闭弹框
          this.dialogVisible = false
          // 刷新页面
          this.fetchData()
        })
    },
    // 添加的方法
    saveRole() {
      api.saveRole(this.sysRole)
        .then(response => {
          // 提示
          this.$message({
            type: 'success',
            message: '添加成功!'
          })
          // 关闭弹框
          this.dialogVisible = false
          // 刷新页面
          this.fetchData()
        })
    },
    // 点击添加，弹出框
    add() {
      this.dialogVisible = true
      this.sysRole = {}
    }
  }
}
</script>

<style scoped>

</style>
