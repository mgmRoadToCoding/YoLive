<template>
  <div>
    <svg-icon icon-class="question" @click="goto" />
  </div>
</template>

<script>
export default {
  name: "SourceDoc",
  data() {
    return {
      url: "http://doc.sourcebyte.vip",
    };
  },
  methods: {
    goto() {
      window.open(this.url);
    },
  },
};
</script>
