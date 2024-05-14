package com.atguigu.mpdemo1010;

import com.atguigu.mpdemo1010.entity.User;
import com.atguigu.mpdemo1010.mapper.UserMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@SpringBootTest
public class Mpdemo1010ApplicationTests {

    @Autowired
    private UserMapper userMapper;

    //查询user表的所有数据
    @Test
    public void contextLoads() {
        List<User> users = userMapper.selectList(null);
        System.out.println(users);
    }

    //添加操作
    @Test
    public void addUser() {
        User user = new User();
        user.setName("东方不败");
        user.setAge(60);
        user.setEmail("lucy@qq.com");

        int result = userMapper.insert(user);
        System.out.println("insert:" + result);//影响的行数
        System.out.println(user);//id自动回填
    }

    @Test
    public void updateUser() {
        User user = new User();
        user.setId(1789958081322766337L);//参数不能是2；因为id是long类型
        user.setAge(120);
        int row = userMapper.updateById(user);
        System.out.println(row);
    }

    //测试乐观锁
    @Test
    public void testOptimisticLocker() {
        //根据id查询数据
        User user = userMapper.selectById(1789966518173642754L);
        //进行修改
        user.setAge(200);
        userMapper.updateById(user);
    }

    //多个id批量查询
    @Test
    public void testSelectDemo1() {
        List<User> users = userMapper.selectBatchIds(Arrays.asList(1L, 2L, 3L));
        System.out.println(users);
    }

    //条件查询
    @Test
    public void testSelectByMap(){
        HashMap<String, Object> map = new HashMap<>();
        map.put("name", "Jone");
        map.put("age", 18);
        List<User> users = userMapper.selectByMap(map);
        users.forEach(System.out::println);
    }

    //分页查询
    @Test
    public void testPage() {
        //1.创建page对象
        Page<User> page = new Page<>(1, 3);
        //2.调用mp分页查询的方法
        IPage<User> userIPage = userMapper.selectPage(page, null);


        System.out.println("当前页" + page.getCurrent());
        System.out.println("该页数据的list集合" + page.getRecords());
        System.out.println("每页显示记录数" + page.getSize());
        System.out.println("总记录数" + page.getTotal());
        System.out.println("总页数" + page.getPages());

        System.out.println("是否有下一页" + page.hasNext());
        System.out.println("是否有上一页" + page.hasPrevious());
    }

    //物理删除
    @Test
    public void testDeleteById() {
        int result = userMapper.deleteById(1L);
        System.out.println(result);
    }

    //批量删除
    @Test
    public void testDeleteBatchIds() {
        int result = userMapper.deleteBatchIds(Arrays.asList(2,3));
        System.out.println(result);
    }




}
