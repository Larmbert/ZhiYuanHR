<template>
    <div>
        <div>
            <el-input class="jobLevelInput"
                      placeholder="请输入职位" size="small"
                      prefix-icon="el-icon-plus" v-model="positionlevelmanage.name">
            </el-input>
            <el-select v-model="positionlevelmanage.titleLevel" placeholder="职位等级" size="small">
                <el-option
                        v-for="item in titleLevels"
                        :key="item"
                        :label="item"
                        :value="item">
                </el-option>
            </el-select>
            <el-button type="primary" size="small" style="margin-left:10px; width: 100px; font-size: 13px"
                         @click="addJobLevel">
                添加
            </el-button>
            <el-button type="danger" size="small" style="margin-left: 30px;width: 100px; font-size: 13px"
                        :disabled="multipleSelection.length==0" @click="deleteJoblevels">
                批量删除
            </el-button>
        </div>
        <div>
            <el-table
                    :data="jls"
                    border
                    stripe
                    @selection-change="handleSelectionChange"
                    height="450"
                    style="width: 100%; margin-top: 10px"
                    size="small">
                <el-table-column
                        type="selection"
                        width="55">
                </el-table-column>
                <el-table-column
                        prop="id"
                        label="编号"
                        width="130px">
                </el-table-column>
                <el-table-column
                        prop="name"
                        label="职称名称"
                        width="200px">
                </el-table-column>
                <el-table-column
                        prop="titleLevel"
                        label="职称级别"
                        width="200px">
                </el-table-column>
                <el-table-column
                        prop="createDate"
                        label="创建时间"
                        width="200px">
                </el-table-column>
                <el-table-column
                        label="是否启用"
                        width="150px">
                    <template slot-scope="scope">
                        <el-tag type="success" v-if="scope.row.enabled">启用</el-tag>
                        <el-tag type="danger" v-else>停用</el-tag>
                    </template>
                </el-table-column>
                <el-table-column label="操作">
                    <template slot-scope="scope">
                        <el-button
                                size="mini"
                                @click="showUpdatejoblevel(scope.row)">编辑
                        </el-button>
                        <el-button
                                size="mini"
                                type="danger"
                                @click="handleDelete(scope.row)">删除
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>
            <el-dialog
                    title="修改职位名称"
                    :visible.sync="dialogVisible"
                    width="30%">
                <div>
                    <el-tag>职称名称</el-tag>
                    <el-input  v-model="updateJob.name" size="small" style="margin-left: 10px;height: 30px;width: 200px"></el-input>
                </div>
                <div style="margin-top: 10px">
                    <el-tag>职称级别</el-tag>
                    <el-select v-model="updateJob.titleLevel"  size="small" style="margin-left: 10px;height: 30px;width: 200px">
                        <el-option
                                v-for="item in titleLevels"
                                :key="item"
                                :label="item"
                                :value="item">
                        </el-option>
                    </el-select>
                </div>
                <div style="margin-top: 10px">
                    <el-tag>是否启用</el-tag>
                    <el-switch
                            v-model="updateJob.enabled"
                            active-text="启用"
                            inactive-text="停用"
                            style="margin-left: 10px;height: 30px">
                    </el-switch>
                </div>
                <span slot="footer" class="dialog-footer">
    <el-button size="small" @click="dialogVisible = false">取 消</el-button>
    <el-button size="small" type="primary" @click="doUpdate">确 定</el-button>
                </span>
            </el-dialog>
        </div>
    </div>
</template>

<script>
    export default {
        name: "positonLevelManage",
        data(){
           return {
               dialogVisible:false,
               multipleSelection:[],
               positionlevelmanage:{
                   name:'',
                   titleLevel:''
               },
               jls:[],
               updateJob:{
                   name:'',
                   titleLevel:'',
                   enabled:false
               },
               titleLevels:[
                   '正高级',
                   '副高级',
                   '中级',
                   '初级',
                   '员级'
               ]
           }
        },
        mounted() {
            this.initJoblevels();
        },
        methods:{
            deleteJoblevels(){
                this.$confirm('此操作将永久删除' + this.multipleSelection.length + '职称, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    let ids='?';
                    this.multipleSelection.forEach(item=>{
                        ids+='ids='+item.id+'&'
                    });
                    this.deleteRequest("/system/basic/joblevel/" + ids).then(resp => {
                        if (resp) {
                            this.initJoblevels();
                        }
                    })
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });
            },
            handleSelectionChange(val){
                this.multipleSelection=val;
            },
            doUpdate(){
                this.putRequest("/system/basic/joblevel/",this.updateJob).then(resp=>{
                    if (resp){
                        this.initJoblevels();
                        this.dialogVisible=false;
                    }
                })
            },
            showUpdatejoblevel(data){
                this.dialogVisible=true;
                Object.assign(this.updateJob,data)
            },
            handleDelete(data){
                this.$confirm('此操作将永久删除' + data.name + '职称, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.deleteRequest("/system/basic/joblevel/" + data.id).then(resp => {
                        if (resp) {
                            this.initJoblevels();
                        }
                    })
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });
            },
            addJobLevel(){
                if (this.positionlevelmanage.name&&this.positionlevelmanage.titleLevel){
                    this.postRequest("/system/basic/joblevel/",this.positionlevelmanage).then(resp=>{
                        if (resp){
                            this.initJoblevels();
                        }
                    })
                }else {
                    this.$message.error("职位名称和职位等级不能为空!");
                }

            },
            initJoblevels() {
                this.getRequest("/system/basic/joblevel/").then(resp=>{
                    if (resp){
                        this.jls=resp;
                        this.positionlevelmanage={
                            name:'',
                            titleLevel: ''
                        }
                    }
                })
            }
        }
    }
</script>

<style scoped>
    .jobLevelInput {
        margin-right: 10px;
        width: 400px;
    }
</style>