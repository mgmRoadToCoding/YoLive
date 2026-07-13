<template>
  <div :class="className" :style="{ height: height, width: width }" />
</template>

<script>
import { initEcharts } from "@/api/index";
import echarts from "echarts";
require("echarts/theme/macarons"); // echarts theme
import resize from "./mixins/resize";

export default {
  mixins: [resize],
  props: {
    className: {
      type: String,
      default: "chart",
    },
    width: {
      type: String,
      default: "100%",
    },
    height: {
      type: String,
      default: "350px",
    },
    autoResize: {
      type: Boolean,
      default: true,
    },
  },
  data() {
    return {
      chart: null,
    };
  },
  mounted() {
    this.$nextTick(() => {
      this.initChart();
    });
  },
  beforeDestroy() {
    if (!this.chart) {
      return;
    }
    this.chart.dispose();
    this.chart = null;
  },
  methods: {
    initChart() {
      this.chart = echarts.init(this.$el, "macarons");
      initEcharts().then((res) => {
        this.chart.showLoading({ text: "正在努力的读取数据中..." });
        this.chart.setOption(res);
        this.chart.hideLoading();
      });
    },
  },
};
</script>
