<template>
    <div  :style="backgroundDiv">
        <el-form :rules="rules" :model="loginForm" class="loginContainer"
                 v-loading="loading"
                 element-loading-text="拼命登录中"
                 element-loading-spinner="el-icon-loading"
                 ref="loginForm">
            <h3 class="loginTitle">系统登录</h3>
            <el-form-item prop="username">
                <el-input  size="normal" type="text" v-model="loginForm.username" auto-complete="off" placeholder="请输入用户名">
                </el-input>
            </el-form-item>
            <el-form-item prop="password">
                <el-input size="normal" type="password" v-model="loginForm.password" auto-complete="off" placeholder="请输入密码"
                @keydown.enter.native="submitLogin">
                </el-input>
            </el-form-item>
            <el-checkbox class="loginRemember" v-model="checked">记住密码</el-checkbox>
            <el-button size="normal" type="primary" style="width: 100%" @click="submitLogin">登录</el-button>
        </el-form>
    </div>
</template>

<script>
    export default {
        name: "Login",
        data(){
            return {
                loading:false,
                loginForm:{
                    username:'admin',
                    password:'123'
                },
                checked:true,
                rules:{
                    username:[{required:true,message:'请输入用户名！',trigeer:'blur'}],
                    password:[{required:true,message:'请输入密码！',trigeer:'blur'}]

                },
                backgroundDiv: {
                    backgroundImage:'url(' + require('../images/backImage.jpg') + ')',
                    backgroundRepeat:'no-repeat',
                    backgroundSize:'100% 100%',
                    height: '100%',
                    position: 'fixed',
                    width: '100%',
                    margin:0,
                    padding:0
                }
            }
        },
        methods:{
            submitLogin() {
                    this.$refs.loginForm.validate((valid) => {
                        if (valid) {
                            this.loading=true;
                            this.postKerValueRequest('/doLogin',this.loginForm).then(resp=>{
                                if (resp){
                                    this.loading=false;
                                    window.sessionStorage.setItem("user",JSON.stringify(resp.obj));
                                    let path=this.$route.query.redirect;
                                    console.log(path);
                                    this.$router.replace((path=='/'||path==undefined)?'/home':path);
                                }
                            })
                        } else {
                            this.$message.error('请输入完整的用户信息哦！');
                            return false;
                        }
                    });
                }
        }
    }
</script>

<style>
    .loginContainer{
        border-radius: 12px;
        background-clip: padding-box;
        margin: 180px auto;
        width: 360px;
        padding: 20px 35px 20px 35px;
        background: #ceffd7;
        border: 1px solid #ceffd7;
        box-shadow: #6250ff;
    }
    .loginTitle{
        margin: 5px auto 20px auto;
        text-align: center;
        color: #545454;
    }
    .loginRemember {
        margin: 0px 0px 15px 0px;
        text-align: left;
    }
</style>
