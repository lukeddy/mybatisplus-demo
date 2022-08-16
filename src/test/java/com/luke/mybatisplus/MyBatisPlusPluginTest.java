package com.luke.mybatisplus;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.luke.mybatisplus.mapper.ProductMapper;
import com.luke.mybatisplus.mapper.UserMapper;
import com.luke.mybatisplus.pojo.Product;
import com.luke.mybatisplus.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MyBatisPlusPluginTest {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ProductMapper productMapper;

    @Test
    public void testPaginationPlugin(){
        Page<User> page=new Page<>(1,3);
        QueryWrapper<User> queryWrapper=new QueryWrapper();
        userMapper.selectPage(page,null);
        System.out.println(page.getPages());
        System.out.println(page.getTotal());
        System.out.println(page.hasNext());
        System.out.println(page.hasPrevious());
        page.getRecords().forEach(System.out::println);
    }

    @Test
    public void testPaginationPlugin2(){
        Page<User> page=new Page<>(1,3);
        QueryWrapper<User> queryWrapper=new QueryWrapper<>();
        queryWrapper.like("name","Wang")
                        .gt("age",5);
        userMapper.selectPage(page,queryWrapper);
        System.out.println(page.getPages());
        System.out.println(page.getTotal());
        System.out.println(page.hasNext());
        System.out.println(page.hasPrevious());
        page.getRecords().forEach(System.out::println);
    }

    /**
     * 测试自定义条件分页
     */
    @Test
    public void testPaginationByCustomizeMethod(){
        Page<User> page=new Page<>(1,3);
        userMapper.selectByCustomizePage(page,24);
        System.out.println(page.getPages());
        System.out.println(page.getTotal());
        System.out.println(page.hasNext());
        System.out.println(page.hasPrevious());
        page.getRecords().forEach(System.out::println);

    }

    /**
     * 乐观锁与悲观锁:
     * 一件商品，成本价是80元，售价是100元。老板先是通知小李，说你去把商品价格增加50元。小
     * 李正在玩游戏，耽搁了一个小时。正好一个小时后，老板觉得商品价格增加到150元，价格太
     * 高，可能会影响销量。又通知小王，你把商品价格降低30元。
     * 此时，小李和小王同时操作商品后台系统。小李操作的时候，系统先取出商品价格100元；小王
     * 也在操作，取出的商品价格也是100元。小李将价格加了50元，并将100+50=150元存入了数据
     * 库；小王将商品减了30元，并将100-30=70元存入了数据库。是的，如果没有锁，小李的操作就
     * 完全被小王的覆盖了。
     * 现在商品价格是70元，比成本价低10元。几分钟后，这个商品很快出售了1千多件商品，老板亏1
     * 万多。
     * 上面的故事，如果是乐观锁，小王保存价格前，会检查下价格是否被人修改过了。如果被修改过
     * 了，则重新取出的被修改后的价格，150元，这样他会将120元存入数据库。
     * 如果是悲观锁，小李取出数据后，小王只能等小李操作完之后，才能对价格进行操作，也会保证
     * 最终的价格是120元。
     *
     * 模拟修改冲提示例
     */
    @Test
    public void testProduct(){
        //1、小李
        Product p1 = productMapper.selectById(1L);
        System.out.println("小李取出的价格：" + p1.getPrice());
        //2、小王
        Product p2 = productMapper.selectById(1L);
        System.out.println("小王取出的价格：" + p2.getPrice());
        //3、小李将价格加了50元，存入了数据库
        p1.setPrice(p1.getPrice() + 50);
        int result1 = productMapper.updateById(p1);
        System.out.println("小李修改结果：" + result1);
        //4、小王将商品减了30元，存入了数据库
        p2.setPrice(p2.getPrice() - 30);
        int result2 = productMapper.updateById(p2);
        System.out.println("小王修改结果：" + result2);
        //最后的结果
        Product p3 = productMapper.selectById(1L);
        //价格覆盖，最后的结果：70
        System.out.println("最后的结果：" + p3.getPrice());
    }
}
