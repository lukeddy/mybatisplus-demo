//初始化日期组件
layui.laydate.render({
    elem:'#createTimeRange',
    range:true,
});

let tableIns;
layui.use('table', function(){
    let table = layui.table;

    //第一个实例
    tableIns=  table.render({
        elem: '#accountList'
        ,height: 450
        ,url: baseURL+'/account/list' //数据接口
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
            {field: 'username',title: '用户名'}
            ,{field: 'realName', title: '真是姓名'}
            ,{field: 'sex', title: '性别'}
            ,{field: 'email', title: '邮箱'}
            ,{field: 'createTime', title: '创建时间'}
            ,{title: '操作', toolbar: '#barDemo'}
        ]]
    });

    //工具条事件
    table.on('tool(accountTable)', function(obj){ //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
        console.log(obj);
        let data = obj.data; //获得当前行数据
        let layEvent = obj.event; //获得 lay-event 对应的值（也可以是表头的 event 参数对应的值）
        let tr = obj.tr; //获得当前行 tr 的 DOM 对象（如果有的话）
        let accountId=data.accountId;


        if(layEvent === 'detail'){ //查看
            openLayer(baseURL+'/account/toDetail/'+accountId,'账户详情');
        } else if(layEvent === 'del'){ //删除
            // layer.confirm('真的删除行么', function(index){
            //     //obj.del(); //删除对应行（tr）的DOM结构，并更新缓存
            //     deleteData(baseURL+"/customer/doDelete/"+customerId);
            //     layer.close(index);
            //     //向服务端发送删除指令
            // });
        } else if(layEvent === 'edit'){ //编辑
            openLayer(baseURL+'/account/toUpdate/'+accountId,'修改账户信息');
            submitData('updateSubmit','post');
        }
    });

});

/**
 * 查询按钮事件
 */
function query(){
    tableIns.reload({
        where:{
            realName:$("#realName").val(),
            email:$("#email").val(),
            createTimeRange:$("#createTimeRange").val()
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
    openLayer(baseURL+'/account/toAdd','新增账户信息');
    submitData('addSubmit','post');
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
function submitData(filterName,requestType){
    layui.form.render();
    layui.form.on('submit('+filterName+')',function (data){
        console.log(data);
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