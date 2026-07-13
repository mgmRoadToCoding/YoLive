<template>
  <el-form ref="form" :model="user" :rules="rules" label-width="80px">
    <el-form-item label="이전 비밀번호" prop="oldPassword">
      <el-input v-model="user.oldPassword" placeholder="이전 비밀번호를 입력하세요." type="password" show-password/>
    </el-form-item>
    <el-form-item label="새 비밀번호" prop="newPassword">
      <el-input v-model="user.newPassword" placeholder="새 비밀번호를 입력하세요." type="password" show-password/>
    </el-form-item>
    <el-form-item label="비밀번호 확인" prop="confirmPassword">
      <el-input v-model="user.confirmPassword" placeholder="비밀번호를 확인해 주세요." type="password" show-password/>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" size="mini" @click="submit">저장</el-button>
      <el-button type="danger" size="mini" @click="close">닫기</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
import { updateUserPwd } from "@/api/system/user";

export default {
  data() {
    const equalToPassword = (rule, value, callback) => {
      if (this.user.newPassword !== value) {
        callback(new Error("两次输入的密码不一致"));
      } else {
        callback();
      }
    };
    return {
      user: {
        oldPassword: undefined,
        newPassword: undefined,
        confirmPassword: undefined
      },
      // 表单校验
      rules: {
        oldPassword: [
          { required: true, message: "旧密码不能为空", trigger: "blur" }
        ],
        newPassword: [
          { required: true, message: "新密码不能为空", trigger: "blur" },
          { min: 6, max: 20, message: "长度在 6 到 20 个字符", trigger: "blur" }
        ],
        confirmPassword: [
          { required: true, message: "确认密码不能为空", trigger: "blur" },
          { required: true, validator: equalToPassword, trigger: "blur" }
        ]
      }
    };
  },
  methods: {
    submit() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          updateUserPwd(this.user.oldPassword, this.user.newPassword).then(response => {
            this.$modal.msgSuccess("修改成功");
          });
        }
      });
    },
    close() {
      this.$tab.closePage();
    }
  }
};
</script>
