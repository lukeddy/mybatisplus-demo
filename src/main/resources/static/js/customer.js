layui.use('table', function(){
    let table = layui.table;

    //第一个实例
    table.render({
        elem: '#customerList'
        ,height: 360
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