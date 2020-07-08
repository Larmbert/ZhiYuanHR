<template>
    <div style="width: 600px">
        <el-input
                placeholder="输入部门关键字进行过滤"
                prefix-icon="el-icon-search"
                v-model="filterText">
        </el-input>
        <el-tree
                :data="deptments"
                :props="defaultProps"
                :filter-node-method="filterNode"
                :expand-on-click-node="false"
                ref="tree">
            <span class="custom-tree-node" style="display: flex;justify-content: space-between;width: 100%" slot-scope="{ node, data }">
        <span>{{ data.name }}</span>
        <span>
          <el-button
                  type="primary"
                  size="mini"
                  class="depBtn"
                  @click="() => showAddDeptView(data)">
            添加
          </el-button>
          <el-button
                  type="danger"
                  size="mini"
                  class="depBtn"
                  @click="() => deleteDept(data)">
            删除
          </el-button>
        </span>
      </span>
        </el-tree>
        <el-dialog
                title="添加部门"
                :visible.sync="dialogVisible"
                width="30%">
            <div>
                <table>
                    <tr>
                        <td>
                            <el-tag>上级部门</el-tag>
                        </td>
                        <td>{{pname}}</td>
                    </tr>
                    <tr>
                        <td>
                            <el-tag>部门名称</el-tag>
                        </td>
                        <td>
                            <el-input v-model="dep.name" placeholder="请输入部门名称..."></el-input>
                        </td>
                    </tr>
                </table>
            </div>
            <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="doAddDep">确 定</el-button>
  </span>
        </el-dialog>
    </div>
</template>

<script>
    export default {
        name: "deptManage",
        data(){
            return{
                dialogVisible: false,
                dep: {
                    name: '',
                    parentId: -1
                },
                pname: '',
                filterText:'',
                deptments:[],
                defaultProps: {
                    children: 'children',
                    label: 'name'
                }
            }
        },
        watch: {
            filterText(val) {
                this.$refs.tree.filter(val);
            }
        },
        mounted() {
            this.initDept();
        },
        methods:{
            initDep() {
                this.dep = {
                    name: '',
                    parentId: -1
                }
                this.pname = '';
            },
            addDep2Deps(deps, dep) {
                for (let i = 0; i < deps.length; i++) {
                    let d = deps[i];
                    if (d.id == dep.parentId) {
                        d.children = d.children.concat(dep);
                        if (d.children.length > 0) {
                            d.parent = true;
                        }
                        return;
                    } else {
                        this.addDep2Deps(d.children, dep);
                    }
                }
            },
            doAddDep(){
                this.postRequest("/system/basic/department/", this.dep).then(resp => {
                    if (resp) {
                        this.addDep2Deps(this.deptments, resp.obj);
                        this.dialogVisible = false;
                        //初始化变量
                        this.initDep();
                    }
                })
            },
            showAddDeptView(data){
                this.pname = data.name;
                this.dep.parentId = data.id;
                this.dialogVisible = true;
            },
            removeDepFromDeps(p,deptments, id) {
                for(let i=0;i<deptments.length;i++){
                    let d = deptments[i];
                    if (d.id == id) {
                        deptments.splice(i, 1);
                        if (deptments.length == 0) {
                            p.parent = false;
                        }
                        return;
                    }else{
                        this.removeDepFromDeps(d,d.children, id);
                    }
                }
            },
            deleteDept(data){
                if (data.parent){
                    this.$message.error("此部门为父部门，不能删除！");
                }else {
                    this.$confirm('此操作将永久删除' + data.name + '部门, 是否继续?', '提示', {
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        type: 'warning'
                    }).then(() => {
                        this.deleteRequest("/system/basic/department/"+data.id).then(resp=>{
                            if (resp) {
                                this.removeDepFromDeps(null,this.deptments,data.id);
                            }
                        })
                    }).catch(() => {
                        this.$message({
                            type: 'info',
                            message: '已取消删除'
                        });
                    });
                }
            },
            initDept(){
                this.getRequest("/system/basic/department/").then(resp=>{
                   if (resp){
                       this.deptments=resp;
                   }
                });
            },
            filterNode(value, data) {
                if (!value) return true;
                return data.name.indexOf(value) !== -1;
            }
        }
    }
</script>

<style>
    .depBtn {
        padding: 2px;
    }
</style>