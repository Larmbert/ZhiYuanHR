<template>
    <div>
        <div style="display: flex;justify-content: center;margin-top: 10px">
            <el-input v-model="keywords" placeholder="通过用户名搜索用户..." prefix-icon="el-icon-search"
                      style="width: 400px;margin-right: 10px" @keydown.enter.native="doSearch"></el-input>
            <el-button icon="el-icon-search" type="primary" @click="doSearch">搜索</el-button>
            <el-button type="primary" icon="el-icon-plus" style="justify-content: flex-end"
                       @click="showAddHrView">添加用户
            </el-button>
        </div>
        <div class="hr-container">
            <el-card class="hr-card" v-for="(hr,index) in hrs" :key="index">
                <div slot="header" class="clearfix">
                    <span>{{hr.name}}</span>
                    <el-button style="float: right; padding: 3px 0;color: red"
                               type="text" icon="el-icon-delete" @click="deleteHr(hr)"></el-button>
                </div>
                <div>
                    <div class="img-container">
                        <img class="userface-img" :src="hr.userface" :alt="hr.name" :title="hr.name">
                    </div>
                    <div class="userinfo-container">
                        <div>用户名：{{hr.name}}</div>
                        <div>手机号码：{{hr.phone}}</div>
                        <div>电话号码：{{hr.telephone}}</div>
                        <div>地址：{{hr.address}}</div>
                        <div>用户状态：
                            <el-switch
                                    v-model="hr.enabled"
                                    @change="updateHr(hr)"
                                    active-text="启用"
                                    active-color="#13ce66"
                                    inactive-color="#ff4949"
                                    inactive-text="禁用">
                            </el-switch>
                        </div>
                        <div>用户角色：
                            <el-tag type="success" style="margin-left: 4px"
                                    v-for="(role,indexr) in hr.roles" :key="indexr">{{role.nameZh}}
                            </el-tag>
                            <el-popover
                                    placement="right"
                                    title="角色列表"
                                    width="200"
                                    @show="showpop(hr)"
                                    @hide="hidepop(hr)"
                                    trigger="click">
                                <el-select v-model="selectedRoles" multiple placeholder="请选择">
                                    <el-option
                                            v-for="(r,indexr) in allRoles"
                                            :key="indexr"
                                            :label="r.nameZh"
                                            :value="r.id">
                                    </el-option>
                                </el-select>
                                <el-button slot="reference" type="text" icon="el-icon-more"
                                           style="margin-left: 4px"></el-button>
                            </el-popover>
                        </div>
                        <div>备注：{{hr.remark}}</div>
                    </div>
                </div>
            </el-card>
            <el-dialog
                    title="添加操作员"
                    :visible.sync="dialogVisible"
                    width="60%" style="font-size: xx-small">
                <div>
                    <el-form :model="addhr"
                             :rules="rules" ref="perForm">
                        <el-row>
                            <el-col :span="8">
                                <el-form-item label="姓名:" prop="name">
                                    <el-input size="mini" style="width: 180px" prefix-icon="el-icon-edit"
                                              v-model="addhr.name" placeholder="请输入姓名"></el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :span="8">
                                <el-form-item label="账户名:" prop="username">
                                    <el-input size="mini" style="width: 180px" prefix-icon="el-icon-edit"
                                              v-model="addhr.username" placeholder="请输入账户名"></el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :span="8">
                                <el-form-item label="手机号:" prop="phone">
                                    <el-input size="mini" style="width: 180px" prefix-icon="el-icon-edit"
                                              v-model="addhr.phone" placeholder="请输入手机号"></el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :span="8">
                                <el-form-item label="电话:" prop="telephone">
                                    <el-input size="mini" style="width: 180px" prefix-icon="el-icon-edit"
                                              v-model="addhr.telephone" placeholder="请输入电话"></el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :span="8">
                                <el-form-item label="地址:" prop="address">
                                    <el-input size="mini" style="width: 180px" prefix-icon="el-icon-edit"
                                              v-model="addhr.address" placeholder="请输入地址"></el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :span="8">
                                <el-form-item label="是否启用:" prop="enabled">
                                    <el-switch
                                            v-model="addhr.enabled"
                                            active-text="启用"
                                            active-color="#13ce66"
                                            inactive-color="#ff4949"
                                            inactive-text="禁用">
                                    </el-switch>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="8">
                                <el-form-item label="密码:" prop="password">
                                    <el-input type="password" size="mini" style="width: 180px" prefix-icon="el-icon-edit"
                                              v-model="addhr.password" placeholder="请输入密码"></el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :span="16">
                                <el-form-item label="头像地址:" prop="userface">
                                    <el-input size="mini" style="width: 300px" prefix-icon="el-icon-edit"
                                              v-model="addhr.userface" placeholder="请输入图像url地址"></el-input>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="16">
                                <el-form-item label="备注:" prop="remark">
                                    <el-input size="mini" style="width: 400px" prefix-icon="el-icon-edit"
                                              v-model="addhr.remark" placeholder="请输入备注"></el-input>
                                </el-form-item>
                            </el-col>
                        </el-row>
                    </el-form>
                </div>
                <span slot="footer" class="dialog-footer">
    <el-button size="small" @click="dialogVisible = false">取 消</el-button>
    <el-button size="small" type="primary" @click="addorEditEmployee">确 定</el-button>
  </span>
            </el-dialog>
        </div>
    </div>
</template>

<script>
    export default {
        name: "SysHr",
        data() {
            return {
                dialogVisible: false,
                keywords: '',
                hrs: [],
                allRoles: [],
                selectedRoles: [],
                addhr: {
                    name: '',
                    phone: '',
                    telephone: '',
                    address: '',
                    enabled: true,
                    username: '',
                    password: '',
                    userface: '',
                    remark: ''
                },
                rules: {
                    name: [{required: true, message: '请输入姓名', trigger: 'blur'}],
                    phone: [{required: true, message: '请输入手机号', trigger: 'blur'}],
                    telephone: [{required: true, message: '请输入电话', trigger: 'blur'}],
                    username: [{required: true, message: '请输入账户名', trigger: 'blur'}],
                    password: [{required: true, message: '请输入密码', trigger: 'blur'}],
                    userface: [{required: true, message: '请输入头像', trigger: 'blur'}]
                },
                emptyAddHr(){
                    this.addhr={
                        name: '',
                        phone: '',
                        telephone: '',
                        address: '',
                        enabled: true,
                        username: '',
                        password: '',
                        userface: '',
                        remark: ''
                    }
                }
            }
        },
mounted() {
            this.initHrs();
},
        methods: {
            addorEditEmployee(){
                this.$refs['perForm'].validate(valid=>{
                    if (valid){
                        this.postRequest("/system/hr/",this.addhr).then(resp=>{
                            console.log(this.addhr);
                            if (resp){
                                this.dialogVisible=false;
                                this.initHrs();
                                this.emptyEmp();
                            }
                        });
                    }
                });
            },
            showAddHrView(){
                    this.dialogVisible = true;
            },
                deleteHr(hr)
                {
                    if (hr.enabled) {
                        this.$message.error("当前用户未停用，不能删除！");
                    } else {
                        this.$confirm('此操作将永久删除【' + hr.name + '】, 是否继续?', '提示', {
                            confirmButtonText: '确定',
                            cancelButtonText: '取消',
                            type: 'warning'
                        }).then(() => {
                            this.deleteRequest("/system/hr/" + hr.id).then(resp => {
                                if (resp) {
                                    this.initHrs();
                                }
                            });
                        }).catch(() => {
                            this.$message({
                                type: 'info',
                                message: '已取消删除'
                            });
                        });
                    }
                }
            ,
                doSearch()
                {
                    this.initHrs();
                }
            ,
                hidepop(hr)
                {
                    let roles = [];
                    Object.assign(roles, hr.roles);
                    let flag = false;
                    if (roles.length != this.selectedRoles.length) {
                        flag = true;
                    } else {
                        for (let i = 0; i < roles.length; i++) {
                            let role = roles[i];
                            for (let j = 0; j < this.selectedRoles.length; j++) {
                                let sr = this.selectedRoles[j];
                                if (role.id == sr) {
                                    roles.splice(i, 1);
                                    i--;
                                    break;
                                }
                            }
                        }
                        if (roles.length != 0) {
                            flag = true;
                        }
                    }
                    if (flag) {
                        let url = '/system/hr/role?hrid=' + hr.id;
                        this.selectedRoles.forEach(sr => {
                            url += '&rids=' + sr;
                        });
                        this.putRequest(url).then(resp => {
                            if (resp) {
                                this.initHrs();
                            }
                        });
                    }
                }
            ,
                showpop(hr)
                {
                    this.initAllRoles();
                    this.selectedRoles = [];
                    let roles = hr.roles;
                    roles.forEach(r => {
                        this.selectedRoles.push(r.id);
                    });
                }
            ,
                initAllRoles()
                {
                    this.getRequest("/system/hr/roles").then(resp => {
                        if (resp) {
                            this.allRoles = resp;
                        }
                    });
                }
            ,
                updateHr(hr)
                {
                    delete hr.roles;
                    this.putRequest("/system/hr/", hr).then(resp => {
                        if (resp) {
                            this.initHrs();
                        }
                    })
                }
            ,
                initHrs()
                {
                    this.getRequest("/system/hr/?keywords=" + this.keywords).then(resp => {
                        if (resp) {
                            this.hrs = resp;
                        }
                    })
                }
            }
        }
</script>

<style>
    .userinfo-container div {
        font-size: 12px;
        color: #409eff;
        margin-top: 2px;
    }

    .userinfo-container {
        margin-top: 20px;
    }

    .img-container {
        width: 100%;
        display: flex;
        justify-content: center;
    }

    .userface-img {
        width: 72px;
        height: 72px;
        border-radius: 72px;
    }

    .hr-container {
        margin-top: 10px;
        display: flex;
        flex-wrap: wrap;
        justify-content: space-around;
    }

    .hr-card {
        width: 350px;
        margin-bottom: 20px;
    }
</style>