let tableIns;
layui.use('table', function(){
    let table = layui.table;

    //第一个实例
    tableIns=  table.render({
        elem: '#roleList'
        ,height: 450
        ,url: baseURL+'/role/list' //数据接口
        ,page: true //开启分页
        ,parseData:function(res){
            return {
                "code":res.code,  //状态码
                "msg":res.msg,  //提示信息
                "count":res.data.count,  //数据总数
                "data":res.data.records //解析数据列表
            }
        }
        ,cols: [[ //表头
            {field: 'roleId', title: '角色ID'}
            ,{field: 'roleName', title: '角色名称'}
            ,{field: 'createTime', title: '创建时间'}
            ,{field: 'remark', title: '备注'}
            ,{title: '操作', toolbar: '#barDemo'}
        ]]
    });

    //工具条事件
    // table.on('tool(roleTable)', function(obj){ //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
    //     console.log(obj);
    //     let data = obj.data; //获得当前行数据
    //     let layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
    //     let tr = obj.tr; //获得当前行 tr 的 DOM 对象（如果有的话）
    //     let customerId=data.customerId;
    //
    //
    //     if(layEvent === 'detail'){ //查看
    //         openLayer(baseURL+'/customer/toDetail/'+customerId,'客户详情');
    //     } else if(layEvent === 'del'){ //删除
    //         layer.confirm('真的删除行么', function(index){
    //             //obj.del(); //删除对应行（tr）的DOM结构，并更新缓存
    //             deleteData(baseURL+"/customer/doDelete/"+customerId);
    //             layer.close(index);
    //             //向服务端发送删除指令
    //         });
    //     } else if(layEvent === 'edit'){ //编辑
    //         openLayer(baseURL+'/customer/toUpdate/'+customerId,'修改客户信息');
    //         submitData('updateSubmit','post');
    //     }
    // });

});

/**
 * 查询按钮事件
 */
function query(){
    tableIns.reload({
        where:{
            roleName:$("#roleName").val()
        },
        page: {
            curr:1
        }
    })
}

/**
 * 跳转到新增页面
 */
function toAddPage(){
    openLayer(baseURL+'/role/toAdd','新增角色');
    //初始化树形组件
    initTree(baseURL+'/role/listResource','resource');

    submitData('addSubmit','post',handleSelectedTreeIds);
}

/**
 * 树封装
 * @param url
 * @param elemId
 */
function initTree(url,elemId){
    $.ajax({
        url: url,
        async:false,
        type:"get",
        success:function (res){
            //console.log(res);
            if(res.code==0){
                layui.tree.render({
                    elem:'#'+elemId,
                    data:res.data,
                    id:elemId,
                    showCheckbox:true
                })
            }
        }
    });
}

/**
 * 打开弹出成层封装
 * @param url
 * @param title
 */
function openLayer(url,title){
    $.ajaxSettings.async=false;
    $.get(url,function (pageContent) {
        layui.layer.open({
            type: 1,
            title: title,
            area: ['800px', '450px'],
            content: pageContent
        });
    });
    $.ajaxSettings.async=true;
}

/**
 * 处理表单提交封装
 * @param filterName
 * @param requestType
 */
function submitData(filterName,requestType,func){
    layui.form.render();
    layui.form.on('submit('+filterName+')',function (data){
        //传递动态处理数据方法
        if(typeof (func)!='undefined'){
            func(data.field);
        }
        // console.log(data);
        $.ajax({
            url: data.form.action,
            async:false,
            type:requestType,
            contentType:"application/json;charset=utf-8",
            data:JSON.stringify(data.field),
            success:function (res){
                if(res.code==0){
                    layui.layer.closeAll();
                    query();
                }else {
                    layui.layer.alert(res.msg);
                }
            }
        });
        //阻止表单提交
        return false;
    });
}

/**
 * 讲选中的的资源树ID统一保存到新变量中
 * @param field
 */
function handleSelectedTreeIds(field){
   let checkedDataArr= layui.tree.getChecked('resource');
   field.resourceIds=getIdFromCheckedArr(checkedDataArr,[]);
}

function getIdFromCheckedArr(checkedDataArr,idArr){
    for(let index in checkedDataArr){
        idArr.push(checkedDataArr[index].id);
        //递归处理子数据
        idArr=getIdFromCheckedArr(checkedDataArr[index].children,idArr);
    }
    console.log(idArr);
    return idArr;
}

