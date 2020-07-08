<template>
  <div>
    <div style="display: flex;justify-content: space-between">
      <el-button icon="el-icon-plus" type="primary" @click="showAddSalaryView"
        >添加账套</el-button
      >
      <el-button icon="el-icon-refresh" type="success" @click="getAllSalaies">刷新</el-button>
    </div>
    <div style="margin-top: 10px">
      <el-table :data="salaries" stripe border>
        <el-table-column type="selection" width="40"></el-table-column>
        <el-table-column
          width="120"
          prop="name"
          label="账套名称"
        ></el-table-column>
        <el-table-column
          width="70"
          prop="basicSalary"
          label="基本工资"
        ></el-table-column>
        <el-table-column
          width="70"
          prop="trafficSalary"
          label="交通补助"
        ></el-table-column>
        <el-table-column
          width="70"
          prop="lunchSalary"
          label="午餐补助"
        ></el-table-column>
        <el-table-column width="70" prop="bonus" label="奖金"></el-table-column>
        <el-table-column
          width="100"
          prop="createDate"
          label="启用时间"
        ></el-table-column>
        <el-table-column label="养老金" align="center">
          <el-table-column
            width="70"
            prop="pensionPer"
            label="比率"
          ></el-table-column>
          <el-table-column
            width="70"
            prop="pensionBase"
            label="基数"
          ></el-table-column>
        </el-table-column>
        <el-table-column label="医疗保险" align="center">
          <el-table-column
            width="70"
            prop="medicalPer"
            label="比率"
          ></el-table-column>
          <el-table-column
            width="70"
            prop="medicalBase"
            label="基数"
          ></el-table-column>
        </el-table-column>
        <el-table-column label="公积金" align="center">
          <el-table-column
            width="70"
            prop="accumulationFundPer"
            label="比率"
          ></el-table-column>
          <el-table-column
            width="70"
            prop="accumulationFundBase"
            label="基数"
          ></el-table-column>
        </el-table-column>
        <el-table-column label="操作" align="center">
          <template slot-scope="scope">
            <el-button @click="showEditSalaryView(scope.row)">编辑</el-button>
            <el-button type="danger" @click="deleteSalary(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <el-dialog :title="dialogTitle" :visible.sync="dialogVisible" width="50%">
      <div
        style="display: flex;justify-content: space-around;align-items: center"
      >
        <el-steps direction="vertical" :active="activeIndex">
          <el-step
            :title="itemName"
            v-for="(itemName, index) in salaryItemName"
            :key="index"
          ></el-step>
        </el-steps>
        <el-input
          v-model="salary[title]"
          :placeholder="'请输入' + salaryItemName[index] + '...'"
          v-for="(value, title, index) in salary"
          :key="index"
          v-show="activeIndex == index"
          style="width: 300px"
        >
        </el-input>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="preStep">{{
          activeIndex == 10 ? "取消" : "上一步"
        }}</el-button>
        <el-button type="primary" @click="nextStep">{{
          activeIndex == 10 ? "完成" : "下一步"
        }}</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "SalSob",
  data() {
    return {
        dialogTitle:'添加工资账套',
      activeIndex: 0,
      salaryItemName: [
        "基本工资",
        "交通补助",
        "午餐补助",
        "奖金",
        "养老金比率",
        "养老金基数",
        "医疗保险比率",
        "医疗保险基数",
        "公积金比率",
        "公积金基数",
        "账套名称"
      ],
      salary: {
        basicSalary: 0,
        trafficSalary: 0,
        lunchSalary: 0,
        bonus: 0,
        pensionPer: 0,
        pensionBase: 0,
        medicalPer: 0,
        medicalBase: 0,
        accumulationFundPer: 0,
        accumulationFundBase: 0,
        name: ""
      },
      dialogVisible: false,
      salaries: []
    };
  },
  mounted() {
    this.getAllSalaies();
  },
  methods: {
      showEditSalaryView(data){
          this.dialogTitle='修改工资账套';
          this.dialogVisible=true;
          this.salary.basicSalary = data.basicSalary;
          this.salary.trafficSalary = data.trafficSalary;
          this.salary.lunchSalary = data.lunchSalary;
          this.salary.bonus = data.bonus;
          this.salary.pensionPer = data.pensionPer;
          this.salary.pensionBase = data.pensionBase;
          this.salary.medicalPer = data.medicalPer;
          this.salary.medicalBase = data.medicalBase;
          this.salary.accumulationFundPer = data.accumulationFundPer;
          this.salary.accumulationFundBase = data.accumulationFundBase;
          this.salary.name = data.name;
          this.salary.id = data.id;
      },
      deleteSalary(data){
          this.$confirm('此操作将删除【' + data.name + '】账套，是否继续？', '提示', {
              cancelButtonText: '取消',
              confirmButtonText: '确定'
          }).then(() => {
              this.deleteRequest("/salary/sob/" + data.id).then(resp => {
                  if (resp) {
                      this.getAllSalaies();
                  }
              })
          }).catch(() => {
              this.$message.info("取消删除!");
          })
      },
    preStep() {
      if (this.activeIndex == 0) {
        return;
      } else if (this.activeIndex == 10) {
        //数据初始化

        //关闭对话框
        this.dialogVisible = false;

        return;
      }
      this.activeIndex--;
    },
    nextStep() {
      if (this.activeIndex == 10) {
          if (this.salary.id){  //存在id，为修改
              this.putRequest("/salary/sob/",this.salary).then(resp=>{
                  if (resp){
                      this.getAllSalaies();
                      this.dialogVisible=false;
                      this.activeIndex=0;
                  }
              });
          }else { //没有id，为新增
        this.postRequest("/salary/sob/", this.salary).then(resp => {
          if (resp) {
            this.getAllSalaies();
            this.dialogVisible = false;
            this.activeIndex = 0;
          }
        });
          }
        this.dialogVisible = false;
        return;
      }
      this.activeIndex++;
    },
    showAddSalaryView() {
          this.dialogTitle='添加工资账套';
      this.dialogVisible = true;
        this.activeIndex = 0;
        this.salary = {
            basicSalary: 0,
            trafficSalary: 0,
            lunchSalary: 0,
            bonus: 0,
            pensionPer: 0,
            pensionBase: 0,
            medicalPer: 0,
            medicalBase: 0,
            accumulationFundPer: 0,
            accumulationFundBase: 0,
            name: ""
        };
    },
    getAllSalaies() {
      this.getRequest("/salary/sob/").then(resp => {
        if (resp) {
          this.salaries = resp;
        }
      });
    }
  }
};
</script>

<style scoped></style>
