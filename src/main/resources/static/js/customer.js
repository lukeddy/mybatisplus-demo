let tableIns;
layui.use('table', function(){
    let table = layui.table;

    //第一个实例
   tableIns=  table.render({
        elem: '#customerList'
        ,height: 450
        ,url: baseURL+'/customer/list' //数据接口
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
            {field: 'realName', title: '真是姓名'}
            ,{field: 'sex', title: '性别'}
            ,{field: 'age', title: '年龄'}
            ,{field: 'phone', title: '手机号'}
            ,{field: 'createTime', title: '创建时间'}
            ,{title: '操作', toolbar: '#barDemo'}
        ]]
    });
});

/**
 * 查询按钮事件
 */
function query(){
    tableIns.reload({
        where:{
            realName:$("#realName").val(),
            phone:$("#phone").val()
        },
        page: {
            curr:1
        }
    })
}

function toAddPage(){
    $.get(baseURL+'/customer/toAdd',function (res){
        layui.layer.open({
            type:1,
            title:"新增客户",
            area:['800px','450px'],
            content:res
        });
        layui.form.render();
        layui.form.on('submit(addSubmit)',function (data){
            console.log(data);
            $.ajax({
                url: data.form.action,
                async:false,
                type:"post",
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
    })
}