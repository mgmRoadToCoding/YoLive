<template>
  <div class="app-container">
    <el-row :gutter="10" class="mb5">
      <el-col :span="1.5">
        <el-button
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:order:export']"
          >导出</el-button
        >
      </el-col>
      <el-form
        :model="queryParams"
        ref="queryForm"
        :inline="true"
        label-width="68px"
        class="el-form-search"
      >
        <el-form-item
          label="订单号"
          prop="orderCode"
          class="el-form-search-item"
        >
          <el-input
            v-model="queryParams.orderCode"
            placeholder="请输入订单号"
            clearable
            size="mini"
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item
          label="订单状态"
          prop="orderStatus"
          class="el-form-search-item"
        >
          <el-select
            v-model="queryParams.orderStatus"
            placeholder="请选择订单状态"
            clearable
            size="mini"
          >
            <el-option
              v-for="dict in dict.type.mall_order_status"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item class="el-form-search-item">
          <el-button
            type="primary"
            icon="el-icon-search"
            size="mini"
            @click="handleQuery"
            >搜索</el-button
          >
          <el-button icon="el-icon-refresh" size="mini" @click="resetQuery"
            >重置</el-button
          >
        </el-form-item>
      </el-form>
    </el-row>

    <el-table
      :height="tableHeight"
      v-loading="loading"
      :data="orderList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="#" type="index" width="50" align="center">
        <template scope="scope">
          <span>{{
            (queryParams.pageNum - 1) * queryParams.pageSize + scope.$index + 1
          }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="订单号"
        align="center"
        prop="orderCode"
        width="160"
      />
      <el-table-column
        label="创建时间"
        align="center"
        prop="orderCreateTime"
        width="150"
      >
        <template slot-scope="scope">
          <span>{{
            parseTime(scope.row.orderCreateTime, "{y}-{m}-{d} {h}:{i}:{s}")
          }}</span>
        </template>
      </el-table-column>
      <el-table-column label="收货人" align="center" prop="receiver" />
      <el-table-column
        label="联系方式"
        align="center"
        prop="receiverPhone"
        width="110"
      />
      <el-table-column
        label="收货地址"
        align="center"
        prop="receiverAddress"
        width="120"
        :show-overflow-tooltip="true"
      />
      <el-table-column label="支付金额" align="center" prop="payAmount" />
      <el-table-column label="支付方式" align="center" prop="payMethod">
        <template slot-scope="scope">
          <dict-tag
            :options="dict.type.mall_pay_method"
            :value="scope.row.payMethod"
          />
        </template>
      </el-table-column>
      <el-table-column
        label="支付时间"
        align="center"
        prop="payTime"
        width="180"
      >
        <template slot-scope="scope">
          <span>{{
            parseTime(scope.row.payTime, "{y}-{m}-{d} {h}:{i}:{s}")
          }}</span>
        </template>
      </el-table-column>
      <el-table-column label="支付状态" align="center" prop="payStatus">
        <template slot-scope="scope">
          <dict-tag
            :options="dict.type.mall_pay_status"
            :value="scope.row.payStatus"
          />
        </template>
      </el-table-column>
      <!-- <el-table-column label="优惠金额" align="center" prop="disAmount" /> -->
      <el-table-column label="订单状态" align="center" prop="orderStatus">
        <template slot-scope="scope">
          <dict-tag
            :options="dict.type.mall_order_status"
            :value="scope.row.orderStatus"
          />
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark" />
      <el-table-column
        fixed="right"
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
        width="150"
      >
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:order:edit']"
            >详情</el-button
          >
          <!-- <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:order:remove']"
            >删除</el-button
          > -->
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改订单信息对话框 -->
    <el-dialog
      :visible.sync="open"
      width="800px"
      append-to-body
      :close-on-click-modal="false"
      v-dialogDrag
    >
      <div class="hearder-order">订单信息</div>
      <div class="content-order">
        <div class="order-item">
          <div class="item-title">订单编号</div>
          <div class="item-value">
            <el-label>{{ form.orderCode }}</el-label>
          </div>
        </div>
        <div class="order-item">
          <div class="item-title">订单状态</div>
          <div class="item-value">
            <el-label>
              <dict-tag
                :options="dict.type.mall_order_status"
                :value="form.orderStatus"
            /></el-label>
          </div>
        </div>
        <div class="order-item">
          <div class="item-title">支付状态</div>
          <div class="item-value">
            <el-label>
              <dict-tag
                :options="dict.type.mall_pay_status"
                :value="form.payStatus"
              />
            </el-label>
          </div>
        </div>
        <div class="order-item">
          <div class="item-title">支付金额</div>
          <div class="item-value">
            <el-label>{{ form.payAmount }}</el-label>
          </div>
        </div>
        <div class="order-item">
          <div class="item-title">支付方式</div>
          <div class="item-value">
            <el-label>
              <dict-tag
                :options="dict.type.mall_pay_method"
                :value="form.payMethod"
              />
            </el-label>
          </div>
        </div>
        <div class="order-item">
          <div class="item-title">创建时间</div>
          <div class="item-value">
            <el-label>{{
              parseTime(form.orderCreateTime, "{y}-{m}-{d} {h}:{i}:{s}")
            }}</el-label>
          </div>
        </div>
      </div>
      <div class="hearder-order">商品信息</div>
      <div class="content-order">
        <div
          class="order-item"
          v-for="(item, index) in mallOrderGoodsList"
          :key="index"
        >
          <div class="item-image">
            <el-image
              :src="item.image"
              :style="`width:80px;height:80px;`"
            ></el-image>
          </div>
          <div class="item-desc">
            <div class="title">{{ item.title }}</div>
            <div class="desc">{{ item.desc }}</div>
            <div class="price">{{ item.price }}{{ item.value }}</div>
          </div>
        </div>
      </div>
      <div class="hearder-order">收货信息</div>
      <div class="content-order">
        <div class="order-item">
          <div class="item-title">收 货 人</div>
          <div class="item-value">
            <el-label>{{ form.receiver }}</el-label>
          </div>
        </div>
        <div class="order-item">
          <div class="item-title">联系方式</div>
          <div class="item-value">
            <el-label>{{ form.receiverPhone }}</el-label>
          </div>
        </div>
        <div class="order-item">
          <div class="item-title">收货地址</div>
          <div class="item-value">
            <el-label>{{ form.receiverAddress }}</el-label>
          </div>
        </div>
      </div>
      <div slot="footer" class="dialog-footer">
        <!-- <el-button type="primary" @click="submitForm">确 定</el-button> -->
        <el-button @click="cancel">关 闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listOrder,
  getOrder,
  delOrder,
  addOrder,
  updateOrder,
} from "@/api/mall/order";

export default {
  name: "Order",
  dicts: ["mall_pay_status", "mall_order_status", "mall_pay_method"],
  data() {
    return {
      // 表格高度
      tableHeight: document.documentElement.clientHeight - 180,
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 订单信息表格数据
      orderList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 20,
        orderByColumn: "create_time",
        isAsc: "desc",
        orderCode: null,
        orderStatus: null,
      },
      // 表单参数
      form: {},
      mallOrderGoodsList: [
        {
          image:
            "https://7478-tx-cloud-mix-mall-d6944c-1302673523.tcb.qcloud.la/1595648833825306090.jpg",
          title: "娇兰口红330kiss亲亲唇膏344滋润（演示商品，不发货）",
          desc: "黑管344#性感橘",
          price: "￥279",
          value: "x1",
        },
        {
          image:
            "https://7478-tx-cloud-mix-mall-d6944c-1302673523.tcb.qcloud.la/1595655334145811959.jpg",
          title: "圣罗兰（YSL）纯口红 （演示商品，不发货）",
          desc: "黑管344#性感橘",
          price: "￥279",
          value: "x1",
        },
        {
          image:
            "https://7478-tx-cloud-mix-mall-d6944c-1302673523.tcb.qcloud.la/1595657991708941861.jpg",
          title: "PANSUR豹纹丝绒雾面唇釉滋润（演示商品，不发货）",
          desc: "黑管344#性感橘",
          price: "￥279",
          value: "x1",
        },
      ],
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询订单信息列表 */
    getList() {
      this.loading = true;
      listOrder(this.queryParams).then((response) => {
        this.orderList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        orderCode: null,
        orderCreateTime: null,
        payAmount: null,
        payMethod: null,
        payTime: null,
        payStatus: null,
        disAmount: null,
        orderStatus: null,
        sortNo: null,
        userId: null,
        addressId: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null,
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加订单信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getOrder(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "订单信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != null) {
            updateOrder(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addOrder(this.form).then((response) => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal
        .confirm("是否确认删除记录？")
        .then(function () {
          return delOrder(ids);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download(
        "system/order/export",
        {
          ...this.queryParams,
        },
        `order_${new Date().getTime()}.xlsx`
      );
    },
  },
};
</script>

<style scoped>
.hearder-order {
  font-size: 15px;
  color: #333;
  font-weight: 700;
  height: 40px;
  line-height: 40px;
}

.order-item {
  display: flex;
}

.order-item .item-title {
  width: 100px;
  text-align: center;
  color: #999;
  height: 30px;
  line-height: 30px;
}

.order-item .item-value {
  font-size: 14px;
  color: #333;
  height: 30px;
  line-height: 30px;
}

.order-item .item-image {
  margin: 10px 30px;
  display: block;
}

.order-item .item-desc {
  height: 25px;
  line-height: 25px;
}
</style>
