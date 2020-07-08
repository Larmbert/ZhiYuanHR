<template>
    <div>
        <el-tabs v-model="activeName" type="card">
            <el-tab-pane label="部门管理" name="first"><dept-manage></dept-manage></el-tab-pane>
            <el-tab-pane label="职位管理" name="second"><position-manage></position-manage></el-tab-pane>
            <el-tab-pane label="职称管理" name="third"><positon-level-manage></positon-level-manage></el-tab-pane>
            <el-tab-pane label="奖惩规则" name="fourth"><rorp-roles></rorp-roles></el-tab-pane>
            <el-tab-pane label="权限管理" name="fifth"><autority-manage></autority-manage></el-tab-pane>
        </el-tabs>
    </div>
</template>

<script>
    import autorityManage from '../../components/sys/basic/autorityManage'
    import deptManage from '../../components/sys/basic/deptManage'
    import positionManage from '../../components/sys/basic/positionManage'
    import positonLevelManage from '../../components/sys/basic/positonLevelManage'
    import rorpRoles from '../../components/sys/basic/rorpRoles'
    export default {
        name: "SysBasic",
        data(){
            return {
                activeName: 'first'
            };
        },
        components:{
            autorityManage,
            deptManage,
            positionManage,
            positonLevelManage,
            rorpRoles
        }
    }
</script>

<style scoped>

</style>