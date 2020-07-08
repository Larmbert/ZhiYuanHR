<template>
    <div>
        <div class="authorityInput">
            <el-input size="small" placeholder="请输入角色英文名" v-model="role.name">
                <template slot="prepend">ROLE_</template>
            </el-input>
            <el-input size="small" placeholder="请输入角色中文名"
                      v-model="role.nameZh"
                      @keydown.enter.native="doAddRole">
            </el-input>
            <el-button style="margin-left: 10px" type="primary" size="small"
                       icon="el-icon-plus"
                       @click="doAddRole">添加角色</el-button>
        </div>
        <div class="authorityCollapse">
            <el-collapse  accordion v-model="activeName" @change="change">
                <el-collapse-item :title="r.nameZh" :name="r.id"  v-for="(r,index) in roles" :key="index">
                    <el-card class="box-card">
                        <div slot="header" class="clearfix">
                            <span>可访问资源</span>
                            <el-button style="float: right; padding: 3px 0;color: #ff0000;"
                                       icon="el-icon-delete" type="text" @click="doDeleteRole(r)"></el-button>
                        </div>
                        <div>
                            <el-tree ref="tree" show-checkbox node-key="id"
                                     :key="index"
                                     :default-checked-keys="selectedMenus"
                                     :data="allMenus" :props="defaultProps"></el-tree>
                            <div style="display: flex;justify-content: flex-end">
                                <el-button size="mini" @click="cancelUpdate">取消修改</el-button>
                                <el-button size="mini" type="primary" @click="doUpdate(r.id,index)">确认修改</el-button>
                            </div>
                        </div>
                    </el-card>
                </el-collapse-item>
            </el-collapse>
        </div>
    </div>
</template>

<script>
    export default {
        name: "autorityManage",
        data(){
            return{
                role:{
                    name:'',
                    nameZh:''
                },
                allMenus:[],
                selectedMenus:[],
                activeName:-1,
                defaultProps: {
                    children: 'children',
                    label: 'name'
                },
                roles:[]
            }
        },
        mounted() {
            this.initRoles();
        },
        methods:{
            doDeleteRole(role){
                this.$confirm('此操作将永久删除' +role.nameZh+ '角色, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.deleteRequest("/system/basic/permiss/role/"+role.id).then(resp=>{
                        if (resp){
                            this.initRoles();
                        }
                    });
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });
            },
            doAddRole(){
                if (this.role.name&&this.role.nameZh){
                    this.postRequest("/system/basic/permiss/role",this.role).then(resp=>{
                       if (resp){
                           this.role.nameZh='';
                           this.role.name='';
                           this.initRoles();
                           this.activeName=-1;
                       }
                    });
                }else {
                    this.$message.error("角色名称不能为空！");
                }
            },
            cancelUpdate(){
                this.activeName=-1;
            },
            doUpdate(rid,index){
                let tree = this.$refs.tree[index];
                let selectedKeys = tree.getCheckedKeys(true);
                let url='/system/basic/permiss/?rid='+rid;
                selectedKeys.forEach(key=>{
                    url+='&mids='+key;
                })
                this.putRequest(url).then(resp=>{
                    if (resp){
                        this.activeName=-1;
                    }
                })
            },
            initSelectedMenus(rid){
                this.getRequest("/system/basic/permiss/mids/"+rid).then(resp=>{
                    if (resp){
                        this.selectedMenus=resp;
                    }
                })
            },
            initAllmenus(){
              this.getRequest("/system/basic/permiss/menu").then(resp=>{
                  if (resp){
                      this.allMenus=resp;
                  }
              })
            },
            change(rid){
                if (rid){
                   this.initAllmenus();
                   this.initSelectedMenus(rid);
                }
            },
            initRoles(){
                this.getRequest("/system/basic/permiss/").then(resp=>{
                   if (resp){
                       this.roles=resp;
                   }
                })
            }
        },
    }
</script>

<style>
    .authorityInput{
        display: flex;
        justify-content: flex-start;
    }
    .authorityInput .el-input{
        width: 300px;
        margin-left: 6px;
    }
    .authorityCollapse{
        margin-top: 10px;
        width: 800px;
    }

</style>