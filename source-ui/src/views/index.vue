<template>
  <div class="dashboard-editor-container">
    <!-- <el-alert
      title="思创OA"
      type="info"
      description="思创OA | 新一代智慧OA租赁服务管理平台，融合了服务管理的流程化概念和产品进销存管理的人财物理念。
      集进销存、租赁、抄表、结算、应收应付、发票、设备资产、报修，耗材等为一体，为企业全面管理提供系统支撑。
      欢迎试用体验，体验地址：https://oa.sourcebyte.vip"
      style="line-height: 1.5"
      :closable="false"
      show-icon
    >
    </el-alert> -->
    <panel-group />
    <el-row style="background: #fff; padding: 32px 16px 0">
      <line-chart :height="lineHeight" />
    </el-row>
  </div>
</template>

<script>
import PanelGroup from "./dashboard/PanelGroup";
import LineChart from "./dashboard/LineChart";

export default {
  name: "Index",
  components: {
    PanelGroup,
    LineChart,
  },
  data() {
    return {
      // 350头部，70提示框
      // 如果设置隐藏top-tags，则还需要减去35
      // lineHeight: document.documentElement.clientHeight - 350 - 70 + "px", //实时屏幕高度
      lineHeight: document.documentElement.clientHeight - 350 + "px", //实时屏幕高度
    };
  },
  methods: {},
};
</script>

<style lang="scss" scoped>
.dashboard-editor-container {
  padding: 32px;
  background-color: #fff;
  position: relative;

  .chart-wrapper {
    background: #fff;
    padding: 16px 16px 0;
    margin-bottom: 32px;
  }
}

@media (max-width: 1024px) {
  .chart-wrapper {
    padding: 8px;
  }
}
</style>
