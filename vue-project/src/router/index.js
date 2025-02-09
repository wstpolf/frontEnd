import  {createWebHashHistory} from 'vue-router';
import { createRouter, createWebHistory } from 'vue-router';  // Vue 3 语法

import Login from '../components/logAndSign/Login.vue'
import SignUp from "../components/logAndSign/SignUp.vue";
import StudentContainer from "../components/student/StuContainer.vue";
import StuMyClass from "../components/student/StuMyClass.vue";
import StuAllClass from "../components/student/StuAllClass.vue";
import StuMyGrade from "../components/student/StuMyGrade.vue";
import StuMyAssignment from "../components/student/StuMyAssignment.vue";
import StuMyNotify from "../components/student/StuMyNotify.vue";
import StuMyInform from "../components/student/StuMyInform.vue";
import TeContainer from "../components/teacher/TeContainer.vue";
import TeMyTeach from "../components/teacher/TeMyTeach.vue";
import TeMyInform from "../components/teacher/TeMyInform.vue";
import TeNotify from "../components/teacher/TeNotify.vue";
import TeGrade from "../components/teacher/TeGrade.vue";
import TeAssign from "../components/teacher/TeAssign.vue";
import PaContainer from "../components/parent/PaContainer.vue";
import PaMyClass from "../components/parent/PaMyClass.vue";
import PaMyInform from "../components/parent/PaMyInform.vue";
import PaAllClass from "../components/parent/PaAllClass.vue";
import PaGrade from "../components/parent/PaGrade.vue";
import PaNotify from "../components/parent/PaNotify.vue";
import AdContainer from "../components/admin/AdContainer.vue";
import AdAccount from "../components/admin/AdAccount.vue";
const routes = [
    {path: '/',component: Login},
    {path: '/signUp',component: SignUp},
    {
        path: '/studentContainer',
        component: StudentContainer,
        redirect:'/studentContainer/myClass',
        children:[
            {
                path: 'myClass',
                component: StuMyClass
            },
            {
                path: 'allClass',
                component: StuAllClass
            },
            {
                path: 'myGrade',
                component: StuMyGrade
            },
            {
                path: 'myAssign',
                component: StuMyAssignment
            },
            {
                path: 'myNotify',
                component: StuMyNotify
            },
            {
                path: 'myInform',
                component: StuMyInform
            }
        ]
    },
    {
        path: '/teContainer',
        component: TeContainer,
        redirect: '/teContainer/myTeach',
        children: [
            {
                path: 'myTeach',
                component: TeMyTeach
            },
            {
                path: 'myNotify',
                component: TeNotify
            },
            {
                path: 'grade',
                component: TeGrade
            },
            {
                path: 'assign',
                component:TeAssign
            },
            {
                path: 'myInform',
                component: TeMyInform
            }
        ]
    },
    {
        path: '/PaContainer',
        component: PaContainer,
        redirect: 'PaContainer/myClass',
        children: [
            {
                path: 'myInform',
                component: PaMyInform
            },
            {
                path: 'myClass',
                component: PaMyClass
            },
            {
                path: 'allClass',
                component: PaAllClass
            },
            {
                path: 'grade',
                component: PaGrade
            },
            {
                path: 'notify',
                component: PaNotify
            }
        ]
    },
    {
        path: '/AdContainer',
        component: AdContainer,
        redirect: '/AdContainer/account',
        children: [
            {
                path: 'account',
                component: AdAccount
            }
        ]
    }
]

const router = createRouter({
    history : createWebHashHistory(),
    routes
})
export default router