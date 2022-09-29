package com.open.project.batch;

import com.open.HelloConcurrentApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * @author liuxiaowei
 * @date 2022年09月29日 16:34
 * @Description
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = HelloConcurrentApplication.class)
public class StudentsTransactionThreadTest {

    @Autowired
    StudentMapper studentMapper;

    @Autowired
    StudentsTransactionThread transactionThread;

    @Test
    public void insert() {
        for (int i = 0; i < 10000; i++) {
            Student student = new Student();
            student.setTeacher("PP"+i);
            studentMapper.insert(student);
        }
    }

    @Test
    public void test() throws Exception {
        transactionThread.updateStudentWithThreadsAndTrans(5);
    }

}