// const BaseUrl='http://localhot:8080/'
//
// const  apiList={
//     login:'student/login'
// }
//
// const base = function (){
//     let apiKey ={};
//     for (const key in apiList) {
//         apiKey[key] = BaseUrl +apiList[key]
//     }
//     return apiKey;
// }
//
// export default {
//     INTERFACES:base()
// }
/*方法一失败，后续查阅资料*/

/*方法二*/
const globalUrl ='http://localhost:10086/'

export default {
    globalUrl
}