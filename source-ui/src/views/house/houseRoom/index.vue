<template>
  <div class="app-container">
    <el-row :gutter="10" class="mb5">
      <el-col :span="1.5">
        <el-button
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:houseRoom:add']"
          >新增</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:houseRoom:edit']"
          >修改</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:houseRoom:remove']"
          >删除</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-dropdown
          size="medium"
          @command="(command) => handleImportCommand(command)"
          v-hasPermi="['system:houseRoom:export']"
        >
          <el-button plain icon="el-icon-caret-bottom" size="mini"
            >数据导入</el-button
          >
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item command="handleImport" icon="el-icon-upload"
              >数据导入</el-dropdown-item
            >
            <el-dropdown-item command="handleExport" icon="el-icon-download"
              >数据导出</el-dropdown-item
            >
          </el-dropdown-menu>
        </el-dropdown>
      </el-col>
      <el-col :span="1.5">
        <el-dropdown
          size="medium"
          @command="(command) => handleMoreCommand(command)"
          v-hasPermi="['system:houseRoom:more']"
        >
          <el-button
            plain
            icon="el-icon-caret-bottom"
            size="mini"
            :disabled="multiple"
            v-hasPermi="['system:houseRoom:more']"
            >更多功能</el-button
          >
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item command="handleAgent" icon="el-icon-setting"
              >设置经纪人</el-dropdown-item
            >
            <el-dropdown-item command="handleAudit" icon="el-icon-check"
              >审核房源</el-dropdown-item
            >
            <el-dropdown-item command="handlePush" icon="el-icon-lock"
              >出租房源</el-dropdown-item
            >
            <el-dropdown-item command="handleClose" icon="el-icon-close"
              >下架房源</el-dropdown-item
            >
            <el-dropdown-item command="handleOpen" icon="el-icon-check"
              >上架房源</el-dropdown-item
            >
          </el-dropdown-menu>
        </el-dropdown>
      </el-col>
      <el-form
        :model="queryParams"
        ref="queryForm"
        :inline="true"
        label-width="68px"
        class="el-form-search"
      >
        <el-form-item label="类型" prop="type" class="el-form-search-item">
          <el-select
            v-model="queryParams.type"
            placeholder="请选择类型"
            clearable
            size="mini"
          >
            <el-option
              v-for="(item, index) in typeOptions"
              :key="index"
              :label="item.label"
              :value="item.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="状态" prop="state" class="el-form-search-item">
          <el-select
            v-model="queryParams.state"
            placeholder="请选择状态"
            clearable
            size="mini"
          >
            <el-option label="待审核" value="0"></el-option>
            <el-option label="待出租" value="1"></el-option>
            <el-option label="已出租" value="2"></el-option>
            <el-option label="已下架" value="3"></el-option>
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
          <right-toolbar
            :showSearch.sync="showSearch"
            @setDataTable="setDataTable"
            :columns="columns"
          ></right-toolbar>
        </el-form-item>
      </el-form>
    </el-row>

    <el-table
      :height="tableHeight"
      v-loading="loading"
      :data="houseRoomList"
      @selection-change="handleSelectionChange"
      ref="dataTable"
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
        label="类型"
        align="center"
        prop="type"
        :formatter="typeFormatter"
        width="60"
        :key="columns[0].key"
        v-if="columns[0].visible"
      >
      </el-table-column>
      <el-table-column
        label="户型"
        align="center"
        prop="houseCode"
        width="120"
        :key="columns[1].key"
        v-if="columns[1].visible"
      />
      <el-table-column
        label="整套面积"
        align="center"
        prop="houseArea"
        width="80"
        :key="columns[2].key"
        v-if="columns[2].visible"
      />
      <el-table-column
        label="出租房间"
        align="center"
        prop="roomType"
        :formatter="roomTypeFormatter"
        width="80"
        :key="columns[3].key"
        v-if="columns[3].visible"
      ></el-table-column>
      <el-table-column
        label="房屋面积"
        align="center"
        prop="roomArea"
        width="80"
        :key="columns[4].key"
        v-if="columns[4].visible"
      />
      <el-table-column
        label="朝向"
        align="center"
        prop="direction"
        width="60"
        :key="columns[5].key"
        v-if="columns[5].visible"
      />
      <el-table-column
        label="装修"
        align="center"
        prop="decoration"
        width="80"
        :key="columns[6].key"
        v-if="columns[6].visible"
      />
      <el-table-column
        label="租金"
        align="center"
        prop="price"
        width="60"
        :key="columns[7].key"
        v-if="columns[7].visible"
      />
      <el-table-column
        label="起租日期"
        align="center"
        prop="startDate"
        width="100"
        :key="columns[8].key"
        v-if="columns[8].visible"
      >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.startDate, "{y}-{m}-{d}") }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="状态"
        align="center"
        prop="state"
        width="60"
        :formatter="stateFormatter"
        :key="columns[9].key"
        v-if="columns[9].visible"
      />
      <el-table-column
        label="封面图"
        align="center"
        prop="faceUrl"
        width="80"
        :key="columns[10].key"
        v-if="columns[10].visible"
      >
        <template slot-scope="scope">
          <image-preview :src="scope.row.faceUrl" :width="30" :height="30" />
        </template>
      </el-table-column>
      <el-table-column
        label="小区名称"
        align="center"
        prop="villageName"
        :show-overflow-tooltip="true"
        width="180"
        :key="columns[11].key"
        v-if="columns[11].visible"
      />
      <el-table-column
        label="房牌号"
        align="center"
        prop="houseNo"
        :show-overflow-tooltip="true"
        width="90"
        :key="columns[13].key"
        v-if="columns[13].visible"
      />
      <el-table-column
        label="门牌号"
        align="center"
        prop="roomCode"
        width="80"
        :key="columns[12].key"
        v-if="columns[12].visible"
      />
      <el-table-column
        label="房源代号"
        align="center"
        prop="code"
        width="90"
        :key="columns[14].key"
        v-if="columns[14].visible"
      ></el-table-column>
      <el-table-column
        label="房东姓名"
        align="center"
        prop="ownerName"
        width="100"
        :key="columns[17].key"
        v-if="columns[17].visible"
      />
      <el-table-column
        label="房东电话"
        align="center"
        prop="owerPhone"
        width="100"
        :key="columns[18].key"
        v-if="columns[18].visible"
      />
      <el-table-column
        label="经纪人"
        align="center"
        prop="agentName"
        width="70"
        :key="columns[15].key"
        v-if="columns[15].visible"
      />
      <el-table-column
        label="经纪人电话"
        align="center"
        prop="agentPhone"
        width="100"
        :key="columns[16].key"
        v-if="columns[16].visible"
      />
      <el-table-column
        label="备注"
        align="center"
        prop="remark"
        width="80"
        :show-overflow-tooltip="true"
        :key="columns[19].key"
        v-if="columns[19].visible"
      />
      <el-table-column
        fixed="right"
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
        width="180"
      >
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:houseRoom:edit']"
            >修改</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:houseRoom:remove']"
            >删除</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="evalList(scope.row)"
            v-hasPermi="['system:evaluate:list']"
            >评价</el-button
          >
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

    <!-- 添加或修改房源详情对话框 -->
    <el-dialog
      :title="title"
      :visible.sync="open"
      width="800px"
      append-to-body
      :close-on-click-modal="false"
      v-dialogDrag
    >
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row :gutter="30">
          <el-col :span="12">
            <el-form-item label="类型" prop="type">
              <el-select
                v-model="form.type"
                placeholder="请选择类型"
                clearable
                :style="{ width: '100%' }"
              >
                <el-option
                  v-for="(item, index) in typeOptions"
                  :key="index"
                  :label="item.label"
                  :value="item.value"
                  :disabled="item.disabled"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="居室" prop="houseNum">
              <el-select
                v-model="form.houseNum"
                placeholder="请选择居室"
                clearable
                :style="{ width: '100%' }"
              >
                <el-option
                  v-for="(item, index) in houseNumOptions"
                  :key="index"
                  :label="item.label"
                  :value="item.value"
                  :disabled="item.disabled"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="30">
          <el-col :span="12"
            ><el-form-item label="厅室" prop="houseHall">
              <el-select
                v-model="form.houseHall"
                placeholder="请选择厅室"
                clearable
                :style="{ width: '100%' }"
              >
                <el-option
                  v-for="(item, index) in houseHallOptions"
                  :key="index"
                  :label="item.label"
                  :value="item.value"
                  :disabled="item.disabled"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12"
            ><el-form-item label="卫生间" prop="toiletNum">
              <el-select
                v-model="form.toiletNum"
                placeholder="请选择卫生间"
                clearable
                :style="{ width: '100%' }"
              >
                <el-option
                  v-for="(item, index) in toiletNumOptions"
                  :key="index"
                  :label="item.label"
                  :value="item.value"
                  :disabled="item.disabled"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="30">
          <el-col :span="12">
            <el-form-item label="整套面积" prop="houseArea">
              <el-input v-model="form.houseArea" placeholder="请输入整套面积" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="出租房间" prop="roomType">
              <el-select
                v-model="form.roomType"
                placeholder="请选择出租房间"
                clearable
                :style="{ width: '100%' }"
              >
                <el-option
                  v-for="(item, index) in roomTypeOptions"
                  :key="index"
                  :label="item.label"
                  :value="item.value"
                  :disabled="item.disabled"
                ></el-option>
              </el-select> </el-form-item
          ></el-col>
        </el-row>
        <el-row :gutter="30">
          <el-col :span="12"
            ><el-form-item label="房屋面积" prop="roomArea">
              <el-input v-model="form.roomArea" placeholder="请输入房屋面积" />
            </el-form-item>
          </el-col>
          <el-col :span="12"
            ><el-form-item label="朝向" prop="direction">
              <el-select
                v-model="form.direction"
                placeholder="请选择朝向"
                clearable
                :style="{ width: '100%' }"
              >
                <el-option
                  v-for="(item, index) in directionOptions"
                  :key="index"
                  :label="item.label"
                  :value="item.value"
                  :disabled="item.disabled"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="30">
          <el-col :span="12">
            <el-form-item label="装修" prop="decoration">
              <el-select
                v-model="form.decoration"
                placeholder="请选择装修"
                clearable
                :style="{ width: '100%' }"
              >
                <el-option
                  v-for="(item, index) in decorationOptions"
                  :key="index"
                  :label="item.label"
                  :value="item.value"
                  :disabled="item.disabled"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="租金" prop="price">
              <el-input v-model="form.price" placeholder="请输入租金" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="30">
          <el-col :span="12">
            <el-form-item label="起租日期" prop="startDate">
              <el-date-picker
                clearable
                size="small"
                v-model="form.startDate"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="选择起租日期"
                style="width: 100%"
              >
              </el-date-picker> </el-form-item
          ></el-col>
          <el-col :span="12">
            <el-form-item label="房源描述" prop="introduce">
              <el-input
                v-model="form.introduce"
                placeholder="请输入房源描述"
                :autosize="{ minRows: 2, maxRows: 2 }"
                :style="{ width: '100%' }"
              ></el-input> </el-form-item
          ></el-col>
        </el-row>
        <el-row :gutter="30">
          <el-col :span="12">
            <el-form-item label="房源小区" prop="villageName">
              <el-select
                v-model="form.villageName"
                placeholder="请选择房源小区"
                clearable
                :style="{ width: '100%' }"
                @change="changeVillage"
              >
                <el-option
                  v-for="(item, index) in houseVillageList"
                  :key="index"
                  :label="item.name"
                  :value="{ value: item.id, label: item.name }"
                  :disabled="item.disabled"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="详细地址" prop="address">
              <el-input
                v-model="form.address"
                placeholder="请输入详细地址"
              /> </el-form-item
          ></el-col>
        </el-row>
        <el-row :gutter="30">
          <el-col :span="12">
            <el-form-item label="房牌号" prop="houseNo">
              <el-input v-model="form.houseNo" placeholder="请输入房牌号" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="门牌号" prop="roomCode">
              <el-input v-model="form.roomCode" placeholder="请输入门牌号" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="30">
          <el-col :span="12">
            <el-form-item label="房东姓名" prop="ownerName">
              <el-input
                v-model="form.ownerName"
                placeholder="请输入房东姓名"
              /> </el-form-item
          ></el-col>
          <el-col :span="12">
            <el-form-item label="房东电话" prop="owerPhone">
              <el-input
                v-model="form.owerPhone"
                placeholder="请输入房东电话"
              /> </el-form-item
          ></el-col>
        </el-row>
        <el-row :gutter="30">
          <el-col :span="12">
            <el-form-item label="经度" prop="longitude">
              <el-input
                v-model="form.longitude"
                placeholder="请输入经度 longitude"
              /> </el-form-item
          ></el-col>
          <el-col :span="12">
            <el-form-item label="纬度" prop="latitude">
              <el-input
                v-model="form.latitude"
                placeholder="请输入纬度 latitude"
              /> </el-form-item
          ></el-col>
        </el-row>
        <el-row :gutter="30">
          <el-col :span="24">
            <el-form-item label="备注" prop="remark">
              <el-input
                v-model="form.remark"
                placeholder="请输入备注"
              /> </el-form-item
          ></el-col>
        </el-row>
        <el-row :gutter="10">
          <el-col :span="24">
            <el-form-item label="房源亮点" prop="featureList">
              <el-checkbox-group v-model="featureList">
                <el-checkbox label="看房方便"></el-checkbox>
                <el-checkbox label="独卫"></el-checkbox>
                <el-checkbox label="独立阳台"></el-checkbox>
                <el-checkbox label="智能锁"></el-checkbox>
                <el-checkbox label="可短租"></el-checkbox>
                <el-checkbox label="首次出租"></el-checkbox>
                <el-checkbox label="免物业费"></el-checkbox>
                <el-checkbox label="民用水电"></el-checkbox>
              </el-checkbox-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="10">
          <el-col :span="24">
            <el-form-item label="房源图片" prop="imageList">
              <image-upload v-model="imageList" :limit="9" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-row :gutter="30">
          <el-col :span="24">
            <div
              style="
                color: red;
                margin-left: 80px;
                cursor: pointer;
                float: left;
              "
            >
              <a
                href="https://api.map.baidu.com/lbsapi/getpoint/index.html"
                target="_blank"
              >
                帮助链接：获取经纬度
              </a>
            </div>
          </el-col>
        </el-row>
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 导入对话框 -->
    <el-dialog :title="upload.title" :visible.sync="upload.open" width="400px">
      <el-upload
        ref="upload"
        :limit="1"
        accept=".xlsx, .xls"
        :headers="upload.headers"
        :action="upload.url + '?updateSupport=' + upload.updateSupport"
        :disabled="upload.isUploading"
        :on-progress="handleFileUploadProgress"
        :on-success="handleFileSuccess"
        :auto-upload="false"
        drag
      >
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">
          将文件拖到此处，或
          <em>点击上传</em>
        </div>
        <div class="el-upload__tip" slot="tip">
          <el-checkbox v-model="upload.updateSupport" />
          <div style="display: inline-block; padding-left: 5px">
            是否更新已经存在的数据
          </div>
          <el-button
            type="primary"
            plain
            icon="el-icon-receiving"
            size="mini"
            @click="handleTemplete"
            style="float: right"
            >下载模板</el-button
          >
        </div>
        <div class="el-upload__tip" style="color: red" slot="tip">
          提示：仅允许导入“xls”或“xlsx”格式文件！
        </div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFileForm">确 定</el-button>
        <el-button @click="upload.open = false">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 更多搜索 -->
    <el-drawer
      title="房源搜索"
      :visible.sync="showSearch"
      direction="rtl"
      ref="drawer"
    >
      <div class="drawer_content">
        <el-form :model="queryParams" ref="queryMoreForm" label-width="80px">
          <el-form-item label="朝向" prop="direction">
            <el-select
              v-model="queryParams.direction"
              placeholder="请选择朝向"
              clearable
              :style="{ width: '100%' }"
            >
              <el-option
                v-for="(item, index) in directionOptions"
                :key="index"
                :label="item.label"
                :value="item.value"
                :disabled="item.disabled"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="装修" prop="decoration">
            <el-select
              v-model="queryParams.decoration"
              placeholder="请选择装修"
              clearable
              :style="{ width: '100%' }"
            >
              <el-option
                v-for="(item, index) in decorationOptions"
                :key="index"
                :label="item.label"
                :value="item.value"
                :disabled="item.disabled"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="租金" prop="price">
            <el-input v-model="queryParams.price" placeholder="请输入租金" />
          </el-form-item>
          <el-form-item label="小区名称" prop="villageName">
            <el-select
              v-model="queryParams.villageName"
              placeholder="请选择房源小区"
              clearable
              style="width: 100%"
            >
              <el-option
                v-for="(item, index) in houseVillageList"
                :key="index"
                :label="item.name"
                :value="item.name"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="房东姓名" prop="ownerName">
            <el-input
              v-model="queryParams.ownerName"
              placeholder="请输入房东姓名"
            />
          </el-form-item>
          <el-form-item label="房东电话" prop="ownerPhone">
            <el-input
              v-model="queryParams.ownerPhone"
              placeholder="请输入房东电话"
            />
          </el-form-item>
        </el-form>
        <div class="drawer_footer">
          <el-button type="primary" size="medium" @click="handleMoreQuery"
            >确定</el-button
          >
        </div>
      </div>
    </el-drawer>

    <select-user ref="select" :ids="ids" @ok="handleQuery" />
    <evaluate ref="eval" :id="checkid" />
  </div>
</template>

<script>
import {
  listHouseRoom,
  getHouseRoom,
  delHouseRoom,
  addHouseRoom,
  updateHouseRoom,
  handleAudit,
  handlePush,
  handleClose,
  handleOpen,
} from "@/api/house/houseRoom";
import { getToken } from "@/utils/auth";

import { listHouseVillage } from "@/api/house/houseVillage";
import selectUser from "./selectUser";
import evaluate from "../evaluate/index";

export default {
  name: "HouseRoom",
  dicts: ["sys_notice_status"],
  components: { evaluate, selectUser },
  data() {
    return {
      // 导入参数
      upload: {
        // 是否显示弹出层（用户导入）
        open: false,
        // 弹出层标题（用户导入）
        title: "",
        // 是否禁用上传
        isUploading: false,
        // 是否更新已经存在的用户数据
        updateSupport: 0,
        // 设置上传的请求头部
        headers: { Authorization: "Bearer " + getToken() },
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + "/system/houseRoom/importData",
      },
      // 表格高度
      tableHeight: document.documentElement.clientHeight - 180,
      // 遮罩层
      loading: true,
      // 选中id数组
      ids: [],
      //评价列表查询的id
      checkid: null,
      // 选中行数组
      selection: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示更多搜索条件
      showSearch: false,
      // 总条数
      total: 0,
      // 房源详情表格数据
      houseRoomList: [],
      // 房源小区表格数据
      houseVillageList: [],
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
        direction: null,
        decoration: null,
        price: null,
        villageName: null,
        agentName: null,
        agentPhone: null,
      },
      // 房源亮点
      featureList: [],
      // 房源照片
      imageList: [],
      // 表单参数
      form: {},
      // 列信息
      columns: [
        { key: 0, label: `类型`, visible: true },
        { key: 1, label: `户型`, visible: true },
        { key: 2, label: `整套面积`, visible: true },
        { key: 3, label: `出租房间`, visible: true },
        { key: 4, label: `房屋面积`, visible: true },
        { key: 5, label: `朝向`, visible: true },
        { key: 6, label: `装修`, visible: true },
        { key: 7, label: `租金`, visible: true },
        { key: 8, label: `起租日期`, visible: true },
        { key: 9, label: `状态`, visible: true },
        { key: 10, label: `封面图`, visible: true },
        { key: 11, label: `小区名称`, visible: true },
        { key: 12, label: `房牌号`, visible: true },
        { key: 13, label: `门牌号`, visible: true },
        { key: 14, label: `房源代号`, visible: true },
        { key: 15, label: `房东姓名`, visible: true },
        { key: 16, label: `房东电话`, visible: true },
        { key: 17, label: `经纪人`, visible: true },
        { key: 18, label: `经纪人电话`, visible: true },
        { key: 19, label: `备注`, visible: true },
      ],
      // 表单校验
      rules: {
        type: [
          {
            required: true,
            message: "请选择类型",
            trigger: "change",
          },
        ],
        houseNum: [
          { required: true, message: "居室不能为空", trigger: "blur" },
        ],
        // houseHall: [
        //   { required: true, message: "厅室不能为空", trigger: "blur" },
        // ],
        // toiletNum: [
        //   { required: true, message: "卫生间不能为空", trigger: "blur" },
        // ],
        houseNo: [
          { required: true, message: "房牌号不能为空", trigger: "blur" },
        ],
        // direction: [
        //   { required: true, message: "朝向不能为空", trigger: "blur" },
        // ],
        decoration: [
          { required: true, message: "装修不能为空", trigger: "blur" },
        ],
        price: [{ required: true, message: "租金不能为空", trigger: "blur" }],
        startDate: [
          { required: true, message: "起租日期不能为空", trigger: "blur" },
        ],
        villageName: [
          { required: true, message: "小区名称不能为空", trigger: "blur" },
        ],
        ownerName: [
          { required: true, message: "房东姓名不能为空", trigger: "blur" },
        ],
        owerPhone: [
          { required: true, message: "房东电话不能为空", trigger: "blur" },
        ],
        longitude: [
          { required: true, message: "经度不能为空", trigger: "blur" },
        ],
        latitude: [
          { required: true, message: "纬度不能为空", trigger: "blur" },
        ],
      },
      typeOptions: [
        {
          label: "整租",
          value: 0,
        },
        {
          label: "合租",
          value: 1,
        },
      ],
      houseNumOptions: [
        {
          label: "一室",
          value: "一室",
        },
        {
          label: "二室",
          value: "二室",
        },
        {
          label: "三室",
          value: "三室",
        },
        {
          label: "四室",
          value: "四室",
        },
      ],
      houseHallOptions: [
        {
          label: "一厅",
          value: "一厅",
        },
        {
          label: "二厅",
          value: "二厅",
        },
      ],
      toiletNumOptions: [
        {
          label: "一卫",
          value: "一卫",
        },
        {
          label: "二卫",
          value: "二卫",
        },
      ],
      roomTypeOptions: [
        {
          label: "主卧",
          value: 1,
        },
        {
          label: "次卧",
          value: 2,
        },
      ],
      directionOptions: [
        {
          label: "东",
          value: "东",
        },
        {
          label: "南",
          value: "南",
        },
        {
          label: "西",
          value: "西",
        },
        {
          label: "北",
          value: "北",
        },
      ],
      decorationOptions: [
        {
          label: "简装",
          value: "简装",
        },
        {
          label: "中装",
          value: "中装",
        },
        {
          label: "精装修",
          value: "精装修",
        },
        {
          label: "豪装",
          value: "豪装",
        },
      ],
      stepTypeOptions: [
        {
          label: "电梯房",
          value: "电梯房",
        },
        {
          label: "楼梯房",
          value: "楼梯房",
        },
      ],
    };
  },
  created() {
    this.getList();
    this.getVillageList();
  },
  methods: {
    handleClose(done) {
      this.$confirm("确认关闭？")
        .then((_) => {
          done();
        })
        .catch((_) => {});
    },
    changeVillage(params) {
      const { value, label } = params;
      this.form.villageId = value;
      this.form.villageName = label;
    },
    /** 查询房源小区列表 */
    getVillageList() {
      listHouseVillage(this.queryParams).then((response) => {
        this.houseVillageList = response.rows;
      });
    },
    /** 查询房源详情列表 */
    getList() {
      this.loading = true;
      listHouseRoom(this.queryParams).then((response) => {
        this.houseRoomList = response.rows;
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
        type: null,
        houseNum: null,
        toiletNum: null,
        houseArea: null,
        roomType: null,
        roomCode: null,
        roomArea: null,
        direction: null,
        price: null,
        floor: null,
        stepType: null,
        startDate: null,
        introduce: null,
        ownerName: null,
        owerPhone: null,
        villageId: null,
        villageName: null,
        address: null,
        houseNo: null,
        payType: null,
        publishId: null,
        state: null,
        createTime: null,
        createName: null,
        updateTime: null,
        updateName: null,
        remark: null,
        houseHall: null,
        code: null,
        faceUrl: null,
        longitude: null,
        latitude: null,
      };
      this.featureList = [];
      this.imageList = null;
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
      this.resetForm("queryMoreForm");
      this.handleQuery();
    },
    /** 更多搜索按钮 */
    handleMoreQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
      this.$refs.drawer.closeDrawer();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id);
      this.selection = selection;
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加房源";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getHouseRoom(id).then((response) => {
        this.form = response.data;
        this.form.featureList.map((item) => {
          this.featureList.push(item.feature);
        });
        let imageArrary = [];
        this.form.imageList.map((item) => {
          imageArrary.push(item.imagePath);
        });
        this.imageList = imageArrary.join(",");
        this.open = true;
        this.title = "修改房源";
      });
    },
    /** 设置经纪人 */
    handleAgent() {
      this.$refs.select.show();
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.featureList && this.featureList.length > 0) {
            let featureList = this.featureList.map((val) => {
              return {
                feature: val,
              };
            });
            this.form.featureList = featureList;
          } else {
            return this.$modal.msgWarning("请选择房源亮点");
          }
          if (this.imageList) {
            let imageList = this.imageList.split(",").map((val) => {
              return {
                imageName: val.split("/")[6],
                imagePath: val,
                imgUrl: val,
                imageSize: -1,
              };
            });
            this.form.imageList = imageList;
          } else {
            return this.$modal.msgWarning("请选择房源图片");
          }
          if (this.form.id != null) {
            updateHouseRoom(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addHouseRoom(this.form).then((response) => {
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
          return delHouseRoom(ids);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => {});
    },
    evalList(row) {
      this.checkid = row.id;
      this.$refs.eval.show(row.id);
    },
    /** 导入 */
    handleImport() {
      this.upload.title = "房源导入";
      this.upload.open = true;
    },
    // 文件上传中处理
    handleFileUploadProgress(event, file, fileList) {
      this.upload.isUploading = true;
    },
    // 文件上传成功处理
    handleFileSuccess(response, file, fileList) {
      this.upload.open = false;
      this.upload.isUploading = false;
      this.$refs.upload.clearFiles();
      this.$alert(response.msg, "导入结果", { dangerouslyUseHTMLString: true });
      this.getList();
    },
    // 提交上传文件
    submitFileForm() {
      this.$refs.upload.submit();
    },
    /** 下载模板 */
    handleTemplete() {
      this.download(
        "system/houseRoom/templete",
        {
          ...this.queryParams,
        },
        `房源导入模板.xlsx`
      );
    },
    /** 导出按钮 */
    handleExport() {
      this.$confirm("确认导出房源数据吗?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        this.download(
          "system/houseRoom/export",
          {
            ...this.queryParams,
          },
          `房源_${new Date().getTime()}.xlsx`
        );
      });
    },
    typeFormatter(row, column) {
      if (row.type == 0) {
        return "整租";
      } else if (row.type == 1) {
        return "合租";
      }
    },
    roomTypeFormatter(row, column) {
      if (row.roomType == 1) {
        return "主卧";
      } else if (row.roomType == 2) {
        return "次卧";
      }
    },
    stateFormatter(row, column) {
      if (row.state == 0) {
        return "待审核";
      } else if (row.state == 1) {
        return "待出租";
      } else if (row.state == 2) {
        return "已出租";
      } else if (row.state == 3) {
        return "已下架";
      }
    },
    // 更多操作触发
    handleMoreCommand(command) {
      switch (command) {
        case "handleAgent":
          this.handleAgent();
          break;
        case "handleAudit":
          this.handleAudit();
          break;
        case "handlePush":
          this.handlePush();
          break;
        case "handleClose":
          this.handleClose();
          break;
        case "handleOpen":
          this.handleOpen();
          break;
        default:
          break;
      }
    },
    // 导入操作触发
    handleImportCommand(command) {
      switch (command) {
        case "handleImport":
          this.handleImport();
          break;
        case "handleTemplete":
          this.handleTemplete();
          break;
        case "handleExport":
          this.handleExport();
          break;
        default:
          break;
      }
    },
    /** 审核 */
    handleAudit() {
      // 是否已经设置经纪人
      let checked = true;
      const ids = this.ids;
      // 获取到所有选中的记录行，验证是否都已经设置经纪人，如没设置，需要提醒
      this.selection.filter((item) => {
        if (!item.agentUserId) {
          checked = false;
        }
      });
      if (checked) {
        this.$modal
          .confirm("确认审核房源吗？")
          .then(function () {
            return handleAudit(ids);
          })
          .then(() => {
            this.getList();
            this.$modal.msgSuccess("审核成功");
          })
          .catch(() => {});
      } else {
        this.$modal.msgWarning("请先设置经纪人");
      }
    },
    /** 出租 */
    handlePush() {
      const ids = this.ids;
      this.$modal
        .confirm("确认出租房源吗？")
        .then(function () {
          return handlePush(ids);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("出租成功");
        })
        .catch(() => {});
    },
    /** 下架 */
    handleClose() {
      const ids = this.ids;
      this.$modal
        .confirm("确认下架房源吗？")
        .then(function () {
          return handleClose(ids);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("下架成功");
        })
        .catch(() => {});
    },
    /** 上架 */
    handleOpen() {
      // 是否已经审核
      let audited = true;
      const ids = this.ids;
      // 获取到所有选中的记录行，验证是否都已经设置经纪人，如没设置，需要提醒
      this.selection.filter((item) => {
        if (item.state == 0) {
          audited = false;
        }
      });
      if (audited) {
        this.$modal
          .confirm("确认上架房源吗？")
          .then(function () {
            return handleOpen(ids);
          })
          .then(() => {
            this.getList();
            this.$modal.msgSuccess("上架成功");
          })
          .catch(() => {});
      } else {
        this.$modal.msgWarning("请先审核房源");
      }
    },
    // 重新绘制表格，防止抖动与滚动条异常
    setDataTable() {
      this.$nextTick(() => {
        this.$refs.dataTable.doLayout();
      });
    },
  },
};
</script>
<style lang="scss" scoped>
.drawer_content {
  margin: 0 30px;
  .drawer_footer {
    float: right;
    padding-bottom: 40rpx;
  }
}
</style>
