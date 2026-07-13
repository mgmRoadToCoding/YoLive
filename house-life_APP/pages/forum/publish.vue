<template>
  <view class="container">
    <view class="content">
      <!-- 标题 -->
      <u-input
        v-model="form.title"
        placeholder="Please add a title"
        maxlength="60"
        input-align="left"
        :clearable="true"
        class="title-input"
      ></u-input>

      <!-- 内容区域（留白，贴近参考图），添加边框 -->
      <u-input
        v-model="form.content"
        type="textarea"
        placeholder="Please enter content"
        placeholder-style="color: #c0c4cc;"
        :height="240"
        :border="true"
        border-color="#dcdfe6"
        maxlength="1000"
        class="content-textarea"
      ></u-input>
      <!-- 图片上传区域（虚线边框 + 加号） -->
      <view class="upload-section">
        <u-upload
          :action="uploadAction"
          :max-count="9"
          :auto-upload="true"
          :show-upload-list="true"
          :multiple="true"
          name="file"
          :width="200"
          :height="200"
          @on-success="onUploadSuccess"
          @on-remove="onUploadRemove"
          @on-error="onUploadError"
          @on-uploaded="onUploaded"
        ></u-upload>
      </view>
      <view class="btn">
        <u-button type="primary" size="medium" @click="submitPost">
          Publish
        </u-button>
      </view>
    </view>
  </view>
</template>

<script>
import config from '@/common/config.js'
// 在页面内显式注册 uView 组件，避免 Unknown custom element 报错
import UInput from '@/uview-ui/components/u-input/u-input.vue'
import UUpload from '@/uview-ui/components/u-upload/u-upload.vue'
import UButton from '@/uview-ui/components/u-button/u-button.vue'

export default {
  components: {
    'u-input': UInput,
    'u-upload': UUpload,
  },
  data() {
    return {
      form: {
        title: '',
        content: '',
      },
      imageFileNames: [],
      uploadAction: '',
    }
  },
  created() {
    // 与后端保持一致：/common/upload
    this.uploadAction = `${config.baseUrl}/common/upload`
  },
  methods: {
    onUploadSuccess(data, index, lists) {
      // 兼容不同返回结构：优先 data.data.fileName，其次 data.fileName
      const fileName = data?.data?.fileName || data?.fileName || ''
      if (fileName) {
        // 直接从 lists 映射，避免索引错位
        this.imageFileNames = (lists || [])
          .map((item) => {
            const resp = item.response
            return resp?.data?.fileName || resp?.fileName || ''
          })
          .filter(Boolean)
      }
    },
    onUploadRemove(index, lists) {
      // 重新从列表计算 fileName，保持与预览一致
      this.imageFileNames = (lists || [])
        .map((item) => {
          const resp = item.response
          return resp?.data?.fileName || resp?.fileName || ''
        })
        .filter(Boolean)
    },
    onUploaded(lists) {
      console.log(lists);
      
      // 最终统一一下
      this.imageFileNames = (lists || [])
        .map((item) => {
          const resp = item.response
          return resp?.data?.fileName || resp?.fileName || ''
        })
        .filter(Boolean)
    },
    onUploadError(err) {
      this.$mytip.toast('图片上传失败或认证失效')
    },
    async submitPost() {
      // 鉴权校验
      const authed = await this.$checkAuth()
      if (!authed) return

      // 基础校验
      if (!this.form.title.trim()) {
        return this.$mytip.toast('请输入标题')
      }
      if (!this.form.content.trim()) {
        return this.$mytip.toast('请输入内容')
      }

      const images = this.imageFileNames.join(',') // 逗号分隔的资源路径
      try {
        const res = await this.$u.http.post(
          '/forum/posts',
          {
            title: this.form.title.trim(),
            content: this.form.content.trim(),
            images,
          },
          {
            header: { 'Content-Type': 'application/json' },
          }
        )

        if (res && res.code === 200) {
          this.$mytip.toast('Published successfully, pending review')
          setTimeout(() => {
            uni.navigateBack()
          }, 500)
        } else {
          this.$mytip.toast(res?.msg || 'Publish failed')
        }
      } catch (e) {
        this.$mytip.toast('Publishing error, please try again later')
      }
    },
  },
}
</script>

<style lang="scss" scoped>
page {
  background: #fff;
  height: 100vh;

}
.content {
  padding: 20rpx 30rpx;
}
.title-input {
  font-size: 32rpx;
}
.notice {
  margin-top: 30rpx;
  color: #c0c4cc;
  font-size: 26rpx;
  line-height: 1.6;
  display: flex;
  flex-direction: column;
}
.upload-section {
  margin-top: 30rpx;
}
/* 覆盖上传按钮样式为虚线边框 */
::v-deep .u-add-wrap {
  border: 1px dashed #dcdfe6;
}
.content-textarea {
  margin-top: 30rpx;
}
.btn{
  margin-top: 30rpx;
  display: flex;
  justify-content: center;
}
</style>
