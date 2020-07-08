import axios from 'axios'
import { Message } from 'element-ui';
import router  from "../router";

axios.interceptors.response.use(success =>{
    if(success.status&&success.status==200&&success.data.status==500){
        Message.error({message:success.data.msg})
        return;
    }
    if (success.data.msg){
        Message.success({message:success.data.msg})
    }
    return success.data;
},error => {
    if (error.response.status==504||error.response.status==404){
        Message.error({message:"服务器离家出走了！o(╯□╰)o"})
    }else if (error.response.status==403){
        Message.error({message:"权限不足，请联系管理员！"})
    }else if (error.response.status==401){
        Message.error({message:"请先登录！"});
        router.replace("/");
    }else {
        if (error.response.data.msg){
            Message.error({message:error.response.data.msg})
        }else {
            Message.error({message:"错误已经超出了小的认知！"})
        }
    }
    return;
})

let base='';
export const postKerValueRequest=(url,params)=>{
    return axios({
        method:'post',
        url:`${base}${url}`,
        data:params,
        transformRequest:[function (data) {
            let kvalue='';
            for (let i in data){
                kvalue+=encodeURIComponent(i)+'='+encodeURIComponent(data[i])+'&'
            }
            return kvalue;
        }],
        headers:{
            'Content-Type':'application/x-www-form-urlencoded'
        }
    });
}

export const postRequest=(url,params)=>{
    return axios({
        method:'post',
        url:`${base}${url}`,
        data:params
    })
}

export const putRequest=(url,params)=>{
    return axios({
        method:'put',
        url:`${base}${url}`,
        data:params
    })
}
export const getRequest=(url,params)=>{
    return axios({
        method:'get',
        url:`${base}${url}`,
        data:params
    })
}
export const deleteRequest=(url,params)=>{
    return axios({
        method:'delete',
        url:`${base}${url}`,
        data:params
    })
}