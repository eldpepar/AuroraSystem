<template>
  <div class="app-container">
    <div class="search-div">
      <el-form label-width="70px" size="small">
        <el-row>
          <el-col :span="8">
            <el-form-item label="操作模块">
              <el-input style="width: 95%" v-model="searchObj.keyword" placeholder="操作人"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="操作时间">
              <el-date-picker
                v-model="createTimes"
                type="datetimerange"
                range-separator="至"
                start-placeholder="开始时间"
                end-placeholder="结束时间"
                value-format="yyyy-MM-dd HH:mm:ss"
                style="margin-right: 10px;width: 100%;"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row style="display:flex">
          <el-button type="primary" icon="el-icon-search" size="mini" @click="fetchData()">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetData">重置</el-button>
        </el-row>
      </el-form>
    </div>
    <!-- 列表 -->
    <el-table
      v-loading="listLoading"
      :data="list"
      stripe
      border
      style="width: 100%;margin-top: 10px;">

      <el-table-column
        label="序号"
        width="70"
        align="center">
        <template slot-scope="scope">
          {{ (page - 1) * limit + scope.$index + 1 }}
        </template>
      </el-table-column>

      <el-table-column prop="title" label="系统模块" width="80"/>
      <el-table-column prop="businessType" label="操作类型" width="100"/>
      <el-table-column prop="requestMethod" label="请求方式" width="80"/>
      <el-table-column prop="operName" label="操作人员" width="120"/>
      <el-table-column prop="operIp" label="登录IP" width="120"/>
      <el-table-column prop="operUrl" show-overflow-tooltip label="请求路径" width="180"/>
      <el-table-column label="状态" width="80">
        <template slot-scope="scope">
          <el-switch v-model="scope.row.status===1"></el-switch>
        </template>
      </el-table-column>
      <el-table-column prop="createTime" label="操作时间" width="180"/>
      <el-table-column label="详细信息">
        <template slot-scope="scope">
          <el-button
            plain
            size="mini"
            type="success"
            @click="openOperParam(scope.row.operParam)">
            请求
          </el-button>
          <el-button
            plain
            size="mini"
            type="primary"
            @click="openJsonResult(scope.row.jsonResult)">
            响应
          </el-button>
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

    <el-dialog title="请求信息" :visible.sync="diaOperVisible" width="40%">
        <el-container>{{opers}}</el-container>
    </el-dialog>
    <el-dialog title="相应信息" :visible.sync="diaResultVisible" width="40%">
      <el-container>{{opers}}</el-container>
    </el-dialog>
  </div>
</template>
<script>
import api from '@/api/system/oper'

export default {
  data() {
    return {
      listLoading: false, // 数据是否正在加载
      list: null, // 列表
      total: 0, // 数据库中的总记录数
      page: 1, // 默认页码
      limit: 8, // 每页记录数
      searchObj: {}, // 查询表单对象
      diaOperVisible: false,
      diaResultVisible: false,
      opers: {},
      createTimes: []
    }
  },
  created() {
    //调用列表方法
    this.fetchData()
  },
  methods: {
    /*
     全选勾选状态发生改变的监听
   */
    handleCheckAllChange(value) {// value 当前勾选状态true/false
      // 如果当前全选, userRoleIds就是所有角色id的数组, 否则是空数组
      this.userRoleIds = value ? this.allRoles.map(item => item.id) : []
      // 如果当前不是全选也不全不选时, 指定为false
      this.isIndeterminate = false
    },

    /*
      角色列表选中项发生改变的监听
    */
    handleCheckedChange(value) {
      const {userRoleIds, allRoles} = this
      this.checkAll = userRoleIds.length === allRoles.length && allRoles.length > 0
      this.isIndeterminate = userRoleIds.length > 0 && userRoleIds.length < allRoles.length
    },
    // 重置查询表单
    resetData() {
      console.log('重置查询表单')
      this.searchObj = {}
      this.createTimes = []
      this.fetchData()
    },
    // 列表
    fetchData(page = 1) {
      this.page = page
      if (this.createTimes && this.createTimes.length == 2) {
        this.searchObj.createTimeBegin = this.createTimes[0]
        this.searchObj.createTimeEnd = this.createTimes[1]
      }
      api.getPageList(this.page, this.limit, this.searchObj)
        .then(response => {
          this.list = response.data.records
          this.total = response.data.total
        })
    },
    openOperParam(operParam) {
      this.diaOperVisible = true
      this.opers = operParam
    },
    openJsonResult(jsonResult) {
      this.diaResultVisible = true
    }
  }
}
</script>
