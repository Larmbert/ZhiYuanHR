<template>
    <div>
        <el-container>
            <el-header class="homeHeader">
                <div class="title">致远人事</div>
                <div>
                    <el-button icon="el-icon-bell" size="normal" style="margin-right:8px;color: #000000"
                               type="text" @click="GoWeChat()"></el-button>
                    <el-dropdown class="userwelcome" @command="commandHandler">
                   <span class="el-dropdown-link">
                       你好，{{user.name}}！<i><img :src="user.userface" alt=""></i>
                   </span>
                        <el-dropdown-menu slot="dropdown">
                            <el-dropdown-item command="userinfo">个人中心</el-dropdown-item>
                            <el-dropdown-item command="setting">设置</el-dropdown-item>
                            <el-dropdown-item command="logout" divided>注销登录</el-dropdown-item>
                        </el-dropdown-menu>
                    </el-dropdown>
                </div>
            </el-header>
            <el-container>
                <el-aside width="200px">
                    <el-menu
                            class="el-menu-vertical-demo"
                            active-text-color="#ffd04b" router unique-opened>
                        <el-submenu :index="index+''" v-for="(item,index) in routes" v-if="!item.hidden" :key="index">
                            <template slot="title">
                                <i style="color: #a28dfe;margin-right: 5px" :class="item.iconCls"></i>
                                <span>{{item.name}}</span>
                            </template>
                            <el-menu-item :index="child.path" v-for="(child,indexc) in item.children" :key="indexc">
                                {{child.name}}
                            </el-menu-item>

                        </el-submenu>
                    </el-menu>
                </el-aside>
                <el-main>
                    <el-breadcrumb separator-class="el-icon-arrow-right" v-if="this.$router.currentRoute.path!='/home'">
                        <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
                        <el-breadcrumb-item>{{this.$router.currentRoute.name}}</el-breadcrumb-item>
                    </el-breadcrumb>
                    <div class="homeWelcome" v-if="this.$router.currentRoute.path=='/home'">欢迎来到致远人事！</div>
                   <router-view class="homeTop"/>
                </el-main>
            </el-container>
        </el-container>
    </div>
</template>

<script>
    export default {
        name: "Home",
        data(){
            return{
                user:JSON.parse(window.sessionStorage.getItem("user"))
            }
        },
    computed:{
            routes(){
                return this.$store.state.routes;
            }
    },
        methods:{
            GoWeChat(){
                this.$router.push("/chat");
            },
            commandHandler(sel){
               if (sel=='logout'){
                   this.$confirm('此操作将注销用户, 是否继续?', '提示', {
                       confirmButtonText: '确定',
                       cancelButtonText: '取消',
                       type: 'warning'
                   }).then(() => {
                      this.getRequest("/logout");
                      window.sessionStorage.removeItem("user");
                       this.$store.commit('initRoutes',[]);
                      this.$router.replace("/");
                   }).catch(() => {
                       this.$message({
                           type: 'info',
                           message: '账户已经注销！'
                       });
                   });
               }
            }
        }
    }
</script>

<style>
    .homeTop{
        margin-top: 10px;
    }
    .homeWelcome{
        text-align: center;
        font-size: 40px;
        font-family: 华文行楷;
        color: #4682fe;
        padding-top: 40px;
    }
    .homeHeader {
        background-color: #69e5fe;
        display: flex;
        align-items: center;
        justify-content: space-between;
        padding: 0px 15px;
        box-sizing: border-box;
    }

    .homeHeader .title {
        font-size: 30px;
        font-family: 华文隶书;
        color: #fdfcfe;
    }
    .homeHeader .userwelcome{
        cursor: pointer;
    }
    .el-dropdown-link img{
        width: 48px;
        height: 48px;
        border-radius: 24px;
        margin-left: 8px;
    }
    .el-dropdown-link{
        display: flex;
        align-items: center;
    }
</style>