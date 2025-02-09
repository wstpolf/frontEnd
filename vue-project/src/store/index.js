import {createStore} from "vuex";
import axios from "axios";
export default createStore({
    state:{
        isLoggedIn:false,
        userInform:{
            id:null,
            username:'',
            phone:'',
            role:''
        },
        stuInform:{
            id:null,
            name:'',
            grade:'',
            parentPhone:'',
        },
        childInform:{
            id:null,
            name:'',
            grade:'',
            parentPhone:'',
        },
        teInform:{
            id:null,
            name:'',
            subject:'',
            experience:null
        }
    },
    mutations:{
        setStuData(state,data){
            state.stuInform = data
        },
        setTeData(state,data){
            state.teInform = data
        }
    },
    actions:{
        setStuData({commit},data){
            commit('setStuData',data)
        },
        setTeData({commit},data){
            commit('setTeData',data)
        }
    },
    getters:{
        //获取学生id
        getStuId(state){
            return state.stuInform.stuId
        },
        getUserInform(state){
            return state.userInform
        }
    }
})