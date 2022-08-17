/**
 * 打开选项卡
 * @param url
 * @param name
 * @param id
 */
function showTab(url,name,id){
    let length= $("li[lay-id="+id+"]").length;
    let element= layui.element;
    if(length==0){
        let fullUrl=baseURL+"/"+url;
        let height=$(window).height()-180;
        let page="<iframe style='width: 100%;height:"+height+"px; ' src="+fullUrl+"></iframe>";
        element.tabAdd('menu',{
            title:name,
            content:page,
            id:id
        });
    }
    element.tabChange("menu",id);

}