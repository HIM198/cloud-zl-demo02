
function getCookie(name){
    var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
    if (arr = document.cookie.match(reg)){
        return (arr[2]);
    }else{
        return null;
    }
}

//axios
function axiosPostRequest(url,data) {
    let token = getCookie("Authorization")
    let result = axios({
        method: 'post',
        url: url,
        data: data,
        headers:{
            'Authorization':token
        }
    }).then(resp=> {
        return resp.data;
    }).catch(error=>{
        return "exception="+error;
    });
    return result;
}

//get
function axiosGetRequest(url) {
    let token = getCookie("Authorization")
    var result = axios({
        method: 'get',
        url: url,
        headers:{
            'Authorization':token
        }
    }).then(function (resp) {
        return resp.data;
    }).catch(function (error) {
        return "exception=" + error;
    });
    return result;
}

axios.interceptors.response.use((response) => {
    return response;
}, function (error) {
    if (401 === error.response.status) {
        alert("会话超时请重新登录!")
        parent.location.href='/html/login.html'
    }
    // else if (404 === error.response.status){
    //     parent.location.href='/html/404.html'
    // }
    // else {
    //     parent.location.href='/html/404.html'
    // }
});
