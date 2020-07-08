<template>
    <div>
        <div>
            <el-input class="posiInput"
                      placeholder="请输入职位" size="small"
                      prefix-icon="el-icon-plus"
                      @keydown.enter.native="addPosition"
                      v-model="pos.name">
            </el-input>
            <el-button type="primary" size="small" style="width: 100px; font-size: 13px"
                       @click="addPosition()">
                添加
            </el-button>
            <el-button type="danger" size="small" style="margin-left: 30px;width: 100px; font-size: 13px"
                       @click="deletePositions" :disabled="multipleSelection.length==0">
                批量删除
            </el-button>
        </div>
        <div class="posiMain">
            <el-table
                    :data="positions"
                    border
                    stripe
                    @selection-change="handleSelectionChange"
                    height="450"
                    style="width: 100%"
                    size="small">
                <el-table-column
                        type="selection"
                        width="55">
                </el-table-column>
                <el-table-column
                        prop="id"
                        label="编号"
                        width="180px">
                </el-table-column>
                <el-table-column
                        prop="name"
                        label="职位名称"
                        width="200px">
                </el-table-column>
                <el-table-column
                        prop="createDate"
                        label="创建时间"
                        width="200px">
                </el-table-column>
                <el-table-column
                        label="是否启用"
                        width="200px">
                    <template slot-scope="scope">
                        <el-tag type="success" v-if="scope.row.enabled">启用</el-tag>
                        <el-tag type="danger" v-else>停用</el-tag>
                    </template>
                </el-table-column>
                <el-table-column label="操作">
                    <template slot-scope="scope">
                        <el-button
                                size="mini"
                                @click="showUpdatePositontable(scope.$index, scope.row)">编辑
                        </el-button>
                        <el-button
                                size="mini"
                                type="danger"
                                @click="handleDelete(scope.$index, scope.row)">删除
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>
            <el-dialog
                    title="修改职位名称"
                    :visible.sync="dialogVisible"
                    width="30%">
                <div>
                    <el-tag size="small">职位信息</el-tag>
                    <el-input class="updateInput" v-model="updatePos.name" size="small"></el-input>
                </div>
                <div style="margin-top: 10px">
                    <el-tag size="small">是否启用</el-tag>
                    <el-switch
                            v-model="updatePos.enabled"
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
        name: "positionManage",
        data() {
            return {
                pos: {
                    name: ''
                },
                positions: [],
                updatePos:{
                    name:'',
                    enabled:false
                },
                multipleSelection:[],
                dialogVisible:false
            }
        },
        mounted() {
            this.initPositions();
        },
        methods: {
            deletePositions(){
                this.$confirm('此操作将永久删除' +this.multipleSelection.length+ '条职位信息, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    let ids = '?';
                    this.multipleSelection.forEach(item => {
                        ids += 'ids=' + item.id + '&';
                    });
                    this.deleteRequest("/system/basic/pos/"+ids).then(resp=>{
                        if (resp){
                            this.initPositions();
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
              this.putRequest("/system/basic/pos/",this.updatePos).then(resp=>{
                  if (resp){
                      this.initPositions();
                      this.updatePos.name='';
                      this.dialogVisible=false;
                  }
                })
            },
            addPosition() {
                if (this.pos.name) {
                    this.postRequest("/system/basic/pos/", this.pos).then(resp => {
                        if (resp) {
                            this.initPositions();
                            this.pos.name = '';
                        }
                    })
                } else {
                    this.$message.error("请先输入职位名称！");
                }
            },
            showUpdatePositontable(index, data) {
                Object.assign(this.updatePos,data);
                this.dialogVisible=true;
            },
            handleDelete(index, data) {
                this.$confirm('此操作将永久删除' + data.name + '职位, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.deleteRequest("/system/basic/pos/" + data.id).then(resp => {
                        if (resp) {
                            this.initPositions();
                        }
                    })
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });
            },
            initPositions() {
                this.getRequest("/system/basic/pos/").then(resp => {
                    if (resp) {
                        this.positions = resp;
                    }
                })
            }
        }
    }
</script>

<style>
    .updateInput{
        width: 200px;
        margin-left: 10px;
    }
    .posiInput {
        margin-right: 10px;
        width: 400px;
    }
    .posiMain {
        margin-top: 10px;
    }
</style>