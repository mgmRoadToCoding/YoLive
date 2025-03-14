<template>
  <div>
    <svg-icon icon-class="github" @click="goto" />
  </div>
</template>

<script>
export default {
  name: "SourceGit",
  data() {
    return {
      url: "https://gitee.com/open-source-byte/source-vue",
    };
  },
  methods: {
    goto() {
      window.open(this.url);
    },
  },
};
</script>
