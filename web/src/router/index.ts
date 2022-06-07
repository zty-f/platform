import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'
import Home from '../views/Home.vue'
import Login from '../views/login.vue'
import Register from '../views/register.vue'
import adminTeamInfo from '../views/admin/teamInfo.vue'
import adminStudentInfo from '../views/admin/studentInfo.vue'
import adminTeacherInfo from '../views/admin/teacherInfo.vue'
import teamInfo from '../views/teamInfo.vue'
import updateTeamInfo from '../views/updateTeamInfo.vue'
import createTeam from '../views/createTeam.vue'
import uploadFile from '../views/student/uploadFile.vue'

const routes: Array<RouteRecordRaw> = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/register',
    name: 'Register',
    component: Register
  },

  {
    path: '/admin/teamInfo',
    name: 'adminTeamInfo',
    component: adminTeamInfo
  },
  {
    path: '/admin/studentInfo',
    name: 'adminStudentInfo',
    component: adminStudentInfo
  },
  {
    path: '/admin/teacherInfo',
    name: 'adminTeacherInfo',
    component: adminTeacherInfo
  },
  {
    path: '/teamInfo',
    name: 'teamInfo',
    component: teamInfo
  },
  {
    path: '/createTeam',
    name: 'createTeam',
    component: createTeam
  },
  {
    path: '/updateTeamInfo',
    name: 'updateTeamInfo',
    component: updateTeamInfo
  },
  {
    path: '/student/uploadFile',
    name: 'uploadFile',
    component: uploadFile
  },
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router;
