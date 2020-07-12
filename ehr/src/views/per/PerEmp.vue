<template>
    <div style="margin-top: 10px">
        <el-table
                v-loading="loading"
                element-loading-text="拼命加载中"
                element-loading-spinner="el-icon-loading"
                element-loading-background="rgba(0, 0, 0, 0.8)"
                :data="emps"
                stripe
                style="width: 100%">
<!--                @selection-change="handleSelectionChange">-->
<!--            <el-table-column-->
<!--                    type="selection"-->
<!--                    width="55">-->
<!--            </el-table-column>-->
            <el-table-column
                    prop="name"
                    fixed
                    label="姓名"
                    width="90">
            </el-table-column>
            <el-table-column
                    prop="workID"
                    label="工号"
                    width="100">
            </el-table-column>
            <el-table-column
                    prop="gender"
                    label="性别">
            </el-table-column>
            <el-table-column
                    prop="birthday"
                    label="出生日期"
                    width="100">
            </el-table-column>
            <el-table-column
                    prop="idCard"
                    label="身份证号"
                    width="150">
            </el-table-column>
            <el-table-column
                    prop="wedlock"
                    label="婚姻状况">
            </el-table-column>
            <el-table-column
                    prop="nation.name"
                    label="民族">
            </el-table-column>
            <el-table-column
                    prop="nativePlace"
                    label="籍贯">
            </el-table-column>
            <el-table-column
                    prop="politicsstatus.name"
                    label="政治面貌">
            </el-table-column>
            <el-table-column
                    prop="email"
                    label="电子邮件"
                    width="150">
            </el-table-column>
            <el-table-column
                    prop="phone"
                    label="电话号码"
                    width="100">
            </el-table-column>
            <el-table-column
                    prop="address"
                    label="联系地址"
                    width="180">
            </el-table-column>
            <el-table-column
                    prop="tiptopDegree"
                    label="最高学历">
            </el-table-column>
            <el-table-column
                    prop="school"
                    label="毕业院校">
            </el-table-column>
            <el-table-column
                    prop="department.name"
                    label="所属部门"
                    width=" 160px">
            </el-table-column>
            <el-table-column
                    prop="position.name"
                    label="职位">
            </el-table-column>
            <el-table-column
                    prop="jobLevel.name"
                    label="职称"
                    width="100">
            </el-table-column>
            <el-table-column
                    prop="engageForm"
                    label="聘用形式">
            </el-table-column>
            <el-table-column
                    prop="beginDate"
                    label="入职日期"
                    width="100">
            </el-table-column>
            <el-table-column
                    prop="conversionTime"
                    label="转正日期"
                    width="100">
            </el-table-column>
            <el-table-column
                    prop="beginContract"
                    label="合同起始日期"
                    width="100">
            </el-table-column>
            <el-table-column
                    prop="endContract"
                    label="合同截止日期"
                    width="100">
            </el-table-column>
            <el-table-column
                    label="合同期限">
                <template slot-scope="scope">
                    <el-tag>{{scope.row.contractTerm}}</el-tag>
                    年
                </template>
            </el-table-column>
        </el-table>
        <div>
            <el-pagination
                    background
                    @current-change="currentChange"
                    @size-change="sizeChange"
                    style="display:flex;justify-content: flex-end"
                    layout="sizes, prev, pager, next, jumper, ->, total, slot"
                    :total="total">
            </el-pagination>
        </div>
    </div>
</template>

<script>
    export default {
        name: "PerEmp",
        data(){
            return{
                emps: [],
                loading: false,
                total: 0,
                page: 1,
                size: 10
            }
        },
        mounted() {
            this.initPersonals();
        },
        methods:{
            sizeChange(currentsize) {
                this.size = currentsize;
                this.initPersonals();
            },
            currentChange(currentpage) {
                this.page = currentpage;
                this.initPersonals();
            },
            initPersonals(){
                this.loading=true;
                this.getRequest("/personnel/emp/?page="+this.page+"&size"+this.size).then(resp=>{
                    this.loading=false;
                    if (resp){
                      this.emps=resp.data;
                      this.total=resp.total;
                    };
                });
            }
        }
    }
</script>

<style>
    /* 可以设置不同的进入和离开动画 */
    /* 设置持续时间和动画函数 */
    .slide-fade-enter-active {
        transition: all .5s ease;
    }

    .slide-fade-leave-active {
        transition: all .5s cubic-bezier(1.0, 0.5, 0.8, 1.0);
    }

    .slide-fade-enter, .slide-fade-leave-to
        /* .slide-fade-leave-active for below version 2.1.8 */
    {
        transform: translateX(10px);
        opacity: 0;
    }
</style>